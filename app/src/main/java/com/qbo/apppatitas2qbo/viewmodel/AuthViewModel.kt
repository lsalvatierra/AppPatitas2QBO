package com.qbo.apppatitas2qbo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.qbo.apppatitas2qbo.repository.AuthRepository
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.request.RequestRegistro
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro

class AuthViewModel : ViewModel() {

    var responseLogin: LiveData<ResponseLogin>
    var responseRegistro: LiveData<ResponseRegistro>
    private var repository = AuthRepository()

    init {
        responseLogin = repository.loginResponse
        responseRegistro = repository.registroResponse
    }

    fun autenticarUsuario(usuario: String, password: String)
    {
        responseLogin = repository.autenticarUsuario(
            RequestLogin(usuario, password))
    }

    fun registrarUsuario(nombres: String, apellidos: String,email: String,
                         celular: String,usuario: String, password: String)
    {
        responseRegistro = repository.registrarUsuario(
            RequestRegistro(nombres, apellidos, email, celular,
            usuario, password)
        )
    }
}