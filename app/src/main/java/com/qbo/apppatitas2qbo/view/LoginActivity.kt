package com.qbo.apppatitas2qbo.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.ActivityLoginBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.db.entity.PersonaEntity
import com.qbo.apppatitas2qbo.utilitarios.*
import com.qbo.apppatitas2qbo.viewmodel.AuthViewModel
import com.qbo.apppatitas2qbo.viewmodel.PersonaViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    //3.1 Definimos el viewmodel
    private lateinit var personaViewModel: PersonaViewModel
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //3.2 Realizamos la instancia de ViewModelProvider
        personaViewModel = ViewModelProvider(this)
            .get(PersonaViewModel::class.java)
        authViewModel = ViewModelProvider(this)
            .get(AuthViewModel::class.java)
        //4.1 Validamos que exista la preferencia recordardatos
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
        //5.1 Crear el evento click Check
        binding.chkrecordar.setOnClickListener {
            setearValoresDeRecordar(it)
        }
        binding.btnlogin.setOnClickListener {
            validarUsuarioPassword()
        }
        binding.btnregistrar.setOnClickListener {
            startActivity(Intent(applicationContext,
                RegistroActivity::class.java))
        }
        authViewModel.responseLogin.observe(this, Observer {
            obtenerDatosLogin(it)
        })
    }
    //5.2. Seateamos los valores cuando quitamos el check de recordar datos
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
    //3.3 Crear método para validar los valores de SharedPreferences.
    fun verificarValorSharedPreferences(): Boolean{
        return SharedPreferencesManager().getSomeBooleanValue(Constantes().PREF_RECORDAR)
    }

    fun autenticarUsuario(usuario: String, password: String){
        authViewModel.autenticarUsuario(usuario, password)
    }

    fun obtenerDatosLogin(responseLogin: ResponseLogin){
        if(responseLogin.rpta){
            //6.1 Guardar Información en SQLite
            val personaEntity = PersonaEntity(
                responseLogin.idpersona.toInt(), responseLogin.nombres,
                responseLogin.apellidos, responseLogin.email, responseLogin.celular,
                responseLogin.usuario, responseLogin.password, responseLogin.esvoluntario
            )
            if(verificarValorSharedPreferences()){
                personaViewModel.actualizar(personaEntity)
            }else{
                personaViewModel.insertar(personaEntity)
                if(binding.chkrecordar.isChecked){
                    SharedPreferencesManager().setSomeBooleanValue(Constantes().PREF_RECORDAR, true)
                }
            }
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finish()
        }else{
            AppMensaje.enviarMensaje(binding.root, responseLogin.mensaje, TipoMensaje.ERROR)
        }
        binding.btnlogin.isEnabled = true
    }

    //2. Método que valida el ingreso de usuario y password.
    fun validarUsuarioPassword(){
        binding.btnlogin.isEnabled = false
        var okLogin = true
        if(binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            okLogin = false
        } else if(binding.etpassword.text.toString().trim().isEmpty()){
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            okLogin = false
        }
        if(okLogin)
            autenticarUsuario(binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
        else{
            binding.btnlogin.isEnabled = true
            AppMensaje.enviarMensaje(binding.root,
                getString(R.string.msguspassword), TipoMensaje.ERROR)
        }
    }
}