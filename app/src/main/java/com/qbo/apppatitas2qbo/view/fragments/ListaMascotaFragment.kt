package com.qbo.apppatitas2qbo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.FragmentListaMascotaBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseMascota
import com.qbo.apppatitas2qbo.view.HomeActivity
import com.qbo.apppatitas2qbo.view.adapters.MascotaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaMascotaFragment : Fragment() {

    private var _binding: FragmentListaMascotaBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentListaMascotaBinding.inflate(inflater, container, false)
        binding.rvmascotas.layoutManager = LinearLayoutManager(
            requireActivity())
        listarMascotas()
        return binding.root
    }

    fun listarMascotas(){
        val call: Call<List<ResponseMascota>> = PatitasCliente.retrofitService.listarMascota()
        call.enqueue(object : Callback<List<ResponseMascota>>{
            override fun onResponse(
                call: Call<List<ResponseMascota>>,
                response: Response<List<ResponseMascota>>
            ) {
                binding.rvmascotas.adapter = MascotaAdapter(response.body()!!)
            }
            override fun onFailure(call: Call<List<ResponseMascota>>, t: Throwable) {

            }
        })
    }

}