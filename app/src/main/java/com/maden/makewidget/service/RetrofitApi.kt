package com.maden.makewidget.service

import com.maden.makewidget.model.LoginResult
import com.maden.makewidget.model.count_model.CountModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RetrofitApi {

    @POST("connect/token")
    @FormUrlEncoded
    fun connectToken(
        @Field("grant_type") grantType: String?,
        @Field("client_id") clientId: String?,
        @Field("client_secret") clientSecret: String?,
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("scope") scope: String?
    ): Single<LoginResult>

    @GET("api/app/dashboard/count")
    fun getCount(
        @Header("Authorization") auth: String,
        @Query("dateTime") dateTime: String?,
    ): Single<CountModel>
}