package com.songguoxiong.wenshu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguoxiong.wenshu.util.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DemoTest {
    private static CloseableHttpClient httpClient = HttpClients.custom()
            // 标准的cookie规范
            .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
            .build();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String request(List<BasicNameValuePair> formData) throws Exception {
        HttpPost httpPost = new HttpPost("https://wenshu.court.gov.cn/website/parse/rest.q4w");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        httpPost.setEntity(new UrlEncodedFormEntity(formData, "UTF-8"));

        String text;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            text = EntityUtils.toString(response.getEntity(), "UTF-8");
        }

        Response response = objectMapper.readValue(text, Response.class);

        String cipherText = response.getResult();
        String key = response.getSecretKey();
        String iv = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return DES3.decrypt(cipherText, key, iv);
    }

    @Test
    public void listPage() throws Exception {
        List<BasicNameValuePair> formData = new ArrayList<>();

        HashMap<String, String> conditionParams = new HashMap<>();
        conditionParams.put("key", "s8");
        conditionParams.put("value", "03");

        ArrayList<HashMap<String, String>> queryCondition = new ArrayList<>();
        queryCondition.add(conditionParams);

        formData.add(new BasicNameValuePair("pageID", new PageID().getValue()));
        formData.add(new BasicNameValuePair("sortFields", "s50:desc"));
        formData.add(new BasicNameValuePair("ciphertext", new CipherText().getValue()));
        formData.add(new BasicNameValuePair("pageNum", "1"));
        formData.add(new BasicNameValuePair("pageSize", "5"));
        formData.add(new BasicNameValuePair("queryCondition", objectMapper.writeValueAsString(queryCondition)));
        formData.add(new BasicNameValuePair("cfg", "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@queryDoc"));
        formData.add(new BasicNameValuePair("__RequestVerificationToken", new RequestVerificationToken(24).getValue()));

        System.out.println(request(formData));
    }

    @Test
    public void detailPage() throws Exception {
        List<BasicNameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("docId", "4e00b8ae589b4288a725aabe00c0e683"));
        formData.add(new BasicNameValuePair("ciphertext", new CipherText().getValue()));
        formData.add(new BasicNameValuePair("cfg", "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@docInfoSearch"));
        formData.add(new BasicNameValuePair("__RequestVerificationToken", new RequestVerificationToken(24).getValue()));

        System.out.println(request(formData));
    }
}
