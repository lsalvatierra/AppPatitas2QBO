package com.qbo.apppatitas2qbo.utilitarios

import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.qbo.apppatitas2qbo.R

object AppMensaje {

    fun enviarMensaje(vista: View, mensaje: String, tipo: TipoMensaje){
        val snackbar = Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG)
        val snackbarView: View = snackbar.view
        if(tipo == TipoMensaje.ERROR){
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(MiApp.INSTANCE,
                    R.color.snackbarerror)
            )
        }else{
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(MiApp.INSTANCE,
                    R.color.snackbarexito)
            )
        }
        snackbar.show()
    }
}