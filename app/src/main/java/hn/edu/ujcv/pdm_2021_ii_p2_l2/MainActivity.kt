package hn.edu.ujcv.pdm_2021_ii_p2_l2

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem

import hn.edu.ujcv.pdm_2021_ii_p2_l2.databinding.ActivityMainBinding

import kotlinx.android.synthetic.main.fragment_first.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    var alumno: HashMap<Int,String> = hashMapOf()
    var clase: HashMap<Int,String> = hashMapOf()
    var numero = 1;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        button_first.setOnClickListener { irMatricula() }


    }



    fun irMatricula(){
        alumno.put(numero++,"2017110190|Carlos Chamorro|carlos.chamorro@ujcv.edu.hn")
        alumno.put(numero++,"2017130315|Angie Nicoll|cmchamorro5120@gmail.com")
        numero = 1
        clase.put(numero++,"FI1015|Filosofia|A|10:00 - 10:50|Academico 1-102")
        clase.put(numero++,"ESP1014|Español|A|11:00 - 11:50|Academico 1-106")
        clase.put(numero++,"MAT1014|Álgebra|B|09:00 - 09:50|Academico 3-300")
        clase.put(numero++,"MAT1015|Geometría y Tri.|C|08:00 - 08:50|Academico 4-404")
        clase.put(numero++,"IDI1015|Ingles I|A|07:00 - 07:50|Academico 2-102")
        val intent = Intent(this, MatriculaActivity::class.java)
        intent.putExtra("alumnos",alumno)
        intent.putExtra("clases",clase)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}