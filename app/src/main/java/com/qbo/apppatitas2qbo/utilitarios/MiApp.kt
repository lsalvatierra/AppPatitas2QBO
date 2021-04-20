package com.qbo.apppatitas2qbo.utilitarios

import android.app.Application
import android.content.Context

class MiApp : Application() {

    private lateinit var instancia: MiApp

    fun getInstance(): MiApp? {
        return instancia
    }

    fun getContext(): Context {
        return instancia
    }

    override fun onCreate() {
        instancia = this
        super.onCreate()
    }
}