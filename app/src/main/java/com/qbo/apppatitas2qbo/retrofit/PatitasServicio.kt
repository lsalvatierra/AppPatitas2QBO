package com.qbo.apppatitas2qbo.retrofit

import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.request.RequestRegistro
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface PatitasServicio {

    @POST("login.php")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @PUT("persona.php")
    fun registro(@Body requestRegistro: RequestRegistro): Call<ResponseRegistro>
}