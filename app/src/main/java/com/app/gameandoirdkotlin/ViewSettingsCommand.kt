package com.app.gameandoirdkotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

class ViewSettingsCommand : AppCompatActivity() {
    lateinit var commandGame:CommandDrawing
    var settingsCommande: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        hideSystemUI()
        commandGame = CommandDrawing(this, displayMetrics.widthPixels, displayMetrics.heightPixels, Runnable { valide() })
        restor()
        setContentView(commandGame)
    }


    private fun hideSystemUI() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    fun valide(){
        settingsCommande = getSharedPreferences("CommandSaveSettings", Context.MODE_PRIVATE)
        val editor = settingsCommande!!.edit()
        editor.putInt("rightBtnX", commandGame.rightBtn.x)
        editor.putInt("rightBtnY", commandGame.rightBtn.y)
        editor.putInt("rightBtnW", commandGame.rightBtn.w)
        editor.putInt("rightBtnH", commandGame.rightBtn.h)

        editor.putInt("leftBtnX", commandGame.leftBtn.x)
        editor.putInt("leftBtnY", commandGame.leftBtn.y)
        editor.putInt("leftBtnW", commandGame.leftBtn.w)
        editor.putInt("leftBtnH", commandGame.leftBtn.h)

        editor.putInt("jumpBtnX", commandGame.jumpBtn.x)
        editor.putInt("jumpBtnY", commandGame.jumpBtn.y)
        editor.putInt("jumpBtnW", commandGame.jumpBtn.w)
        editor.putInt("jumpBtnH", commandGame.jumpBtn.h)

        editor.putInt("attackBtnX", commandGame.attackBtn.x)
        editor.putInt("attackBtnY", commandGame.attackBtn.y)
        editor.putInt("attackBtnW", commandGame.attackBtn.w)
        editor.putInt("attackBtnH", commandGame.attackBtn.h)

        editor.commit()

        finish()
    }

    fun restor(){
        settingsCommande = getSharedPreferences("CommandSaveSettings", Context.MODE_PRIVATE)

        commandGame.rightBtnSettings(settingsCommande!!.getInt("rightBtnX", commandGame.rightBtn.x),
            settingsCommande!!.getInt("rightBtnY", commandGame.rightBtn.y),
            settingsCommande!!.getInt("rightBtnW", commandGame.rightBtn.w),
            settingsCommande!!.getInt("rightBtnH", commandGame.rightBtn.h))

        commandGame.leftBtnSettings(settingsCommande!!.getInt("leftBtnX", commandGame.leftBtn.x),
            settingsCommande!!.getInt("leftBtnY", commandGame.leftBtn.y),
            settingsCommande!!.getInt("leftBtnW", commandGame.leftBtn.w),
            settingsCommande!!.getInt("leftBtnH", commandGame.leftBtn.h))

        commandGame.jumpBtnSettings(settingsCommande!!.getInt("jumpBtnX", commandGame.jumpBtn.x),
            settingsCommande!!.getInt("jumpBtnY", commandGame.jumpBtn.y),
            settingsCommande!!.getInt("jumpBtnW", commandGame.jumpBtn.w),
            settingsCommande!!.getInt("jumpBtnH", commandGame.jumpBtn.h))

        commandGame.attackBtnSettings(settingsCommande!!.getInt("attackBtnX", commandGame.attackBtn.x),
            settingsCommande!!.getInt("attackBtnY", commandGame.attackBtn.y),
            settingsCommande!!.getInt("attackBtnW", commandGame.attackBtn.w),
            settingsCommande!!.getInt("attackBtnH", commandGame.attackBtn.h))
    }
}