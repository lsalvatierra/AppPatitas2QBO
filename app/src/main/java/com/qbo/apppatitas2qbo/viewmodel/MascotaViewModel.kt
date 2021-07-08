package com.qbo.apppatitas2qbo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.qbo.apppatitas2qbo.repository.MascotaRepository
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseMascota
import com.qbo.apppatitas2qbo.retrofit.response.ResponseRegistro

class MascotaViewModel: ViewModel() {

    private var repository = MascotaRepository()
    var responseRegistro: LiveData<ResponseRegistro> = repository.responseRegistro

    fun listarMascotas(): LiveData<List<ResponseMascota>>
    {
        return repository.listarMascotas()
    }

    fun registrarVoluntario(idPersona : Int) {
        responseRegistro = repository.registrarVoluntario(idPersona)
    }

}