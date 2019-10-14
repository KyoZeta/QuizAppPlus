    package com.example.quizappplus.Vistas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import com.example.quizappplus.Modelos.Configuraciones
import com.example.quizappplus.R
import com.example.quizappplus.VistaModelos.ConfiguracionesVM
import com.example.quizappplus.VistaModelos.MenuPrincipalVM
import kotlinx.android.synthetic.main.activity_configuraciones.*

const val EXTRA_CONFIGURACINES = "com.example.quizappplus.Vistas.EXTRA_CONFIGURACIONES"
const val EXTRA_RESULT_CONFIUGRACIONES =
    "com.example.quizappplus.Vistas.EXTRA_RESULT_CONFIGURACIONES"
const val CONFIGURACIONES_REQUEST_CODE = 1000

class ConfiguracionesActivity : AppCompatActivity() {

    //region ControlesVista
    //CheckBoxes
    private lateinit var todosCheckBox: CheckBox
    private lateinit var cineCheckBox: CheckBox
    private lateinit var musicaCheckBox: CheckBox
    private lateinit var deporteCheckBox: CheckBox
    private lateinit var historiaCheckBox: CheckBox
    private lateinit var smashCheckBox: CheckBox
    private lateinit var variosCheckBox: CheckBox

    //Spinners
    private lateinit var numPreguntasSpinner: Spinner
    private lateinit var numPistasSpinner: Spinner

    //RadioButtons
    private lateinit var dificultadRadioGroup: RadioGroup
    private lateinit var facilRadioButton: RadioButton
    private lateinit var medioRadioButton: RadioButton
    private lateinit var dificilRadioButton: RadioButton

    //Switchs
    private lateinit var habilitarPistasSwitch: Switch

    //lista de checkboxes
    private lateinit var checkBoxes: List<CheckBox>
    //endregion

    private val model by lazy { ViewModelProviders.of(this)[ConfiguracionesVM::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuraciones)

        //region InicializadorControles
        todosCheckBox = findViewById(R.id.todos_checkbox)
        cineCheckBox = findViewById(R.id.cine_checkbox)
        musicaCheckBox = findViewById(R.id.musica_checkbox)
        deporteCheckBox = findViewById(R.id.deportes_checkbox)
        historiaCheckBox = findViewById(R.id.historia_checkbox)
        smashCheckBox = findViewById(R.id.smash_checkbox)
        variosCheckBox = findViewById(R.id.varios_checkbox)

        numPreguntasSpinner = findViewById(R.id.spinner_preguntas)
        numPistasSpinner = findViewById(R.id.spinner_pistas)

        dificultadRadioGroup = findViewById(R.id.dificultad_Radiogroup)
        facilRadioButton = findViewById(R.id.facil_radio_button)
        medioRadioButton = findViewById(R.id.medio_radio_button)
        dificilRadioButton = findViewById(R.id.dificil_radio_button)

        habilitarPistasSwitch = findViewById(R.id.enable_switch_pistas)

        //volverButton=findViewById(R.id.volver_Button)
        //endregion

        //Lista de los checkboxes que tenemos, exepto el de seleccionar todos. sirve para realizar
        //mas facilmente algunas actualizaciones.
        checkBoxes = listOf<CheckBox>(
            cineCheckBox,
            musicaCheckBox,
            smashCheckBox,
            deporteCheckBox,
            historiaCheckBox,
            variosCheckBox
        )

        //Asignar las configuraciones al modelo, que fueron pasadas por el activity main
        model.Inicializar(intent.getSerializableExtra(EXTRA_CONFIGURACINES) as Configuraciones)
        //Esta variable sirve solo como shortcut para no escribir mas
        val configuraciones = model.configuraciones

        //Actualizamos los controles con las configuraciones que nos pasaron
        SetConfiguraciones(configuraciones)

        //region Modificaciones
        //region checkboxes
        todosCheckBox.setOnClickListener {
            if (todosCheckBox.isChecked) ActivateAllCheckBoxes()
        }
        cineCheckBox.setOnClickListener {
            ActivateCheckBox(0, (it as CheckBox).isChecked)
        }
        musicaCheckBox.setOnClickListener {
            ActivateCheckBox(1, (it as CheckBox).isChecked)
        }
        smashCheckBox.setOnClickListener {
            ActivateCheckBox(2, (it as CheckBox).isChecked)
        }
        deporteCheckBox.setOnClickListener {
            ActivateCheckBox(3, (it as CheckBox).isChecked)
        }
        historiaCheckBox.setOnClickListener {
            ActivateCheckBox(4, (it as CheckBox).isChecked)
        }
        variosCheckBox.setOnClickListener {
            ActivateCheckBox(5, (it as CheckBox).isChecked)
        }
        //endregion
        //region switches
        habilitarPistasSwitch.setOnClickListener {
            spinner_pistas.isEnabled = habilitarPistasSwitch.isChecked
            configuraciones.pistas = habilitarPistasSwitch.isChecked
            ActualizarConfiguraciones()
        }
        //endregion
        //region spinners
        numPreguntasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                var1: AdapterView<*>,
                var2: View?,
                position: Int,
                var4: Long
            ) {
                ValidatorSpinnerPreguntas()
                ActualizarConfiguraciones()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        numPistasSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                var1: AdapterView<*>,
                var2: View?,
                position: Int,
                var4: Long
            ) {
                configuraciones.numPistas = spinner_pistas.selectedItem.toString().toInt()
                ActualizarConfiguraciones()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        //endregion
        //region radioboutons
        facilRadioButton.setOnClickListener {
            configuraciones.dificultad = 1
            ActualizarConfiguraciones()
        }
        medioRadioButton.setOnClickListener {
            configuraciones.dificultad = 2
            ActualizarConfiguraciones()
        }
        dificilRadioButton.setOnClickListener {
            configuraciones.dificultad = 3
            ActualizarConfiguraciones()
        }
        //endregion
        //endregion
    }

    private fun SetConfiguraciones(configuraciones: Configuraciones) {
        //Este ciclo pasa por la lista de configuraciones y pone los checkboxes correspondientes
        //a esa categoria activados si esa categoria esta seleccionada para su uso
        for (i in 0 until configuraciones.categorias.size) {
            if (configuraciones.categorias[i].seleccionada == true) {
                checkBoxes[i].isChecked = true
            }
        }
        //si todas estan seleccionadas, activa el checkbox de todos
        todosCheckBox.isChecked = model.numCategoriesSelected == 6 //se iguala a 6 por que el el numero de check boxes
        habilitarPistasSwitch.isChecked = configuraciones.pistas
        spinner_pistas.isEnabled = habilitarPistasSwitch.isChecked
        spinner_preguntas.setSelection(configuraciones.numPregunta - 5) //-5 por que la posición comienza en 0, pero el primer numero es 5
        spinner_pistas.setSelection(configuraciones.numPistas - 1) //-1 por que los numeros comienzan en 1 pero el arreglo en 0
        ChecarRadioButton(configuraciones.dificultad)
    }

    private fun ActivateAllCheckBoxes() {
        for (i in 0 until checkBoxes.size) {
            checkBoxes[i].isChecked = true
            model.configuraciones.categorias[i].seleccionada = true
        }
        model.numCategoriesSelected = 6
        ActualizarConfiguraciones()
    }

    //A esta funcion le paso el numero de checkbox que esta en el arreglo
    //Es importante que esten en el mismo orden que la lista que esta en configuracion
    //activado solo dice si al clickearlo se pudo como checkeado o no
    private fun ActivateCheckBox(idCheckBox: Int, activado: Boolean) {
        //Esta es una validación para que no pueda dejar ninguno seleccionado
        if (model.numCategoriesSelected == 1 && activado == false) {
            checkBoxes[idCheckBox].isChecked = true
            return
        }
        //Aqui cambia una propiedad del modelo que es el numero de categorias seleccionadas
        //Cambia dependiendo de si activaste o desactivaste un checkbox y sirve para no tener que
        //estar averiguando constantemente el numero
        model.numCategoriesSelected =
            if (activado) model.numCategoriesSelected + 1
            else model.numCategoriesSelected - 1
        //Si seleccionas todas, automaticamente se activa el checkbox de todos
        todosCheckBox.isChecked = model.numCategoriesSelected == 6
        //Cada que cambias de numero de categorias seleccionadas hay que ver si no infringes el numero
        //maximo de preguntas
        ValidatorSpinnerPreguntas()
        //aqui cambiamos el estado de la categoria en las configuraciones
        model.configuraciones.categorias[idCheckBox].seleccionada = activado
        ActualizarConfiguraciones()
    }

    //Esta funcion sirve para que cuando cambias algo, el modelo se actualiza para mandarlo de nuevo
    //Al activity main
    private fun ActualizarConfiguraciones() {
        val intent: Intent = Intent()
        intent.putExtra(EXTRA_RESULT_CONFIUGRACIONES, model.configuraciones)
        setResult(Activity.RESULT_OK, intent)
    }

    //Activa el radio button dependiendo de la dificultad seleccionada
    private fun ChecarRadioButton(id: Int) {
        when (id) {
            1 -> facilRadioButton.isChecked = true
            2 -> medioRadioButton.isChecked = true
            3 -> dificilRadioButton.isChecked = true
        }
    }

    //Valida el numero maximo de preguntas que se pueden poner dependiendo del numero de categorias
    //seleccionadas
    private fun ValidatorSpinnerPreguntas() {
        if (model.numCategoriesSelected > 1) {
            model.configuraciones.numPregunta = spinner_preguntas.selectedItem.toString().toInt()
        } else {
            model.configuraciones.numPregunta = 5
            spinner_preguntas.setSelection(0)
        }
    }

}
