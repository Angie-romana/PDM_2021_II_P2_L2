package hn.edu.ujcv.pdm_2021_ii_p2_l2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class NotasActivity : AppCompatActivity() {
    var notas = ArrayList <String>()
    var valores: HashMap<Int, String> = hashMapOf()
    var numero= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)
        val arrayAdapter:ArrayAdapter<*>


        val lvDatos= findViewById<ListView>(R.id.lvNotas)
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, notas)
        lvDatos.adapter =arrayAdapter

    }
}