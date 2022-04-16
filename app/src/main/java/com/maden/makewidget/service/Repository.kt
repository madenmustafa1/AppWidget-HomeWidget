package com.maden.makewidget.service

import com.maden.makewidget.model.LoginResult
import com.maden.makewidget.model.count_model.CountModel
import java.util.concurrent.TimeUnit
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

class Repository {

    private val okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val call = Retrofit.Builder()
        .baseUrl(Constants.LINK)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build().create(RetrofitApi::class.java)

    fun connectToken(username: String, password: String): Single<LoginResult> {
        return call.connectToken(
            grantType = "password", clientId = "DijitalKurye_App",
            clientSecret = "1q2w3e*",
            username = username, password = password, scope = "DijitalKurye"
        )
    }

    fun getCount(auth: String, dateTime: String): Single<CountModel> {
        return call.getCount(auth = auth, dateTime = dateTime)
    }


}