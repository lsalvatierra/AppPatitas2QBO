package com.qbo.apppatitas2qbo.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qbo.apppatitas2qbo.R
import com.qbo.apppatitas2qbo.databinding.FragmentListaMascotaBinding
import com.qbo.apppatitas2qbo.databinding.ItemMascotaBinding
import com.qbo.apppatitas2qbo.retrofit.response.ResponseMascota

class MascotaAdapter (private var lstmascotas:List<ResponseMascota>)
    : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemMascotaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMascotaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(lstmascotas[position]){
                binding.tvnommascota.text = nommascota
                binding.tvcontacto.text  = contacto
                binding.tvfechaperdida.text  = fechaperdida
                binding.tvlugar.text  = lugar
                Glide.with(holder.itemView.context)
                    .load(urlimagen)
                    .into(binding.ivmascota)
            }
        }
    }

    override fun getItemCount()= lstmascotas.size
}


