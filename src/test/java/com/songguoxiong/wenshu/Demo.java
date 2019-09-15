package com.songguoxiong.wenshu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguoxiong.wenshu.utils.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class Demo {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static String request(List<BasicNameValuePair> formData) throws Exception {
        HttpPost httpPost = new HttpPost("http://wenshu.court.gov.cn/website/parse/rest.q4w");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
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
        List<BasicNameValuePair> formData = List.of(
                new BasicNameValuePair("pageID", new PageID().getValue()),
                new BasicNameValuePair("sortFields", "s50:desc"),
                new BasicNameValuePair("ciphertext", new CipherText().getValue()),
                new BasicNameValuePair("pageNum", "1"),
                new BasicNameValuePair("pageSize", "5"),
                new BasicNameValuePair("queryCondition", objectMapper.writeValueAsString(List.of(
                        Map.of("key", "s8", "value", "03"))
                )),
                new BasicNameValuePair("cfg", "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@queryDoc"),
                new BasicNameValuePair("__RequestVerificationToken", new RequestVerificationToken(24).getValue())
        );

        System.out.println(request(formData));
    }

    @Test
    public void detailPage() throws Exception {
        List<BasicNameValuePair> formData = List.of(
                new BasicNameValuePair("docId", "4e00b8ae589b4288a725aabe00c0e683"),
                new BasicNameValuePair("ciphertext", new CipherText().getValue()),
                new BasicNameValuePair("cfg", "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@docInfoSearch"),
                new BasicNameValuePair("__RequestVerificationToken", new RequestVerificationToken(24).getValue())
        );

        System.out.println(request(formData));
    }
}
