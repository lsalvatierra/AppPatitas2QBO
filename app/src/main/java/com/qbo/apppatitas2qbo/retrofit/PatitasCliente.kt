package com.qbo.apppatitas2qbo.retrofit

import com.qbo.apppatitas2qbo.utilitarios.Constantes
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object PatitasCliente {

    private var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            //.addInterceptor(ApiInterceptor())
            .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(Constantes().API_PATITAS_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val retrofitService: PatitasServicio by lazy {
        buildRetrofit().create(PatitasServicio::class.java)
    }
}