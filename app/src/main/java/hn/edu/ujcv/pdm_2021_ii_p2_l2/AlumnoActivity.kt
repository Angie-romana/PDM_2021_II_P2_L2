package hn.edu.ujcv.pdm_2021_ii_p2_l2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_alumno.*
import android.content.Intent
import android.widget.Toast

class AlumnoActivity : AppCompatActivity() {
    var alumno: HashMap<Int,String> = hashMapOf()
    var numero = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alumno)
        inicializarAlumno()
      btnRegistrar.setOnClickListener { guardarRegistro() }
        llenarDatos()
        btnEnviar.setOnClickListener { enviarDatos() }
        txvIconNumCuenta.setOnClickListener{borrar()}

    }
    private fun borrar() {
        txtCuenta.setText("")
        txtNombre.setText("")
        txtCorreo.setText("")
    }
    private fun enviarDatos() {
        val intent = Intent(this,ClaseActivity::class.java)
        intent.putExtra("alumnos",alumno)
        startActivity(intent)
    }

    private fun inicializarAlumno() {
        btnRegistrar.isEnabled = true

    }
    fun minLength():Boolean{
        if(txtNombre.text.toString().length<3)
        {
            txtNombre.error = " El nombre no puede tener menos de 3 caracteres"
            return false
        }
        if(txtCuenta.text.toString().length<10)
        {
            txtCuenta.error = "El numero de cuenta no puede tener menos de 6 caracteres"
            return false
        }
        if(txtCorreo.text.toString().length<15)
        {
            txtCorreo.error =" El correo no puede tener menos de 15 caracteres"
            return false
        }
        return true
    }

    private fun guardarRegistro() {
        val dato = StringBuilder()
        numero+=1
        dato.append(txtCuenta.text.toString().trim()).append("|")
        dato.append(txtNombre.text.toString().trim()).append("|")
        dato.append(txtCorreo.text.toString().trim()).append("|")
        alumno.put(numero, dato.toString())
        btnRegistrar.isEnabled = true
        minLength()
        datosVacios()

        if(datosVacios()== true && minLength() == true)
        {

            Toast.makeText(this, "Se ha enviado exitosamente", Toast.LENGTH_SHORT).show()
        }

    }
    fun datosVacios():Boolean
    {
        if (txtNombre.text.toString().isEmpty())
        {
            txtNombre.error = "Debe ingresar un nombre"
            return false
        }
        if(txtCuenta.text.toString().isEmpty())
        {
            txtCuenta.error = "Debe ingresar un numero de cuenta"
            return false
        }
        if(txtCorreo.text.toString().isEmpty())
        {
            txtCorreo.error = "Debe ingresar un correo"
            return false
        }
        return true
    }

    private fun llenarDatos() {
        var intent = intent
        var cuenta : String
        var nombre : String
        var correo : String
        for(valor in alumno)
        {
            val lista = valor.toString().split("|").toTypedArray()
            cuenta = lista[1].toString()
            nombre = lista[2].toString()
            correo = lista[3].toString()

        }


    }



}