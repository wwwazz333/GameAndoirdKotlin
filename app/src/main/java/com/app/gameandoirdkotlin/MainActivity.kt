package com.app.gameandoirdkotlin

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {
    var settings:SharedPreferences? = null


    var nombreQuestion = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()//cacher bar du haut

        restor()
    }

    fun actualise(){
        //textView.text = nombreQuestion.toString()
    }

    fun restor(){
        settings = getSharedPreferences("SavePara", Context.MODE_PRIVATE)
        nombreQuestion = settings!!.getInt("nombreQuestionSave", 5)
        actualise()
    }


    fun onClickPlay(view:View){
        val intent = Intent(this, QuizView::class.java)
        startActivity(intent)
    }

    fun onClickPara(view: View){
        val intent = Intent(this, ParametreView::class.java)
        startActivity(intent)
        finish()
    }

    fun onClickGame(view: View){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun onClickWifi(view: View){
        val intent = Intent(this, ConnectWifiP2pActivity::class.java)
        startActivity(intent)
    }
    fun onClickExit(view: View){
        finish()
        moveTaskToBack(true)
        System.exit(0)
    }
}