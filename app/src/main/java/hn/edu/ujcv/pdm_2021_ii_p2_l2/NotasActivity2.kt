package hn.edu.ujcv.pdm_2021_ii_p2_l2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_notas2.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.ArrayList as CollectionsArrayList

class NotasActivity2 : AppCompatActivity() {

    var alumno: HashMap<Int, String> = hashMapOf()
    var clase: HashMap<Int, String> = hashMapOf()
    var matricula: HashMap<Int, String> = hashMapOf()
    var notas:HashMap<Int, String> = hashMapOf()
    var numero = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas2)

        inicializar()
        llenarSpiner()
        guardarNotas()
        // llenarlist()

    }

    private fun inicializar() {
        var intent = intent
        alumno = intent.getSerializableExtra("alumnos") as HashMap<Int, String>
        clase = intent.getSerializableExtra("clases") as HashMap<Int, String>
        matricula = intent.getSerializableExtra("matriculas") as HashMap<Int, String>
        var nota1= "0.0"
        var nota2 ="0.0"
        var nota3= "0.0"
        txtNota.setText(nota1)
        txtNota1.setText(nota2)
        txtNota2.setText(nota3)

        txtNota.isEnabled=false
        txtNota1.isEnabled=false
        txtNota2.isEnabled=false

    }


    fun llenarSpiner() {
        val default = "Seleccione un número de cuenta"
        var datos = CollectionsArrayList<String>()
        datos.add(default)
        for (alumnos in alumno) {
            val lista = alumnos.toString().split("|", "=")
            var numeroCuenta = lista[1]
            var nombre = lista[2]
            var dato = numeroCuenta + " - " + nombre
            datos.add(dato)
        }
        var spinner = findViewById<Spinner>(R.id.spnNumeroCuenta)
        var adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, datos)
        spinner.adapter = adaptador

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var datos2 = CollectionsArrayList<String>()
                var default= "Clases Matriculadas"

                var numeroCuenta: String
                var codigoMatricula: String
                var asignatura: String
                datos2.add(default)

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoMatricula = lista2[2]
                    if (datos[position].contains(numeroCuenta)) {
                       asignatura = añadirNombreAsignatura(codigoMatricula)
                        var dato2 = "$codigoMatricula-$asignatura"
                        datos2.add(dato2)

                    } else {
                        if (!datos[position].equals("Seleccione un número de cuenta")) {
                            txtNota.isEnabled=true
                            txtNota1.isEnabled=true
                            txtNota2.isEnabled=true


                        }
                        //var dato2 = "No Tiene Clases matriculadas"
                       // datos2.add(dato2)

                    }
                }
                rellenarSpinnerClases(datos2)



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                var nota1= "0.0"
                var nota2 ="0.0"
                var nota3= "0.0"
                txtNota.setText(nota1)
                txtNota1.setText(nota2)
                txtNota2.setText(nota3)

                txtNota.isEnabled=false
                txtNota1.isEnabled=false
                txtNota2.isEnabled=false
            }
        }

    }

    fun rellenarSpinnerClases(prueba2: ArrayList<String>) {
        var spinner2 = findViewById<Spinner>(R.id.spnClasesMatriculadas)
        var adaptador2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, prueba2)
        spinner2.adapter = adaptador2

        spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var numeroCuenta: String
                var codigoMatricula: String
                var asignatura: String

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoMatricula = lista2[2]
                    if (prueba2[position].contains(numeroCuenta)&& prueba2[position].contains(codigoMatricula)){

                    }


                    }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

        }


    }

    fun guardarNotas(){
        noVacio()
        var dato = StringBuilder()
        numero += 1
        dato.append(txtNota.text.toString()).append("|")
        dato.append(txtNota1.text.toString()).append("|")
        dato.append(txtNota2.text.toString()).append("|")
        notas.put(numero, dato.toString())

        if (noVacio()== true ){
            btnEnviarCorreo.isEnabled = true
            Toast.makeText(this, "Se ha guardado correctamente las Notas", Toast.LENGTH_SHORT).show()
        }


    }

    fun noVacio() : Boolean{
        if(txtNota.text.toString().isEmpty()) {
            txtNota.error ="Esta vacío el campo"
            return false
        }else if(txtNota1.text.toString().isEmpty()){
            txtNota1.error = "Esta vacío el campo"
            return false
        }

        if(txtNota2.text.toString().isEmpty()) {
            txtNota2.error ="Esta vacío el campo"
            return false
        }



        return true

    }


    fun añadirNombreAsignatura(codigo: String):String {
        var datos = ArrayList<String>()
        var nombreAsignatura= " "
        var codigoClase: String
        for (clases in clase) {
            val lista = clases.toString().split("|", "=")
            codigoClase = lista[1]
            nombreAsignatura = lista[2]
            if (codigoClase.equals(codigo)){
                var dato = "$nombreAsignatura"
                datos.add(dato)
                return dato

            }
         //   var dato = "$codigoClase - $nombreAsignatura"

        }

        return nombreAsignatura
    }
}












   /* fun llenarlist(){

        var datos =ArrayList<String>()

        var numeroCuenta:String
        var codigoMatricula:String
        var nombre : String

        var Asignatura:String



        for (matriculas in matricula){
            val lista = matriculas.toString().split("|","=")
            numeroCuenta= lista[1]
            codigoMatricula= lista[2]
            var dato = "$numeroCuenta - $codigoMatricula"
            datos.add(dato)

        }*/

       // val personas = mutableListOf("Angie","Carlos","Jungkook")


    /*    val arrayAdapter:ArrayAdapter<String>
        val lvDatos = findViewById<ListView>(R.id.lvNotas2)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,datos)
        lvDatos.adapter= arrayAdapter*/





