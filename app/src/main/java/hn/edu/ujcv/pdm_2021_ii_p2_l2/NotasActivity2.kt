package hn.edu.ujcv.pdm_2021_ii_p2_l2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_notas2.*
import java.util.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.HashMap
import java.util.*
import javax.mail.*
import javax.mail.internet.*
import kotlin.collections.ArrayList as CollectionsArrayList

class NotasActivity2 : AppCompatActivity() {


    var alumno: HashMap<Int, String> = hashMapOf()
    var clase: HashMap<Int, String> = hashMapOf()
    var matricula: HashMap<Int, String> = hashMapOf()
    var notas:HashMap<String, String> = hashMapOf()
     var datosGlobal = ArrayList<String>()
    var valor =""
    var valor2 =""
    var numero = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas2)

        inicializar()
        llenarSpiner()
        btnGuardar.setOnClickListener { guardarNotas() }
        btnEnviarCorreo.setOnClickListener { generarCorreo() }


        // llenarlist()

    }

    private fun generarCorreo() {

        val userName ="angienicoll@gmail.com"
        val password =  "IbelieveinyourGalaxy"+"$"+"Bangtan*ARMY7"
        // FYI: passwords as a command arguments isn't safe
        // They go into your bash/zsh history and are visible when running ps

        val emailFrom = "angienicoll@gmail.com"
        val emailTo ="angienicoll@gmail.com"
        //val emailCC =

        val subject = "SMTP Test"
        val text = "Hello Kotlin Mail"

        val props = Properties()
        putIfMissing(props, "mail.smtp.host", "smtp.gmail.com")
        putIfMissing(props, "mail.smtp.port", "587")
        putIfMissing(props, "mail.smtp.auth", "true")
        putIfMissing(props, "mail.smtp.starttls.enable", "true")

        val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(userName, password)
            }
        })

        session.debug = true

        try {
            val mimeMessage = MimeMessage(session)
            mimeMessage.setFrom(InternetAddress(emailFrom))
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo, false))
          //  mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailCC, false))
            mimeMessage.setText(text)
            mimeMessage.subject = subject
            mimeMessage.sentDate = Date()

            val smtpTransport = session.getTransport("smtp")
            smtpTransport.connect()
            smtpTransport.sendMessage(mimeMessage, mimeMessage.allRecipients)
            smtpTransport.close()
        } catch (messagingException: MessagingException) {
            messagingException.printStackTrace()
        }





/*        var txtemail: EditText
       var txtMensage: EditText

       val button: Button = findViewById(R.id.btnEnviarCorreo)
        val editTextTo: EditText

        button.setOnClickListener(View.OnClickListener {
            val to = "angienicoll7@gmail.com"
            val subject = "Test"
            val message = "Hola FIGHTING tu puedes"

            val intent = Intent(Intent.ACTION_SEND)
            val addressees = arrayOf(to)
            intent.putExtra(Intent.EXTRA_EMAIL, addressees)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, notas.get("Geometria y Tri.-2017130315"))
            intent.setType("message/rfc822")
            startActivity(Intent.createChooser(intent, "Send Email using:"));
        })*/

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

    }


    fun llenarSpiner() {
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
                var codigoMatricula: String
                var asignatura: String
               // datos2.add(default)
                valor2=datos[position]

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoMatricula = lista2[2]

                    if (datos[position].contains(numeroCuenta)) {
                       asignatura = añadirNombreAsignatura(codigoMatricula)
                        var dato2 = "$codigoMatricula-$asignatura"
                        var datos2Global =  numeroCuenta+codigoMatricula
                        datos2.add(dato2)


                    } else {
                        if(!datos[position].equals("Seleccione un número de cuenta")&& datos2.isEmpty()) {
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

                valor = prueba2[position]

                for (matriculas in matricula) {
                    val lista2 = matriculas.toString().split("|", "=")
                    numeroCuenta = lista2[1]
                    codigoMatricula = lista2[2]

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
        var valores=valor+"-"+valor2
        var valores2="$valor2 $valor"
      //  Toast.makeText(this, "$valores", Toast.LENGTH_SHORT).show()
        dato.append(valores2.toString()).append("|")
        dato.append(txtNota.text.toString()).append("|")
        dato.append(txtNota1.text.toString()).append("|")
        dato.append(txtNota2.text.toString()).append("|")
       // dato.append(promedio.toString()).append("|")
        notas.put(valores, dato.toString())

        if (noVacio()== true&&noMasde100()==true ){
            btnEnviarCorreo.isEnabled = true
            Toast.makeText(this, "$notas", Toast.LENGTH_LONG).show()
        }


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





