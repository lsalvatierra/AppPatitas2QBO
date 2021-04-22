package com.qbo.apppatitas2qbo.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.ActivityLoginBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.utilitarios.Constantes
import com.qbo.apppatitas2qbo.db.entity.PersonaEntity
import com.qbo.apppatitas2qbo.utilitarios.MiApp
import com.qbo.apppatitas2qbo.utilitarios.SharedPreferencesManager
import com.qbo.apppatitas2qbo.viewmodel.PersonaViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    //Definimos el viewmodel
    private lateinit var personaViewModel: PersonaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Realizamos la instancia de ViewModelProvider
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        //Validamos que exista la preferencia recordardatos
        if(verificarValorSharedPreferences()){
            //activar checkbox recordar
            binding.chkrecordar.isChecked = true
            binding.etusuario.isEnabled = false
            binding.etpassword.isEnabled = false
            binding.chkrecordar.text = "Quitar el chek para ingresar con otro usuario"
            //obtiene los datos de SQLite y los muestra en los edittext
            personaViewModel.obtener()
                .observe(this, Observer { persona ->
                    // Update the cached copy of the words in the adapter.
                    persona?.let {
                        binding.etusuario.setText(persona.usuario)
                        binding.etpassword.setText(persona.password)
                    }

                })
        }else{
            personaViewModel.eliminartodo()
        }

        binding.chkrecordar.setOnClickListener {
            setearValoresDeRecordar(it)
        }

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

    fun setearValoresDeRecordar(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            when (view.id) {
                R.id.chkrecordar -> {
                    if(!checked){
                        if(verificarValorSharedPreferences()){
                            SharedPreferencesManager()
                                .deletePreference(Constantes().PREF_RECORDAR)
                            personaViewModel.eliminartodo()
                            binding.etusuario.isEnabled = true
                            binding.etpassword.isEnabled = true
                            binding.chkrecordar.text = getString(R.string.valchkguardardatos)
                        }
                    }
                }
            }
        }
    }

    fun verificarValorSharedPreferences(): Boolean{
        return SharedPreferencesManager().getSomeBooleanValue(Constantes().PREF_RECORDAR)
    }


    fun autenticarUsuario(vista: View, usuario: String, password: String){
        val requestLogin = RequestLogin(usuario, password)
        val call: Call<ResponseLogin> = PatitasCliente.retrofitService.login(requestLogin)
        call.enqueue(object :Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                binding.btnlogin.isEnabled = true
                val respuesta = response.body()!!
                if(respuesta.rpta){
                    val personaEntity = PersonaEntity(
                        respuesta.idpersona.toInt(),
                        respuesta.nombres,
                        respuesta.apellidos,
                        respuesta.email,
                        respuesta.celular,
                        respuesta.usuario,
                        respuesta.password,
                        respuesta.esvoluntario
                    )
                    if(verificarValorSharedPreferences()){
                        personaViewModel.actualizar(personaEntity)
                    }else{
                        personaViewModel.insertar(personaEntity)
                        if(binding.chkrecordar.isChecked){
                            SharedPreferencesManager().setSomeBooleanValue(Constantes().PREF_RECORDAR, true)
                        }
                    }
                    startActivity(Intent(applicationContext,
                        HomeActivity::class.java))
                    finish()
                }else{
                    mostrarMensaje(vista, response.body()!!.mensaje)

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