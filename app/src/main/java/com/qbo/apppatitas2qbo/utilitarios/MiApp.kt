package com.qbo.apppatitas2qbo.utilitarios

import android.app.Application
import android.content.Context

class MiApp : Application() {
    init { INSTANCE = this }

    companion object {
        lateinit var INSTANCE: MiApp
            private set

        val applicationContext: Context get() { return INSTANCE.applicationContext }
    }
}