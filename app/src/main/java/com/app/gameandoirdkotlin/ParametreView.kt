package com.app.gameandoirdkotlin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_parametre_view.*


class ParametreView : AppCompatActivity() {

    var settings:SharedPreferences? = null
    var nombreQuestion:Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parametre_view)
        supportActionBar?.title = "Paramètre"

        val adapter: ArrayAdapter<*> =
            ArrayAdapter.createFromResource(this, R.array.nombreQuestionArray, R.xml.spinner)
        nombreQuestionSpinner.setAdapter(adapter)


        restor()
    }

    fun actualise(){
        nombreQuestionSpinner.setSelection(nombreQuestion-1)
    }

    fun restor(){
        settings = getSharedPreferences("SavePara", 0)
        nombreQuestion = settings!!.getInt("nombreQuestionSave", 5)
        actualise()
    }
    fun default(){
        nombreQuestion = 5
        actualise()
    }


    fun onClickValide(view: View){
        settings = getSharedPreferences("SavePara", 0)
        val editor = settings!!.edit()
        editor.putInt("nombreQuestionSave", nombreQuestionSpinner.selectedItem.toString().toInt())
        editor.commit()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun CommandSettingsStart(view: View){
        val intent = Intent(this, ViewSettingsCommand::class.java)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_para, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.reset -> {
                default()
            }

        }
        return super.onOptionsItemSelected(item)
    }


}
