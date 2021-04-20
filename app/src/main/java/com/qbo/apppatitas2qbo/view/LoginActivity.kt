package com.qbo.apppatitas2qbo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.ActivityLoginBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnlogin.setOnClickListener {
            binding.btnlogin.isEnabled = false
            if(validarUsuarioPassword()){
                autenticarUsuario(it, binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
            }else{
                binding.btnlogin.isEnabled = true
                mostrarMensaje(it, getString(R.string.msguspassword))
            }
        }
        binding.tvregistrarme.setOnClickListener {
            startActivity(Intent(applicationContext,
                RegistroActivity::class.java))
        }
    }


    fun autenticarUsuario(vista: View, usuario: String, password: String){
        val requestLogin = RequestLogin(usuario, password)
        val call: Call<ResponseLogin> = PatitasCliente.retrofitService.login(requestLogin)
        call.enqueue(object :Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                binding.btnlogin.isEnabled = true
                if(response.body()!!.rpta){
                    startActivity(Intent(applicationContext,
                        HomeActivity::class.java))
                }
            }
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                binding.btnlogin.isEnabled = true
                Log.e("ErroWS", t.message)
                mostrarMensaje(vista, "Login failed!")
            }
        })
    }


    //2. Método que valida el ingreso de usuario y password.
    fun validarUsuarioPassword():Boolean{
        var respuesta = true
        if(binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            respuesta = false
        } else if(binding.etpassword.text.toString().trim().isEmpty()){
            binding.etpassword.isFocusable = true
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    //1. Creamos método para enviar nuestros mensajes
    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}