package com.sgx.spider.wenshu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sgx.spider.wenshu.docid.DocIdDecryptor;
import com.sgx.spider.wenshu.docid.RunEvalParser;
import com.sgx.spider.wenshu.vl5x.Guid;
import com.sgx.spider.wenshu.vl5x.Number;
import com.sgx.spider.wenshu.vl5x.Vjkl5;
import com.sgx.spider.wenshu.vl5x.Vl5x;
import com.sgx.spider.wenshu.wzws.WZWSParser;
import org.apache.http.HttpEntity;
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

import javax.script.ScriptException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Demo {
    public static final String DOMAIN = "wenshu.court.gov.cn";

    public static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();

    public static BasicCookieStore cookieStore = new BasicCookieStore();

    public static CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .setDefaultCookieStore(cookieStore)
            .build();

    @Test
    public void requestList() throws Exception {
        String url = "http://wenshu.court.gov.cn/List/ListContent";
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");

        Vjkl5 vjkl5 = new Vjkl5();

        List<NameValuePair> data = new ArrayList<>();
        data.add(new BasicNameValuePair("Param", "关键词:合同"));
        data.add(new BasicNameValuePair("Index", "1"));
        data.add(new BasicNameValuePair("Page", "10"));
        data.add(new BasicNameValuePair("Order", "法院层级"));
        data.add(new BasicNameValuePair("Direction", "asc"));
        data.add(new BasicNameValuePair("vl5x", new Vl5x(vjkl5).getValue()));
        data.add(new BasicNameValuePair("number", new Number().getValue()));
        data.add(new BasicNameValuePair("guid", new Guid().getValue()));
        httpPost.setEntity(new UrlEncodedFormEntity(data, "UTF-8"));

        BasicClientCookie cookie = new BasicClientCookie("vjkl5", vjkl5.getValue());
        cookie.setDomain(DOMAIN);
        cookieStore.addCookie(cookie);

        String text;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            text = EntityUtils.toString(entity);
        }

        List<JSONObject> jsonObjects = JSON.parseArray((String) JSON.parse(text), JSONObject.class);
        System.out.println("列表数据: " + jsonObjects);

        String runEval = jsonObjects.get(0).getString("RunEval");
        String key = RunEvalParser.parse(runEval);
        System.out.println("RunEval解析完成: " + key);
        System.out.println();

        DocIdDecryptor decryptor = new DocIdDecryptor(key);

        for (int i = 1; i < jsonObjects.size(); i++) {
            JSONObject item = jsonObjects.get(i);
            String cipherText = item.getString("文书ID");
            System.out.println("解密: " + cipherText);
            String docId = decryptor.decryptDocId(cipherText);
            System.out.println("成功, 文书ID: " + docId);
            System.out.println();
        }
    }

    @Test
    public void requestDetail() throws URISyntaxException, IOException, ScriptException, NoSuchMethodException {
        String url = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx";

        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.addParameter("DocID", "13d4c01a-0734-4ec1-bbac-658f8bb8ec62");

        URI builtUrl = uriBuilder.build();
        HttpGet httpGet = new HttpGet(builtUrl);

        String text;
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            text = EntityUtils.toString(entity, "UTF-8");
        }

        if (text.contains("请开启JavaScript并刷新该页")) {
            // 这样更快
            String redirectUrl1 = WZWSParser.parse(text, builtUrl);
            // 这样慢很多
            String redirectUrl2 = WZWSParser.parse(text);

            assert Objects.equals(redirectUrl1, redirectUrl2);

            try (CloseableHttpResponse response = httpClient.execute(new HttpGet(redirectUrl1))) {
                HttpEntity entity2 = response.getEntity();
                text = EntityUtils.toString(entity2, "UTF-8");
            }
        }

        System.out.println(text);
    }
}
