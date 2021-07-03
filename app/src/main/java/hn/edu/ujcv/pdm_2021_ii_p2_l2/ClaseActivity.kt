package hn.edu.ujcv.pdm_2021_ii_p2_l2

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_clase.*
import java.util.*

class ClaseActivity : AppCompatActivity() {
    var clase: HashMap<Int,String> = hashMapOf()
    var numero = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase)
        inicializar()
        llenarDatos()
        txtHora.setOnClickListener{showTimePicker()}
        btnRegClase.setOnClickListener {guardarRegistro() }
    }

    private fun showTimePicker() {
        val mcurrentTime = Calendar.getInstance()
        var hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        var minute = mcurrentTime[Calendar.MINUTE]

       /* val dpd = DatePickerDialog(this, { view, mAño, mMes, mDia ->
            txtFechaPublicacion.setText(""+mDia+"/"+(mMes.toInt() +1)+"/"+mAño)

        },año,mes-1,dia)
        dpd.show()*/
        val timePickerDialog = TimePickerDialog(this, AlertDialog.THEME_HOLO_DARK,
            {timePicker,selectedHour, selectedMinute,  -> txtHora.setText(" " + hour+ ":" + minute)} ,hour,minute,false)

       // val timePickerDialog = TimePickerDialog( this,  AlertDialog.THEME_HOLO_DARK,
            //{ timePicker, selectedHour, selectedMinute,  -> } ,hour,minute, false

       // )

        timePickerDialog.show()

    }


   private fun inicializar() {
        btnRegClase.isEnabled = true
    }
    private fun guardarRegistro() {
        val dato = StringBuilder()
        numero+=1
        dato.append(txtCodigo.text.toString().trim()).append("|")
        dato.append(txtNombreC.text.toString().trim()).append("|")
        dato.append(txtSeccion.text.toString().trim()).append("|")
        dato.append(txtHora.text.toString().trim()).append("|")
        dato.append(txtEdificio.text.toString().trim()).append("|")
        dato.append(txtPiso.text.toString().trim()).append("|")
        dato.append(txtAula.text.toString().trim()).append("|")
        clase.put(numero, dato.toString())
        minLength()
        datosVacios()
        btnRegClase.isEnabled = true

       if(datosVacios()== true && minLength() == true)
       {

           Toast.makeText(this, "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show()
       }


    }
    fun minLength():Boolean{
        if(txtNombreC.text.toString().length<3)
        {
            txtNombreC.error = " El nombre no puede tener menos de 3 caracteres"
            return false
        }
        if(txtCodigo.text.toString().length<10)
        {
            txtCodigo.error = "El codigo no puede tener menos de 10 caracteres"
            return false
        }
        if(txtSeccion.text.toString().length<1)
        {
            txtSeccion.error =" La seccion no puede estar vacio"
            return false
        }
        if(txtHora.text.toString().length<10)
        {
            txtHora.error =" La hora no puede estar vacio"
            return false
        }
        if(txtEdificio.text.toString().length<1)
        {
            txtEdificio.error ="El edificio no puede estar vacio"
            return false
        }
        if(txtPiso.text.toString().length<1)
        {
            txtPiso.error =" El piso no puede estar vacio"
            return false
        }
        if(txtAula.text.toString().length<1)
        {
            txtSeccion.error =" El aula no puede estar vacio"
            return false
        }

        return true
    }
    fun datosVacios() :Boolean
    {
        if (txtNombreC.text.toString().isEmpty())
        {
            txtNombreC.error = " Debe ingresar un nombre"
            return false
        }
        if(txtCodigo.text.toString().isEmpty())
        {
            txtCodigo.error =  "Debe ingresar un codigo"
            return false
        }
        if(txtSeccion.text.toString().isEmpty())
        {
           txtSeccion.error =" Debe ingresar una seccion"
            return false
        }
        if(txtHora.text.toString().isEmpty())
        {
           txtHora.error = "Debe ingresar la hora"
            return false
        }
        if(txtEdificio.text.toString().isEmpty())
        {
           txtEdificio.error ="Debe ingresar el edificio"
            return false
        }
        if(txtPiso.text.toString().isEmpty())
        {
            txtPiso.error = "Debe ingresar el piso"
            return false
        }
        if(txtAula.text.toString().isEmpty())
        {
            txtAula.error = "Debe ingresar el aula"
            return false
        }
return true
    }
    private fun llenarDatos() {

        var codigo  : String
        var nombre  : String
        var seccion : String
        var hora    : String
        var edificio : String
        var piso     : String
        var aula     : String
        txtCodigo.setText("")
        txtNombreC.setText("")
        txtHora.setText("")
        txtSeccion.setText("")
        txtEdificio.setText("")
        txtPiso.setText("")
        txtAula.setText("")

        for(valor in clase)
        {
            val lista = valor.toString().split("|").toTypedArray()
            codigo    = lista[1].toString()
            nombre    = lista[2].toString()
            seccion   = lista[3].toString()
            hora      = lista[4].toString()
            edificio  = lista[5].toString()
            piso      = lista[6].toString()
            aula      = lista[7].toString()


        }


    }
}