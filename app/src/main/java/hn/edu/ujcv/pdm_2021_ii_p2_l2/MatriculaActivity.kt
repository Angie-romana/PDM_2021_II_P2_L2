package hn.edu.ujcv.pdm_2021_ii_p2_l2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_matricula.*
import java.lang.Exception
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MatriculaActivity : AppCompatActivity() {
    var alumno: HashMap<Int,String> = hashMapOf()
    var clase: HashMap<Int,String> = hashMapOf()
    var matricula: HashMap<Int,String> = hashMapOf()
    var numeroCuenta = " "
    var codigoClaseARegistrar = " "
    var correo:String = ""
    var contraseña:String = ""
    var session:Session? = null
    var contenidoMatricula =""
    var claseMatriculadaAlumno = ArrayList<String>()
    var correoAlumno =""
    var codigoClaseMatriculada = ""

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
        btnEnviarMatricula.setOnClickListener { enviarCorreo() }
    }
//    clase.put(numero++,"FIL1015|Filosofia|A|10:00 - 10:50|Academico 1-102")
    private fun construirCorreo():String{
        var mensaje = "Las clases matriculadas por el alumno: " + numeroCuenta + " son: <br><br>"
        var numCuenta = ""
        var claseMatriculada = ""
        var contador = 0
        var numero = 1
        var contadorClase = 1
        var llevaLaClase = false
        var contador2 = 1
        claseMatriculadaAlumno.clear()
        for(matriculas in matricula) {
            val lista = matriculas.toString().split("|", "=")
            numCuenta = lista[1]
            claseMatriculada = lista[2].substring(0, 7)
            claseMatriculadaAlumno.add(numCuenta + "|" + claseMatriculada)
        }
                for(clases in clase){
                    numero = 1
                    contadorClase = 1
                    contador2 = 1
                    for(i in 0..(matricula.size-1)){
                        numCuenta = matricula.getValue(numero++).substring(0,10)
                        var codigoClase = matricula.getValue(contadorClase++).substring(11,(matricula.getValue(contador2++).length - 1))
                        codigoClaseMatriculada = listItems.get(contador).substring(0,7)
                        if(numCuenta.equals(numeroCuenta) && codigoClase.equals(codigoClaseMatriculada)){
                            llevaLaClase = true
                        }
                    }
                    if(!llevaLaClase){
                        contador++
                        continue
                    }
                    val lista2 = clases.toString().split("|","=")
                    val codigoClase = lista2[1]
                    val nombreClase = lista2[2]
                    val seccionClase = lista2[3]
                    val horarioClase = lista2[4]
                    val edificioClase = lista2[5]
                    val pisoClase = lista2[6]
                    val aulaClase = lista2[7]
                    val lista6 = claseMatriculadaAlumno.get(contador++).split("|")
                    val codigoClaseMatriculada = lista6[1]
                    if(codigoClase.equals(codigoClaseMatriculada)){
                        mensaje += "Código de clase: " + codigoClase + "<br> Nombre de la clase: " + nombreClase+
                                "<br>Sección de la clase: " + seccionClase + "<br>Horario de la clase: " + horarioClase+
                                "<br>Edificio de la clase: " + edificioClase + "<br>Piso de la clase: " + pisoClase+
                                "<br>Aula de la clase: "+aulaClase +"<br><br>"
                    }
                }
        return mensaje
    }
//     alumno.put(numero++,"2017110190|Carlos Chamorro|carlos.chamorro@ujcv.edu.hn")
    private fun capturarCorreo(){
        for(alumnos in alumno){
            val lista = alumnos.toString().split("|","=")
            var numeroCuentaAlumno = lista[1]
            var emailAlumno = lista[3]
            if(numeroCuentaAlumno.equals(numeroCuenta)){
                correoAlumno = emailAlumno
                return
            }
        }
    }

    private fun enviarCorreo(){
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val properties = Properties()
        properties.put("mail.smtp.host","smtp.gmail.com")
        properties.put("mail.smtp.socketFactory.port","465")
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory")
        properties.put("mail.smtp.auth","true")
        properties.put("mail.smtp.port","465")
        try {
            session = Session.getDefaultInstance(properties, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(correo,contraseña)
                }
            })
            }catch (e:Exception){

            }
        if(session!=null){
            var mimessage = MimeMessage(session)
            var message: Message
            val email = InternetAddress(correo)
            message = mimessage
            message.setFrom(email)
            numeroCuenta = spiNumeroCuenta.selectedItem.toString().substring(0,10)
            message.setSubject("Matrícula del alumno: " + numeroCuenta)
            capturarCorreo()
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correoAlumno))
            val mensaje = construirCorreo()
            message.setContent(mensaje,"text/html; charset=utf-8")
            Transport.send(message)
        }
    }


    private fun inicializar() {
        var intent = intent
        alumno = intent.getSerializableExtra("alumnos") as HashMap<Int, String>
        clase = intent.getSerializableExtra("clases") as HashMap<Int, String>
        txvMensajeError.isVisible = false
        correo = "proyectodispositivosmoviles1@gmail.com"
        contraseña = "Proyecto1DispMoviles"
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
            codigoClase = lista[2].substring(0,7)
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
                codigoClaseARegistrar = listItems.get(position).substring(0,7)
                numeroCuenta = spiNumeroCuenta.selectedItem.toString().substring(0,10)
                datosClases.clear()
                datosClases.add(codigoClaseARegistrar)
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

//    clase.put(numero++,"IDI1015|Ingles I|A|07:00 - 07:50|Academico|2|102")
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
            var piso = lista[6]
            var aula = lista[7]
            var dato = codigoClase + "," + nombreClase + "," + seccion + "," + horario + "," + edificio +","+piso +","+aula
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