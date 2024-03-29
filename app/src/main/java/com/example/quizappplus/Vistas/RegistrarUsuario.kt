package com.example.quizappplus.Vistas

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.quizappplus.DB.AppDatabase
import com.example.quizappplus.R
import com.example.quizappplus.VistaModelos.RegistrarUsuarioVM
import kotlinx.android.synthetic.main.activity_iniciar_sesion.*

class RegistrarUsuario : AppCompatActivity() {

    private lateinit var imgselect1_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect2_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect3_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect4_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect5_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect6_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect7_imageButton_registrarUsuiario : ImageButton
    private lateinit var imgselect8_imageButton_registrarUsuiario : ImageButton

    private lateinit var crearcuenta_title : TextView

    private lateinit var Usuario_editView_registrarUsuario : EditText
    private lateinit var password_editView_registrarUsuario : EditText

    private lateinit var terminarRegistro_button_registrarUsuario : Button

    private lateinit var waifuList : MutableList<ImageButton>

    private val model by lazy { ViewModelProviders.of(this)[RegistrarUsuarioVM::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_usuario)

        crearcuenta_title = findViewById(R.id.crearcuenta_title)

        imgselect1_imageButton_registrarUsuiario = findViewById(R.id.imgselect1_imageButton_registrarUsuiario)
        imgselect1_imageButton_registrarUsuiario.tag = R.drawable.kurisutina

        imgselect2_imageButton_registrarUsuiario = findViewById(R.id.imgselect2_imageButton_registrarUsuiario)
        imgselect2_imageButton_registrarUsuiario.tag = R.drawable.kuroneko

        imgselect3_imageButton_registrarUsuiario = findViewById(R.id.imgselect3_imageButton_registrarUsuiario)
        imgselect3_imageButton_registrarUsuiario.tag = R.drawable.kanna

        imgselect4_imageButton_registrarUsuiario = findViewById(R.id.imgselect4_imageButton_registrarUsuiario)
        imgselect4_imageButton_registrarUsuiario.tag = R.drawable.gears

        imgselect5_imageButton_registrarUsuiario = findViewById(R.id.imgselect5_imageButton_registrarUsuiario)
        imgselect5_imageButton_registrarUsuiario.tag = R.drawable.norespawn

        imgselect6_imageButton_registrarUsuiario = findViewById(R.id.imgselect6_imageButton_registrarUsuiario)
        imgselect6_imageButton_registrarUsuiario.tag = R.drawable.albedo

        imgselect7_imageButton_registrarUsuiario = findViewById(R.id.imgselect7_imageButton_registrarUsuiario)
        imgselect7_imageButton_registrarUsuiario.tag = R.drawable.raphtalia

        imgselect8_imageButton_registrarUsuiario = findViewById(R.id.imgselect8_imageButton_registrarUsuiario)
        imgselect8_imageButton_registrarUsuiario.tag = R.drawable.mododiablo

        Usuario_editView_registrarUsuario = findViewById(R.id.Usuario_editView_registrarUsuario)
        password_editView_registrarUsuario = findViewById(R.id.password_editView_registrarUsuario)

        waifuList = mutableListOf(imgselect1_imageButton_registrarUsuiario, imgselect2_imageButton_registrarUsuiario,
                imgselect3_imageButton_registrarUsuiario, imgselect4_imageButton_registrarUsuiario, imgselect5_imageButton_registrarUsuiario,
                imgselect6_imageButton_registrarUsuiario, imgselect7_imageButton_registrarUsuiario, imgselect8_imageButton_registrarUsuiario)

        terminarRegistro_button_registrarUsuario = findViewById(R.id.terminarRegistro_button_registrarUsuario)

        model.inicializar(AppDatabase.getAppDatabase(this))

        // Recibir Intent
        if (intent.getBooleanExtra("editarJugador", false)) {
            crearcuenta_title.text = "Editar Perfil"

            model.actualizarDatosUsuarioActivo()
        }

        val waifuOnClickEvent = View.OnClickListener {

            for (waffle in waifuList) {
                waffle.isEnabled = true
                waffle.alpha = 1f
            }

            it.isEnabled = false
            it.alpha = 0.5f

            val imageButton = it as ImageButton

            model.selectedWaifu = imageButton.tag as Int
        }

        for (waffle in waifuList) {
            waffle.setOnClickListener(waifuOnClickEvent)

            if (waffle.tag == model.selectedWaifu) {
                waffle.alpha = 0.5f
            }

            else {
                waffle.alpha = 1.0f
            }
        }

        Usuario_editView_registrarUsuario.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                model.userNameText = p0?.toString() ?: ""
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userNameText = p0?.toString() ?: ""
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userNameText = p0?.toString() ?: ""
            }
        })

        password_editView_registrarUsuario.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                model.userPasswordText = p0?.toString() ?: ""
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userPasswordText = p0?.toString() ?: ""
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                model.userPasswordText = p0?.toString() ?: ""
            }
        })

        terminarRegistro_button_registrarUsuario.setOnClickListener {
            if (validarInputs()) {

                val toastMessage = if (model.editarUsuarioActivity) "Datos Actualizados" else "Registro Exitoso!"
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()

                if (model.editarUsuarioActivity) {
                    setResult(PERFIL_EDITAR_USUARIO)
                }

                finish()
            }

            else {
                // Mostramos un alert dialog
                val message = if (Usuario_editView_registrarUsuario.text.isBlank() || password_editView_registrarUsuario.text.isBlank() || model.selectedWaifu == 0)
                    "Favor de llenar todos los campos"
                else
                    "Nombre de usuario no disponible"

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage(message)

                builder.setPositiveButton("Ok") { _, _ ->

                }

                builder.show()
            }
        }

        Usuario_editView_registrarUsuario.setText(model.userNameText)
        password_editView_registrarUsuario.setText(model.userPasswordText)
    }

    private fun validarInputs() : Boolean {

        if (Usuario_editView_registrarUsuario.text.isBlank() || password_editView_registrarUsuario.text.isBlank()) {
            return false
        }

        return model.tryRegistrarUsuario()
    }
}
