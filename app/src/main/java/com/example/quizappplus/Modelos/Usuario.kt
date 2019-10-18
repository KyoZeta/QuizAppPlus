package com.example.quizappplus.Modelos

import com.example.quizappplus.DB.AppDatabase
import com.example.quizappplus.DB.Entidades.JuegoEntity
import java.io.Serializable

data class Usuario(var nombre:String, var puntuacion:Int, var usoCheats:Boolean = false, var posicion:Int, var porsentaje:Double = 0.0):Serializable{

// Comentado por si las moscas
/*    fun GetGameStatus(db:AppDatabase,idUsuario:Int):Boolean{
        val juegoActual:JuegoEntity = db.getJuegoDao().GetJuegoByUserId(idUsuario)
        return juegoActual.estatusJuego==1
    }*/
}