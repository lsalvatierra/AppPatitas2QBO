package com.qbo.apppatitas2qbo.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.qbo.apppatitas2qbo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Un controlador que permite enviar y procesar objetos
        //Message y Runnable
        //Cada instancia de Handler está asociada con un solo hilo
        //y la cola de mensajes de ese hilo
        Handler().postDelayed({
            //Runnable que se agrega a la cola de mensajes para que se
            //ejecute una vez transcurrido el tiempo especificado
            // Start your app main activity
            startActivity(Intent(this,LoginActivity::class.java))
            // close this activity
            finish()
        }, 6000)
    }
}