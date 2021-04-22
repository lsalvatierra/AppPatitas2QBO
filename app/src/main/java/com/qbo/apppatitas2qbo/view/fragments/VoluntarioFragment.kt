package com.qbo.apppatitas2qbo.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.FragmentListaMascotaBinding
import com.qbo.apppatitas2qbo.databinding.FragmentVoluntarioBinding
import com.qbo.apppatitas2qbo.db.entity.PersonaEntity
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestVoluntario
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro
import com.qbo.apppatitas2qbo.viewmodel.PersonaViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class VoluntarioFragment : Fragment() {

    private var _binding: FragmentVoluntarioBinding? = null
    private val binding get() = _binding!!
    //Definimos el viewmodel
    private lateinit var personaViewModel: PersonaViewModel
    //Definimos el objeto PersonaEntity
    private lateinit var personaEntity: PersonaEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentVoluntarioBinding.inflate(inflater, container, false)

        //Realizamos la instancia de ViewModelProvider
        val vista = inflater.inflate(R.layout.fragment_voluntario, container, false)
        personaViewModel = ViewModelProvider(requireActivity()).get(PersonaViewModel::class.java)
        personaViewModel.obtener()
                .observe(viewLifecycleOwner, Observer { persona ->
                    // Update the cached copy of the words in the adapter.
                    persona?.let {
                        if(persona.esvoluntario == "1"){
                            actualizarFormulario()
                        }else{
                            personaEntity = persona
                        }
                    }
                })

        binding.btnregvoluntario.setOnClickListener {
            if(binding.chkaceptocondiciones.isChecked){
                binding.btnregvoluntario.isEnabled = false
                registrarVoluntario(it)
            }else{
                mostrarMensaje(it, "Acepte los términos y condiciones para ser voluntario")
            }
        }

        return binding.root
    }

    private fun registrarVoluntario(vista: View) {
        val call: Call<ResponseRegistro> = PatitasCliente
            .retrofitService.registrarVoluntario(RequestVoluntario(personaEntity.id))
        call.enqueue(object : Callback<ResponseRegistro> {
            override fun onResponse(
                call: Call<ResponseRegistro>,
                response: Response<ResponseRegistro>
            ) {
                if (response.body()!!.rpta) {
                    val nuevaPersonaEntity = PersonaEntity(
                            personaEntity.id,
                            personaEntity.nombres,
                            personaEntity.apellidos,
                            personaEntity.email,
                            personaEntity.celular,
                            personaEntity.usuario,
                            personaEntity.password,
                            "1"
                    )
                    personaViewModel.actualizar(nuevaPersonaEntity)
                    actualizarFormulario()
                }
                mostrarMensaje(vista, response.body()!!.mensaje)
                binding.btnregvoluntario.isEnabled = true
            }

            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {
                binding.btnregvoluntario.isEnabled = true
            }

        })
    }

    //2. Actualizar controles del formulario
    private fun actualizarFormulario() {
        binding.tvtextovoluntario.visibility = View.GONE
        binding.btnregvoluntario.visibility = View.GONE
        binding.chkaceptocondiciones.visibility = View.GONE
        binding.tvtituvoluntario.text = getString(R.string.valtvesuvoluntario)

    }

    //1. Creamos método para enviar nuestros mensajes
    fun mostrarMensaje(vista: View, mensaje: String){
        Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG).show()
    }

}