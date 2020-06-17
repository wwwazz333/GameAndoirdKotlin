package com.app.gameandoirdkotlin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sec_dialog.*
import java.util.ArrayList

class QuizView : AppCompatActivity() {
    private val selectedColor:String = "#6689ff"
    private val unselectedColor:String = "#ffffff"

    private var quizs = ArrayList<Quiz>()
    private var quizsIndex:Int = 0
    private var bonneReponses:Int = 0
    private var checked:Int = 0
    private var nombreQuestion = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec_dialog)
        supportActionBar?.hide()

        var settings = getSharedPreferences("SavePara", Context.MODE_PRIVATE)
        nombreQuestion = settings.getInt("nombreQuestionSave",5)
        for (i in (1..nombreQuestion)){
            quizs.add(Quiz())
        }

        actualise()
    }

    private fun actualise(){
        if(quizsIndex < quizs.size) {
            question.text = quizs[quizsIndex].question
            reponse1.text = quizs[quizsIndex].reponse1
            reponse2.text = quizs[quizsIndex].reponse2
            reponse3.text = quizs[quizsIndex].reponse3
        } else{
            //Toast.makeText(this, "done", Toast.LENGTH_SHORT).show()
            var done = AlertDialog.Builder(this)
            done.setTitle("Fin")
            done.setMessage("Vous avez fini le quiz.\nVous avez eu : ${bonneReponses}/${quizs.size}")
            done.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                finish()
            }
            done.show()
        }
    }

    fun onClickReponse1(view:View){
        clearColor()
        reponse1.setTextColor(Color.parseColor(selectedColor))

        checked = 1
    }
    fun onClickReponse2(view:View){
        clearColor()
        reponse2.setTextColor(Color.parseColor(selectedColor))

        checked = 2
    }
    fun onClickReponse3(view:View){
        clearColor()
        reponse3.setTextColor(Color.parseColor(selectedColor))

        checked = 3
    }

    fun onClickValide(view:View){
        if(checked!=0) {
            if(quizs[quizsIndex].isCorrect(checked)){
                Toast.makeText(this, "good",Toast.LENGTH_SHORT).show()
                bonneReponses++
            } else {
                Toast.makeText(this, "bad",Toast.LENGTH_SHORT).show()
            }
            quizsIndex++
            actualise()
            clearColor()
        }
    }

    fun clearColor(){
        reponse1.setTextColor(Color.parseColor(unselectedColor))
        reponse2.setTextColor(Color.parseColor(unselectedColor))
        reponse3.setTextColor(Color.parseColor(unselectedColor))
    }
}