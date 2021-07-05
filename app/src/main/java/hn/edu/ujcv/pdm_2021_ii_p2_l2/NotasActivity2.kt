package hn.edu.ujcv.pdm_2021_ii_p2_l2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_notas2.*
import java.util.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.HashMap
import javax.mail.*
import kotlin.collections.ArrayList as CollectionsArrayList

class NotasActivity2 : AppCompatActivity() {


    var alumno: HashMap<Int, String> = hashMapOf()
    var clase: HashMap<Int, String> = hashMapOf()
    var matricula: HashMap<Int, String> = hashMapOf()
    var notas:HashMap<String, String> = hashMapOf()
    var datosGlobal = ArrayList<String>()
    var codigoMateria =""
    var nombreEstudiante =""
    var numero = ""
    var numeroCuentaAlumno = ""
    var correoAlumno = ""
    var correo:String = ""
    var contraseña:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas2)

        inicializar()
        llenarSpinerClases()
        btnGuardar.setOnClickListener { guardarNotas() }
        btnEnviarCorreo.setOnClickListener { generarCorreo() }


        // llenarlist()

    }
    private fun capturarCorreo(){
        for(alumnos in alumno){
            val lista = alumnos.toString().split("|","=")
            var numeroCuentaAlumno = lista[1]
            var emailAlumno = lista[3]
            if(numeroCuentaAlumno.equals(numeroCuentaAlumno)){
                correoAlumno = emailAlumno
                return
            }
        }
    }

    private fun construirCorreo():String{
        var mensaje = "Las notas de las clases matriculadas por el alumno: " + numeroCuentaAlumno + " son: <br><br>"
        var notasArray = ArrayList<String>()
        var clasesArray = ArrayList<String>()
        var nombreCodigoAlumno:String
        var codigoClase:String
        var nota1:String
        var nota2:String
        var nota3:String
        var mensaje2="NOOOOOOOOOOO"

        for (notas in notas){
            var lista= notas.toString().split("|","=")
            nombreCodigoAlumno= lista[5]
            codigoClase=lista[1]
            nota1=lista[2]
            nota2=lista[3]
            nota3=lista[4]
            if(spnNumeroCuenta.selectedItem.equals(nombreCodigoAlumno)){
                notasArray.add("Codigo de clase :$codigoClase<br>" +
                        "Primer Parcial: $nota1 <br>" +
                        "Segundo Parcial:$nota2<br>" +
                        "Tercer Parcial:$nota3 ]\n <br>" +
                        "<br>[")

            }

        }
        var totalArray =notasArray.size-1
        mensaje2= notasArray.toString()

        /*for(matriculas in matricula){
            val lista = matriculas.toString().split("|","=")
            var numCuenta = lista[1]
            var claseMatriculada = lista[2]

            if(numCuenta.equals(numeroCuentaAlumno)){
                for(clases in clase){
                    val lista2 = clases.toString().split("|","=")
                    val codigoClase = lista2[1]
                    val nombreClase = lista2[2]
                    if(claseMatriculada.equals(codigoClase)){
                        clasesArray.add(nombreClase+"|"+numeroCuentaAlumno)
                    }
                    if(codigoClase.equals(claseMatriculada)) {
                        for(valor in notas){
                            val lista3 = valor.toString().split("=","|")
                            var nota1 = lista3[2]
                            var nota2 = lista3[3]
                            var nota3 = lista3[4]
                            notasArray.add(nota1+"|"+nota2+"|"+nota3)
                        }
                    }
                }
            }
        }
        for(i in 0..(notas.size-1)){
            val lista5 = clasesArray.get(i).split("|")
            var clase = lista5[0]
            var numCuenta = lista5[1]
            if(numCuenta.equals(numeroCuentaAlumno)){
                val lista4 = notasArray.get(i).split("|")
                var nota1Final = lista4[0]
                var nota2Final = lista4[1]
                var nota3Final = lista4[2]

                mensaje += "Nombre de la clase: " + clase +
                        "<br>Nota1: " + nota1Final + "<br>Nota2: " + nota2Final+
                        "<br>Nota3: " + nota3Final + "<br><br>"
            }
        }*/

        return mensaje2
    }

    private fun generarCorreo() {
        val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val properties = Properties()
        properties.put("mail.smtp.host","smtp.gmail.com")
        properties.put("mail.smtp.socketFactory.port","465")
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory")
        properties.put("mail.smtp.auth","true")
        properties.put("mail.smtp.port","465")
        val session = Session.getDefaultInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(correo, contraseña)
            }
        })
        try {
            var mimessage = MimeMessage(session)
            var message: Message
            val email = InternetAddress(correo)
            message = mimessage
            message.setFrom(email)
            numeroCuentaAlumno = spnNumeroCuenta.selectedItem.toString().substring(0,10)
            message.setSubject("Notas del alumno: " + numeroCuentaAlumno)
            capturarCorreo()
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(correoAlumno))
            val mensaje = construirCorreo()
            message.setContent(mensaje,"text/html; charset=utf-8")
            Transport.send(message)
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
        }
    }
    private fun putIfMissing(props: Properties, key: String, value: String) {
        if (!props.containsKey(key)) {
            props[key] = value
        }
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
        btnGuardar.isEnabled=false

        txtNota.isEnabled=false
        txtNota1.isEnabled=false
        txtNota2.isEnabled=false

        correo = "proyectodispositivosmoviles1@gmail.com"
        contraseña = "Proyecto1DispMoviles"

    }


    fun llenarSpinerClases() {
        val default = "Seleccione un número de cuenta"
        var datos = ArrayList<String>()
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
                //var default= "Clases Matriculadas"

                var numeroCuenta: String
                var codigoClase: String
                var asignatura: String
                // datos2.add(default)
                nombreEstudiante=datos[position]

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoClase = lista2[2]

                    if (datos[position].contains(numeroCuenta)) {
                        asignatura = añadirNombreAsignatura(codigoClase)
                        codigoClase=codigoClase.substring(0,7)
                        var dato2 = "$codigoClase-$asignatura"

                        datos2.add(dato2)


                    } else {
                        if(!datos[position].equals("Seleccione un número de cuenta")) {
                            txtNota.setText("")
                            txtNota1.setText("")
                            txtNota2.setText("")
                            txtNota.isEnabled=true
                            txtNota1.isEnabled=true
                            txtNota2.isEnabled=true
                            btnGuardar.isEnabled=true

                        }
                        //var dato2 = "No Tiene Clases matriculadas"
                        // datos2.add(dato2)

                    }
                }
                rellenarSpinnerClases(datos2)

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
                /*        var nota1= "0.0"
                        var nota2 ="0.0"
                        var nota3= "0.0"
                        txtNota.setText(nota1)
                        txtNota1.setText(nota2)
                        txtNota2.setText(nota3)
                        txtNota.isEnabled=false
                        txtNota1.isEnabled=false
                        txtNota2.isEnabled=false*/
            }
        }


    }
    fun toastquenomesirvio(){
        Toast.makeText(this, "El alumno no tiene clases matriculadas", Toast.LENGTH_LONG).show()

    }

    fun rellenarSpinnerClases(prueba2: ArrayList<String>) {
        var spinner2 = findViewById<Spinner>(R.id.spnClasesMatriculadas)
        var adaptador2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, prueba2)
        spinner2.adapter = adaptador2

        /*      spinner2.onItemClickListener=object :
              AdapterView.OnItemClickListener{
                  override fun onItemClick(
                      parent: AdapterView<*>?,
                      view: View?,
                      position: Int,
                      id: Long
                  ) {
                      valor= prueba2[position]
                  }
              }*/

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
                var llave:String

                codigoMateria = prueba2[position]
                btnGuardar.isEnabled=true

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoMatricula = lista2[2]

                }
                // if(prueba2[position].equals(valor)){
                var nota1:String
                var nota2 :String
                var nota3:String
                var numeroCuenta2: String
                for (nota in notas) {
                    val lista3 = nota.toString().split("|", "=")
                    numeroCuenta2= lista3[1]
                    nota1=lista3[2]
                    nota2=lista3[3]
                    nota3=lista3[4]
                    if (prueba2[position].equals(numeroCuenta2)) {
                        txtNota.setText(nota1)
                        txtNota1.setText(nota2)
                        txtNota2.setText(nota3)

                        // return
                    }


                }

                for (nota in notas){
                    val lista2 = nota.toString().split("|", "=")


                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

        }


    }

    fun guardarNotas(){
        noVacio()

        var datos2 = ArrayList<String>()

        var numeroCuenta: String
        var codigoMatricula: String


        for (matriculas in matricula) {
            val lista2 = matriculas.toString().split("|", "=")
            numeroCuenta = lista2[1]
            codigoMatricula = lista2[2]
            var matricula=numeroCuenta+codigoMatricula
            datos2.add(matricula)}

        var nota1=0.0
        var nota2=0.0
        var nota3=0.0
        var promedio=0.0
        /* nota1=txtNota.text.toString().toDouble()
         nota2=txtNota1.text.toString().toDouble()
         nota3=txtNota2.text.toString().toDouble()*/

        //promedio=(nota1+nota2+nota3)/3


        var dato = StringBuilder()
        numero+1
        var valores=codigoMateria+"-"+nombreEstudiante
        var codigodeMateria="$codigoMateria"
        //  Toast.makeText(this, "$valores", Toast.LENGTH_SHORT).show()
        dato.append(codigodeMateria.toString()).append("|")
        dato.append(txtNota.text.toString()).append("|")
        dato.append(txtNota1.text.toString()).append("|")
        dato.append(txtNota2.text.toString()).append("|")
         dato.append(nombreEstudiante.toString()).append("|")
        notas.put(valores, dato.toString())

        if (noVacio()== true&&noMasde100()==true ){
            btnEnviarCorreo.isEnabled = true
            Toast.makeText(this, "$nombreEstudiante", Toast.LENGTH_LONG).show()
            limpiar()

        }


    }

    fun limpiar(){
        txtNota.setText("")
        txtNota1.setText("")
        txtNota2.setText("")
        btnGuardar.isEnabled=false
    }

    fun noMasde100():Boolean{
        if(txtNota.text.toString().toDouble()>100) {
            txtNota.error ="El Valor no debe ser mayor a 100"
            return false
        }
        if(txtNota1.text.toString().toDouble()>100) {
            txtNota1.error ="El Valor no debe ser mayor a 100"
            return false
        }
        if(txtNota2.text.toString().toDouble()>100) {
            txtNota2.error ="El Valor no debe ser mayor a 100"
            return false
        }
        return true
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
