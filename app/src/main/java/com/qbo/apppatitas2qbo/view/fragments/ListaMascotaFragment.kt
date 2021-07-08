package com.qbo.apppatitas2qbo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.FragmentListaMascotaBinding
import com.qbo.apppatitas2qbo.retrofit.PatitasCliente
import com.qbo.apppatitas2qbo.retrofit.request.RequestLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseLogin
import com.qbo.apppatitas2qbo.retrofit.response.ResponseMascota
import com.qbo.apppatitas2qbo.view.HomeActivity
import com.qbo.apppatitas2qbo.view.adapters.MascotaAdapter
import com.qbo.apppatitas2qbo.viewmodel.AuthViewModel
import com.qbo.apppatitas2qbo.viewmodel.MascotaViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaMascotaFragment : Fragment() {

    private var _binding: FragmentListaMascotaBinding? = null
    private val binding get() = _binding!!
    private lateinit var mascotaViewModel: MascotaViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentListaMascotaBinding.inflate(inflater, container, false)
        binding.rvmascotas.layoutManager = LinearLayoutManager(
            requireActivity())
        mascotaViewModel = ViewModelProvider(requireActivity())
            .get(MascotaViewModel::class.java)
        listarMascotas()
        return binding.root
    }

    fun listarMascotas(){
        mascotaViewModel.listarMascotas().observe(viewLifecycleOwner,
            Observer {
                binding.rvmascotas.adapter = MascotaAdapter(it)
        })
    }

}