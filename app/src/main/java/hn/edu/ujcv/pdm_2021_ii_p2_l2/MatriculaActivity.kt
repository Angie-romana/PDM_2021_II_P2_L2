package hn.edu.ujcv.pdm_2021_ii_p2_l2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_matricula.*

class MatriculaActivity : AppCompatActivity() {
    var alumno: HashMap<Int,String> = hashMapOf()
    var clase: HashMap<Int,String> = hashMapOf()
    var matricula: HashMap<Int,String> = hashMapOf()
    var codigoClaseARegistrar = " "

    var listItems = ArrayList<String>()
    var adapter: ArrayAdapter<String>? = null
    var numero = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matricula)
        inicializar()
        setSpinner()
        llenarClases()
        llenarMatricula()
        btnNotas.setOnClickListener { irNotas() }
    }

    private fun inicializar() {
        var intent = intent
        alumno = intent.getSerializableExtra("alumnos") as HashMap<Int, String>
        clase = intent.getSerializableExtra("clases") as HashMap<Int, String>
        txvMensajeError.isVisible = false
    }

    var eliminarMatricula: View.OnClickListener = View.OnClickListener { view ->
        matricula.remove(matricula.size)
        adapter?.notifyDataSetChanged()
        Snackbar.make(view, "Se he eliminado la clase exitosamente", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onStart(){
        super.onStart()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
            listItems)
        lstMatricula.adapter = adapter
    }

    private fun irNotas(){
        val intent = Intent(this, NotasActivity2::class.java)
        intent.putExtra("alumnos",alumno)
        intent.putExtra("clases",clase)
        intent.putExtra("matriculas",matricula)
        startActivity(intent)
    }

    private fun guardarMatricula(array:ArrayList<String>,numeroCuenta:String){
        val dato = StringBuilder()
        val lista = array.toString().split("|","=")
        var codigoClase = lista[0].substring(1)
        dato.append(numeroCuenta).append("|")
        dato.append(codigoClase)
        matricula.put(numero,dato.toString())
        numero+=1
    }

    private fun validarClaseMatriculada():Boolean{
        var numeroCuenta: String
        var codigoClase:String
        txvMensajeError.isVisible = false
        for(matriculas in matricula){
            val lista = matriculas.toString().split("|","=")
            numeroCuenta = lista[1]
            codigoClase = lista[2]
            var numeroARegistrar = spiNumeroCuenta.selectedItem.toString().substring(0,10)
            if(numeroCuenta.equals(numeroARegistrar) && codigoClase.equals(codigoClaseARegistrar)){
                txvMensajeError.isVisible = true
                txvMensajeError.setTextColor(Color.parseColor("#ff0000"))
                txvMensajeError.setText("La clase con código " + codigoClaseARegistrar +" ya está matriculada.")
                return true
            }
        }
        return false
    }

    private fun llenarMatricula():ArrayList<String>{
        var datosClases = ArrayList<String>()
        var datos = ""
        lstMatricula.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if(spiNumeroCuenta.selectedItem=="Seleccione un número de cuenta"){
                    listItems.clear()
                    adapter?.notifyDataSetChanged()
                    return
                }
                datosClases.clear()
                datos = clase.get(position+1).toString()
                datosClases.add(datos)
                var numeroCuenta = spiNumeroCuenta.selectedItem.toString().substring(0,10)
                codigoClaseARegistrar = listItems.get(position).substring(0,7)
                if(validarClaseMatriculada()){
                    return
                }
                guardarMatricula(datosClases,numeroCuenta)
                Snackbar.make(view, "Acción:", Snackbar.LENGTH_LONG)
                    .setAction("Eliminar clase matriculada", eliminarMatricula).show()
            }
        }

        return datosClases
    }

    private fun llenarClases(){
        spiNumeroCuenta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                addListItems()
            }

        }
    }

    private fun obtenerDatosSpinner():ArrayList<String>{
        val default = "Seleccione un número de cuenta"
        var datos = ArrayList<String>()
        datos.add(default)
        for (alumnos in alumno){
            val lista = alumnos.toString().split("|","=")
            var numeroCuenta = lista[1]
            var nombre = lista[2]
            var dato = numeroCuenta + " - " + nombre
            datos.add(dato)
        }
        return datos
    }

//    clase.put(numero++,"FI1015|Filosofia|A|10:00 - 10:50|Academico 1-102")
    private fun addListItems(){
        listItems.clear()
        if(spiNumeroCuenta.selectedItem=="Seleccione un número de cuenta"){
            listItems.clear()
            adapter?.notifyDataSetChanged()
            return
        }
        for (clases in clase){
            val lista = clases.toString().split("|","=")
            var codigoClase = lista[1]
            var nombreClase = lista[2]
            var seccion = lista[3]
            var horario = lista[4]
            var edificio = lista[5]
            var dato = codigoClase + "," + nombreClase + "," + seccion + "," + horario + "," + edificio
            listItems.add(dato)
            adapter?.notifyDataSetChanged()
        }
    }



    private fun setSpinner() {
        val mySpinner: Spinner =findViewById(R.id.spiNumeroCuenta)
        val numerosDeCuentas = obtenerDatosSpinner()
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, numerosDeCuentas)
        mySpinner.adapter=adapter
        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                txvMensajeError.isVisible = false
                txvMensajeError.setText("")
            }
        }

    }
}