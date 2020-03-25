package com.songguoxiong.wenshu;

import com.songguoxiong.wenshu.utils.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WenshuService {
    @FormUrlEncoded
    @POST("website/parse/rest.q4w")
    Call<Response> list(@Field("pageID") String pageID,
                        @Field("sortFields") String sortFields,
                        @Field("ciphertext") String cipherText,
                        @Field("pageNum") int pageNum,
                        @Field("pageSize") int pageSize,
                        @Field("queryCondition") String queryCondition,
                        @Field("cfg") String cfg,
                        @Field("__RequestVerificationToken") String requestVerificationToken);

    @FormUrlEncoded
    @POST("website/parse/rest.q4w")
    Call<Response> detail(@Field("docId") String docId,
                          @Field("ciphertext") String cipherText,
                          @Field("cfg") String cfg,
                          @Field("__RequestVerificationToken") String requestVerificationToken);
}