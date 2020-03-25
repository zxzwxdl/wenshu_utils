package com.songguoxiong.wenshu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.songguoxiong.wenshu.utils.*;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class DemoTest {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://wenshu.court.gov.cn/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    private static WenshuService service = retrofit.create(WenshuService.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public Response request(Call<Response> call) throws Exception {
        Response response = call.execute().body();

        if (response == null || !response.isSuccess()) {
            return response;
        }

        String cipherText = response.getResult();
        String key = response.getSecretKey();
        String iv = new SimpleDateFormat("yyyyMMdd").format(new Date());

        response.setResult(DES3.decrypt(cipherText, key, iv));
        return response;
    }

    @Test
    public void listPage() throws Exception {
        HashMap<String, String> conditionParams = new HashMap<>();
        conditionParams.put("key", "s8");
        conditionParams.put("value", "03");

        ArrayList<HashMap<String, String>> queryCondition = new ArrayList<>();
        queryCondition.add(conditionParams);

        Call<Response> list = service.list(
                new PageID().getValue(),
                "s50:desc",
                new CipherText().getValue(),
                1,
                5,
                objectMapper.writeValueAsString(queryCondition),
                "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@queryDoc",
                new RequestVerificationToken(24).getValue()
        );
        System.out.println(request(list));
    }

    @Test
    public void detailPage() throws Exception {
        Call<Response> detail = service.detail(
                "4e00b8ae589b4288a725aabe00c0e683",
                new CipherText().getValue(),
                "com.lawyee.judge.dc.parse.dto.SearchDataDsoDTO@docInfoSearch",
                new RequestVerificationToken(24).getValue()
        );
        System.out.println(request(detail));
    }
}
