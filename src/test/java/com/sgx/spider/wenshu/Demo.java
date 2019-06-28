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

public class Demo {
    private static final String DOMAIN = "wenshu.court.gov.cn";

    private static RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
    private static BasicCookieStore cookieStore = new BasicCookieStore();
    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(requestConfig)
            .setDefaultCookieStore(cookieStore)
            // 设置重定向策略，允许所有method的自动重定向，配合列表页post返回307的重定向
            .setRedirectStrategy(new LaxRedirectStrategy())
            .build();

    private static final String errorMsg = "请开启JavaScript并刷新该页";

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
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        if (text.contains(errorMsg)) {
            int retry = 3;
            boolean success = false;
            for (int i = 0; i < retry; i++) {
                String redirectUrl = WZWSParser.parse(text);

                httpPost = new HttpPost(redirectUrl);
                httpPost.setEntity(formEntity);
                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    text = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
                if (!text.contains(errorMsg)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                throw new Exception(String.format("连续%d获取数据失败", retry));
            }
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
    public void detailPage() throws Exception {
        String url = "http://wenshu.court.gov.cn/CreateContentJS/CreateContentJS.aspx";

        String text;

        URI uri = new URIBuilder(url)
                .addParameter("DocID", "8f0230e9-f0e4-418f-a8e0-4c24677c7a79")
                .build();
        HttpGet httpGet = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        if (text.contains(errorMsg)) {
            int retry = 3;
            boolean success = false;
            for (int i = 0; i < retry; i++) {
                String redirectUrl = WZWSParser.parse(text);
                try (CloseableHttpResponse response = httpClient.execute(new HttpGet(redirectUrl))) {
                    text = EntityUtils.toString(response.getEntity(), "UTF-8");
                }
                if (!text.contains(errorMsg)) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                throw new Exception(String.format("连续%d获取数据失败", retry));
            }
        }

        System.out.println(text);
    }
}
