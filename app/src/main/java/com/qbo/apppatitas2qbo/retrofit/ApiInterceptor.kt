package com.qbo.apppatitas2qbo.retrofit

import com.qbo.apppatitas2qbo.utilitarios.Constantes
import com.qbo.apppatitas2qbo.utilitarios.SharedPreferencesManager
import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val token: String = SharedPreferencesManager().getSomeStringValue(Constantes().PREF_TOKEN)
        val request = chain.request().newBuilder().addHeader("Authorization",
                "Bearer $token"
        ).build()
        return chain.proceed(request)
    }
}