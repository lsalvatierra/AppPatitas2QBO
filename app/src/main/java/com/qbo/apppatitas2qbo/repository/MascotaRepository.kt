package com.qbo.apppatitas2qbo.repository

import androidx.lifecycle.MutableLiveData
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestVoluntario
import com.qbo.apppatitas2qbo.retrofit.response.ResponseMascota
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro
import com.qbo.apppatitas2qbo.view.adapters.MascotaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MascotaRepository {

    var responseMascota = MutableLiveData<List<ResponseMascota>>()
    var responseRegistro = MutableLiveData<ResponseRegistro>()

    fun listarMascotas() : MutableLiveData<List<ResponseMascota>> {
        val call: Call<List<ResponseMascota>> = PatitasCliente.retrofitService.listarMascota()
        call.enqueue(object : Callback<List<ResponseMascota>> {
            override fun onResponse(
                call: Call<List<ResponseMascota>>,
                response: Response<List<ResponseMascota>>
            ) {
                responseMascota.value = response.body()
            }
            override fun onFailure(call: Call<List<ResponseMascota>>, t: Throwable) {

            }
        })
        return responseMascota
    }

    fun registrarVoluntario(idPersona: Int) : MutableLiveData<ResponseRegistro>
    {
        val call: Call<ResponseRegistro> = PatitasCliente
            .retrofitService.registrarVoluntario(RequestVoluntario(idPersona))
        call.enqueue(object : Callback<ResponseRegistro>{
            override fun onResponse(
                call: Call<ResponseRegistro>,
                response: Response<ResponseRegistro>
            ) {
                responseRegistro.value = response.body()
            }
            override fun onFailure(call: Call<ResponseRegistro>, t: Throwable) {

            }
        })
        return responseRegistro
    }
}