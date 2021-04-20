package com.qbo.apppatitas2qbo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.ActivityRegistroBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestRegistro
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnregistrarme.setOnClickListener {
            binding.btnregistrarme.isEnabled = false
            if(validarFormulario(it)){
                registrarUsuario(it)
            }else{
                binding.btnregistrarme.isEnabled = true
            }
        }
        binding.btnirlogin.setOnClickListener{
            startActivity(Intent(applicationContext,
                LoginActivity::class.java))
        }
    }

    private fun registrarUsuario(vista: View) {
        val requestRegistro = RequestRegistro(
            binding.etnomusuario.text.toString(),
            binding.etapeusuario.text.toString(),
            binding.etemailusuario.text.toString(),
            binding.etcelusuario.text.toString(),
            binding.etusureg.text.toString(),
            binding.etpassreg.text.toString()
        )
        val call: Call<ResponseRegistro> = PatitasCliente
            .retrofitService.registro(requestRegistro)
        call.enqueue(object : Callback<ResponseRegistro>{
            override fun onResponse(
                call: Call<ResponseRegistro>,
                response: Response<ResponseRegistro>
            ) {
                if (response.body()!!.rpta) {
                    setearControles()
                }
                mostrarMensaje(vista, response.body()!!.mensaje)
                binding.btnregistrarme.isEnabled = true
            }

            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    //3. Método que setea los controles del formulario
    private fun setearControles() {
        binding.etnomusuario.setText("")
        binding.etapeusuario.setText("")
        binding.etemailusuario.setText("")
        binding.etcelusuario.setText("")
        binding.etusureg.setText("")
        binding.etpassreg.setText("")
        binding.etrepassreg.setText("")
    }

    //2. Método que valida el formulario.
    fun validarFormulario(vista: View):Boolean{
        var respuesta = true
        when {
            binding.etnomusuario.text.toString().trim().isEmpty() -> {
                binding.etnomusuario.isFocusableInTouchMode = true
                binding.etnomusuario.requestFocus()
                mostrarMensaje(vista, getString(R.string.action_cerrar))
                respuesta = false
            }
            binding.etapeusuario.text.toString().trim().isEmpty() -> {
                binding.etapeusuario.isFocusable = true
                binding.etapeusuario.isFocusableInTouchMode = true
                binding.etapeusuario.requestFocus()
                mostrarMensaje(vista, "Ingrese su apellido")
                respuesta = false
            }
            binding.etemailusuario.text.toString().trim().isEmpty() -> {
                binding.etemailusuario.isFocusable = true
                binding.etemailusuario.isFocusableInTouchMode = true
                binding.etemailusuario.requestFocus()
                mostrarMensaje(vista, "Ingrese su email")
                respuesta = false
            }
            binding.etemailusuario.text.toString().trim().isNotEmpty() -> {
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                if(!pattern.matcher(binding.etemailusuario.text.toString().trim()).matches())
                {
                    binding.etemailusuario.isFocusable = true
                    binding.etemailusuario.isFocusableInTouchMode = true
                    binding.etemailusuario.requestFocus()
                    mostrarMensaje(vista, "Ingrese un email válido")
                    respuesta = false
                }
            }
            binding.etcelusuario.text.toString().trim().isEmpty() -> {
                binding.etcelusuario.isFocusable = true
                binding.etcelusuario.isFocusableInTouchMode = true
                binding.etcelusuario.requestFocus()
                mostrarMensaje(vista, "Ingrese su celular")
                respuesta = false
            }

            binding.etusureg.text.toString().trim().isEmpty() -> {
                binding.etusureg.isFocusable = true
                binding.etusureg.isFocusableInTouchMode = true
                binding.etusureg.requestFocus()
                mostrarMensaje(vista, "Ingrese su usuario")
                respuesta = false
            }
            binding.etpassreg.text.toString().trim().isEmpty() -> {
                binding.etpassreg.isFocusable = true
                binding.etpassreg.isFocusableInTouchMode = true
                binding.etpassreg.requestFocus()
                mostrarMensaje(vista, "Ingrese su password")
                respuesta = false
            }
            binding.etpassreg.text.toString().trim().isNotEmpty() -> {
                if(binding.etpassreg.text.toString() != binding.etrepassreg.text.toString()){
                    binding.etrepassreg.isFocusable = true
                    binding.etrepassreg.isFocusableInTouchMode = true
                    binding.etrepassreg.requestFocus()
                    mostrarMensaje(vista, "Su password no coincide")
                    respuesta = false
                }
            }
        }
        return respuesta
    }


    //1. Creamos método para enviar nuestros mensajes
    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }
}