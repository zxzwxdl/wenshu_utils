package com.songguoxiong.wenshu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguoxiong.wenshu.utils.old.docid.DocIdDecryptor;
import com.songguoxiong.wenshu.utils.old.docid.RunEvalParser;
import com.songguoxiong.wenshu.utils.old.vl5x.Guid;
import com.songguoxiong.wenshu.utils.old.vl5x.Number;
import com.songguoxiong.wenshu.utils.old.vl5x.Vjkl5;
import com.songguoxiong.wenshu.utils.old.vl5x.Vl5x;
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
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OldDemoTest {
    private static final String DOMAIN = "oldwenshu.court.gov.cn";

    private static BasicCookieStore cookieStore = new BasicCookieStore();
    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .setDefaultCookieStore(cookieStore)
            .build();

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void listPage() throws Exception {
        String url = "http://oldwenshu.court.gov.cn/List/ListContent";

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

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(formEntity);

        String text;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        String s = objectMapper.readValue(text, String.class);
        if (s.equals("remind")) {
            throw new Exception("访问限制，请使用代理");
        }

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
            String docId = decryptor.decrypt(cipherText);
            System.out.println("成功, 文书ID: " + docId + "\n");
        }
    }

    @Test
    public void detailPage() throws Exception {
        String url = "http://oldwenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx";

        HttpGet httpGet = new HttpGet(new URIBuilder(url)
                .addParameter("DocID", "8f0230e9-f0e4-418f-a8e0-4c24677c7a79")
                .build());

        String text;
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        if (text.contains("window.location.href")) {
            throw new Exception("需要验证码，请自行打码或使用代理");
        }

        System.out.println(text);

        // 提取数据
        Pattern pattern = Pattern.compile("var caseinfo=JSON\\.stringify\\((?<caseInfo>.+?)\\);\\$.+var jsonHtmlData = (?<jsonHtmlData>\".+\");", Pattern.DOTALL);
        Matcher m = pattern.matcher(text);
        if (m.find()) {
            Map<String, String> caseInfo = objectMapper.readValue(
                    m.group("caseInfo"),
                    new TypeReference<Map<String, String>>() {
                    }
            );
            System.out.println(caseInfo);

            Map<String, String> jsonHtmlData = objectMapper.readValue(
                    objectMapper.readValue(m.group("jsonHtmlData"), String.class),
                    new TypeReference<Map<String, String>>() {
                    }
            );
            System.out.println(jsonHtmlData);
        }
    }
}