package com.example.quizappplus.Vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProviders
import com.example.quizappplus.DB.AppDatabase
import com.example.quizappplus.R
import com.example.quizappplus.VistaModelos.PerfilUsuarioVM

const val PERFIL_EDITAR_USUARIO = 123

class PerfilJugador : AppCompatActivity() {

    private lateinit var perfil_photo : ImageView
    private lateinit var perfil_username : EditText
    private lateinit var perfil_userpassword : EditText
    private lateinit var perfil_editarperfil : Button

    private val model by lazy { ViewModelProviders.of(this)[PerfilUsuarioVM::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_jugador)

        perfil_photo = findViewById(R.id.perfil_photo)
        perfil_username = findViewById(R.id.perfil_username)
        perfil_userpassword = findViewById(R.id.perfil_userpassword)
        perfil_editarperfil = findViewById(R.id.perfil_editarperfil)

        model.inicializar(AppDatabase.getAppDatabase(this))

        if(model.photoid != null) {
            perfil_photo.setImageDrawable(getDrawable(model.photoid as Int))
        }
        perfil_username.setText(model.userName)
        perfil_userpassword.setText(model.userPassword)

        perfil_editarperfil.setOnClickListener {
            val toCrarUsuarioIntent = Intent(this, RegistrarUsuario::class.java)
            toCrarUsuarioIntent.putExtra("editarJugador", true)
            startActivityForResult(toCrarUsuarioIntent, PERFIL_EDITAR_USUARIO)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PERFIL_EDITAR_USUARIO) {
            model.inicializar(AppDatabase.getAppDatabase(this))
            perfil_username.setText(model.userName)
            perfil_userpassword.setText(model.userPassword)
            perfil_photo.setImageDrawable(getDrawable(model.photoid as Int))
        }
    }
}
