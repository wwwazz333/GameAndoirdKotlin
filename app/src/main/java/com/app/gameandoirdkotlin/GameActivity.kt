package com.app.gameandoirdkotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class GameActivity : AppCompatActivity() {

    lateinit var game:Drawing
    var settingsCommande:SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        hideSystemUI()
        game = Drawing(this, displayMetrics.widthPixels, displayMetrics.heightPixels)

        settingsCommande = getSharedPreferences("CommandSaveSettings", Context.MODE_PRIVATE)

        game.rightBtnSettings(settingsCommande!!.getInt("rightBtnX", game.rightBtn.x),
            settingsCommande!!.getInt("rightBtnY", game.rightBtn.y),
            settingsCommande!!.getInt("rightBtnW", game.rightBtn.w),
            settingsCommande!!.getInt("rightBtnH", game.rightBtn.h))

        game.leftBtnSettings(settingsCommande!!.getInt("leftBtnX", game.leftBtn.x),
            settingsCommande!!.getInt("leftBtnY", game.leftBtn.y),
            settingsCommande!!.getInt("leftBtnW", game.leftBtn.w),
            settingsCommande!!.getInt("leftBtnH", game.leftBtn.h))

        game.jumpBtnSettings(settingsCommande!!.getInt("jumpBtnX", game.jumpBtn.x),
            settingsCommande!!.getInt("jumpBtnY", game.jumpBtn.y),
            settingsCommande!!.getInt("jumpBtnW", game.jumpBtn.w),
            settingsCommande!!.getInt("jumpBtnH", game.jumpBtn.h))

        game.attackBtnSettings(settingsCommande!!.getInt("attackBtnX", game.attackBtn.x),
            settingsCommande!!.getInt("attackBtnY", game.attackBtn.y),
            settingsCommande!!.getInt("attackBtnW", game.attackBtn.w),
            settingsCommande!!.getInt("attackBtnH", game.attackBtn.h))

        setContentView(game)



    }

    override fun onPause() {
        super.onPause()
        try {
            game.pause()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        game.resume()
    }


    private fun hideSystemUI() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    private fun showSystemUI() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }


}