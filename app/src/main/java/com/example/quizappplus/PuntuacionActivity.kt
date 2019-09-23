package com.example.quizappplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView

class PuntuacionActivity : AppCompatActivity() {

    //region objetosVista
    private lateinit var listaPuntuaciones:ListView

    private lateinit var menuPrincipalButton:Button
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntuacion)

        //region InicializarControles
        listaPuntuaciones=findViewById(R.id.lista_puntuaciones)
        menuPrincipalButton=findViewById(R.id.menu_puntuaciones_button)
        //endregion
    }
}