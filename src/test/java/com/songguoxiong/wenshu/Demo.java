package com.songguoxiong.wenshu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguoxiong.wenshu.utils.old.docid.DocIdDecryptor;
import com.songguoxiong.wenshu.utils.old.docid.RunEvalParser;
import com.songguoxiong.wenshu.utils.old.vl5x.Guid;
import com.songguoxiong.wenshu.utils.old.vl5x.Number;
import com.songguoxiong.wenshu.utils.old.vl5x.Vjkl5;
import com.songguoxiong.wenshu.utils.old.vl5x.Vl5x;
import com.songguoxiong.wenshu.utils.old.wzws.WZWSParser;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {
    private static final String DOMAIN = "wenshu.court.gov.cn";

    private static BasicCookieStore cookieStore = new BasicCookieStore();
    private static CloseableHttpClient httpClient = HttpClients.custom()
            // 标准的cookie规范
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .setDefaultCookieStore(cookieStore)
            // 设置重定向策略，允许所有method的自动重定向，配合列表页post返回307的重定向
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();

    @Test
    public void listPage() throws Exception {
        String url = "http://wenshu.court.gov.cn/List/ListContent";

        Vjkl5 vjkl5 = new Vjkl5();
        BasicClientCookie cookie = new BasicClientCookie("vjkl5", vjkl5.getValue());
        cookie.setDomain(DOMAIN);
        cookieStore.addCookie(cookie);

        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("Param", "案件类型:刑事案件"));
        data.add(new BasicNameValuePair("Index", "1"));
        data.add(new BasicNameValuePair("Page", "10"));
        data.add(new BasicNameValuePair("Order", "法院层级"));
        data.add(new BasicNameValuePair("Direction", "asc"));
        data.add(new BasicNameValuePair("vl5x", new Vl5x(vjkl5).getValue()));
        data.add(new BasicNameValuePair("number", new Number().getValue()));
        data.add(new BasicNameValuePair("guid", new Guid().getValue()));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data, "UTF-8");

        String text;

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(formEntity);
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {// 请求1
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        if (text.contains("请开启JavaScript并刷新该页")) {
            // 如果使用代理，确保请求1和请求2的ip为同一个，否则将继续返回"请开启JavaScript并刷新该页"
            String dynamicUrl = WZWSParser.parse(text);
            httpPost.setURI(URI.create(dynamicUrl));

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {// 请求2
                text = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.readValue(text, String.class);
        List<Map<String, String>> list = objectMapper.readValue(s, new TypeReference<List<Map<String, String>>>() {
        });
        System.out.println("列表数据: " + list);

        String runEval = list.get(0).get("RunEval");
        String key = RunEvalParser.parse(runEval);
        System.out.println("RunEval解析完成: " + key + "\n");

        DocIdDecryptor decryptor = new DocIdDecryptor(key);

        for (int i = 1; i < list.size(); i++) {
            Map<String, String> item = list.get(i);
            String cipherText = item.get("文书ID");
            System.out.println("解密: " + cipherText);
            String docId = decryptor.decryptDocId(cipherText);
            System.out.println("成功, 文书ID: " + docId + "\n");
        }
    }

    @Test
    public void detailPage() throws Exception {
        String url = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx";

        String text;

        URI uri = new URIBuilder(url)
                .addParameter("DocID", "8f0230e9-f0e4-418f-a8e0-4c24677c7a79")
                .build();
        HttpGet httpGet = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {// 请求1
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        if (text.contains("请开启JavaScript并刷新该页")) {
            // 如果使用代理，确保请求1和请求2的ip为同一个，否则将继续返回"请开启JavaScript并刷新该页"
            String dynamicUrl = WZWSParser.parse(text);
            httpGet.setURI(URI.create(dynamicUrl));

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {// 请求2
                text = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }

        System.out.println(text);

        // 提取数据
        Pattern pattern = Pattern.compile("var caseinfo=JSON\\.stringify\\((?<caseInfo>.+?)\\);\\$.+var jsonHtmlData = (?<jsonHtmlData>\".+\");", Pattern.DOTALL);
        Matcher m = pattern.matcher(text);
        if (m.find()) {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference ref = new TypeReference<Map<String, String>>() {
            };

            Map caseInfo = objectMapper.readValue(m.group("caseInfo"), ref);
            System.out.println(caseInfo);

            String s = objectMapper.readValue(m.group("jsonHtmlData"), String.class);
            Map jsonHtmlData = objectMapper.readValue(s, ref);
            System.out.println(jsonHtmlData);
        }
    }
}
