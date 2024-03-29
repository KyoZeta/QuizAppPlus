package com.example.quizappplus.VistaModelos

import androidx.lifecycle.ViewModel
import com.example.quizappplus.DB.AppDatabase
import com.example.quizappplus.Modelos.Configuraciones
import com.example.quizappplus.Modelos.Usuario

class MenuPrincipalVM:ViewModel(){

    var preguntarIdentidad = true

    //Inicializamos las configuraciones con sus valores base
    var configuraciones: Configuraciones =
        Configuraciones()

    //Inicializamos los puntajes para pasarlos por toda la aplicación
    var mejoresPuntajes:ArrayList<Usuario> = arrayListOf()

    //Esta bandera se cambia a true cuando el juego comienza
    //con eso no reiniciamos las configuraciones ni los puntajes cada que se gira la pantalla o algo asi
    private var flagInicioJuego:Boolean = false

    private var idUsuarioActivo : Int? = null

    private lateinit var database : AppDatabase

    //propiedad para obtener el valor de la bandera
    val FlagInicio get() = flagInicioJuego

    //Solo con este metodo podemos cambiar el estado de la bandera a true
    fun SetFlagInicioJuego()
    {
        flagInicioJuego = true
    }

    fun InicializarJuego(database : AppDatabase)
    {
        if (FlagInicio == false){
            SetFlagInicioJuego()
        }

        this.database = database
    }

    /**
     * @brief Returna true si hay algun juego guardado
     */
    fun existeJuegoGuardado() : Boolean {
        val juegoActual = database.getJuegoDao().GetJuego(getIdUsurioActivo())
        return juegoActual.estatusJuego == 1
    }

    /**
     * @brief Elimina el juego guardado anteriormente si existe
     */
    fun eliminarJuegoGuardado() {
        Usuario.FinishGame(database, getIdUsurioActivo())
    }

    fun setIdUsuarioActivo(id : Int) {
        idUsuarioActivo = id
    }

    fun getIdUsurioActivo() : Int {
        return idUsuarioActivo!!
    }

    fun getUserName() : String {
        return database.getUsuarioDao().getUsuarioById(getIdUsurioActivo()).userName
    }
}