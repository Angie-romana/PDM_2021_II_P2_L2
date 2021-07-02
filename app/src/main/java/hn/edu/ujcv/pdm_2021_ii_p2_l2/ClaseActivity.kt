package hn.edu.ujcv.pdm_2021_ii_p2_l2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_alumno.*
import kotlinx.android.synthetic.main.activity_clase.*

class ClaseActivity : AppCompatActivity() {
    var clase: HashMap<Int,String> = hashMapOf()
    var numero = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clase)
        inicializar()
        llenarDatos()

        btnRegClase.setOnClickListener { guardarRegistro() }

    }



    private fun inicializar() {
        btnRegClase.isEnabled = false
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
        btnRegClase.isEnabled = true
        minLength()
        datosVacios()

    }
    fun minLength():Boolean{
        if(txtNombreC.text.toString().length<3)
        {
            txtNombreC.error = " El nombre no puede tener menos de 3 caracteres"
            return false
        }
        if(txtCodigo.text.toString().length<6)
        {
            txtCodigo.error = "El codigo no puede tener menos de 6 caracteres"
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

    fun datosVacios()
    {
        if (txtNombreC.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar un nombre", Toast.LENGTH_SHORT).show()
        }
        if(txtCodigo.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar un codigo", Toast.LENGTH_SHORT).show()
        }
        if(txtSeccion.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar la seccion", Toast.LENGTH_SHORT).show()
        }
        if(txtHora.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar la hora", Toast.LENGTH_SHORT).show()
        }
        if(txtEdificio.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar el edificio", Toast.LENGTH_SHORT).show()
        }
        if(txtPiso.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar el piso", Toast.LENGTH_SHORT).show()
        }
        if(txtAula.text.toString().isEmpty())
        {
            Toast.makeText(this,"Debe ingresar el aula", Toast.LENGTH_SHORT).show()
        }

    }
    private fun llenarDatos() {
        var intent = intent
        var codigo  : String
        var nombre  : String
        var seccion : String
        var hora    : String
        var edificio : String
        var piso     : String
        var aula     : String

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