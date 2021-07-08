package com.qbo.apppatitas2qbo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.ActivityRegistroBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestRegistro
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro
import com.qbo.apppatitas2qbo.utilitarios.AppMensaje
import com.qbo.apppatitas2qbo.utilitarios.TipoMensaje
import com.qbo.apppatitas2qbo.viewmodel.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegistroBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this)
            .get(AuthViewModel::class.java)
        binding.btnregistrarme.setOnClickListener {
            binding.btnregistrarme.isEnabled = false
            if(validarFormulario(it)){
                authViewModel.registrarUsuario(binding.etnomusuario.text.toString(),
                    binding.etapeusuario.text.toString(),
                    binding.etemailusuario.text.toString(),
                    binding.etcelusuario.text.toString(),
                    binding.etusureg.text.toString(),
                    binding.etpassreg.text.toString())
            }else{
                binding.btnregistrarme.isEnabled = true
            }
        }
        binding.btnirlogin.setOnClickListener{
            startActivity(Intent(applicationContext,
                LoginActivity::class.java))
        }
        authViewModel.responseRegistro.observe(this, Observer {
            obtenerResultadoRegistro(it)
        })
    }

    private fun obtenerResultadoRegistro(responseRegistro: ResponseRegistro) {
        if (responseRegistro.rpta) {
            setearControles()
        }
        AppMensaje.enviarMensaje(binding.root,
            responseRegistro.mensaje, TipoMensaje.ERROR)
        binding.btnregistrarme.isEnabled = true
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
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su nombre", TipoMensaje.ERROR)
                respuesta = false
            }
            binding.etapeusuario.text.toString().trim().isEmpty() -> {
                binding.etapeusuario.isFocusableInTouchMode = true
                binding.etapeusuario.requestFocus()
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su apellido", TipoMensaje.ERROR)
                respuesta = false
            }
            binding.etemailusuario.text.toString().trim().isEmpty() -> {
                binding.etemailusuario.isFocusableInTouchMode = true
                binding.etemailusuario.requestFocus()
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su email", TipoMensaje.ERROR)
                respuesta = false
            }
            /*binding.etemailusuario.text.toString().trim().isNotEmpty() -> {
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                if(!pattern.matcher(binding.etemailusuario.text.toString().trim()).matches())
                {
                    binding.etemailusuario.isFocusableInTouchMode = true
                    binding.etemailusuario.requestFocus()
                    AppMensaje.enviarMensaje(binding.root,
                        "Ingrese su email correctamente", TipoMensaje.ERROR)
                    respuesta = false
                }
            }*/
            binding.etcelusuario.text.toString().trim().isEmpty() -> {
                binding.etcelusuario.isFocusableInTouchMode = true
                binding.etcelusuario.requestFocus()
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su celular", TipoMensaje.ERROR)
                respuesta = false
            }

            binding.etusureg.text.toString().trim().isEmpty() -> {
                binding.etusureg.isFocusableInTouchMode = true
                binding.etusureg.requestFocus()
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su usuario", TipoMensaje.ERROR)
                respuesta = false
            }
            binding.etpassreg.text.toString().trim().isEmpty() -> {
                binding.etpassreg.isFocusableInTouchMode = true
                binding.etpassreg.requestFocus()
                AppMensaje.enviarMensaje(binding.root,
                    "Ingrese su password", TipoMensaje.ERROR)
                respuesta = false
            }
            binding.etpassreg.text.toString().trim().isNotEmpty() -> {
                if(binding.etpassreg.text.toString() != binding.etrepassreg.text.toString()){
                    binding.etrepassreg.isFocusableInTouchMode = true
                    binding.etrepassreg.requestFocus()
                    AppMensaje.enviarMensaje(binding.root,
                        "Su password no coincide", TipoMensaje.ERROR)
                    respuesta = false
                }
            }
        }
        return respuesta
    }


}