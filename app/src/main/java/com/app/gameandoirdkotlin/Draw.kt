package com.app.gameandoirdkotlin

import android.content.Context
import android.view.View

abstract class Draw(context: Context, screenSizeWidth:Int, screenSizeHeight:Int): View(context) {

    val screenSizeWidth:Int
    val screenSizeHeight:Int

    var rightBtn:Button
    var leftBtn:Button
    var jumpBtn:Button
    var attackBtn:Button
    var pauseBtn:Button

    val colones:ArrayList<Int> = ArrayList()
    val ligne:ArrayList<Int> = ArrayList()

    init {
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight

        var nbrColones = 7
        var nbrLigne = 7
        for(i in 0 until nbrColones){
            colones.add((this.screenSizeWidth/nbrColones)*i)
            for(j in 0 until nbrLigne){
                ligne.add((this.screenSizeHeight/nbrLigne)*j)
            }
        }

        rightBtn = Button(this, R.drawable.right, colones[1], (ligne[6]-(ligne[1]/1.5)).toInt(), (colones[1]/1.5).toInt(), ligne[1], Runnable {})
        leftBtn = Button(this, R.drawable.left, colones[0], (ligne[6]-(ligne[1]/1.5)).toInt(), (colones[1]/1.5).toInt(), ligne[1], Runnable {})
        jumpBtn = Button(this, R.drawable.jump, colones[6], ligne[5], colones[1], ligne[2], Runnable {})
        attackBtn = Button(this, R.drawable.sword, colones[6], ligne[4], colones[1]/2, ligne[1], Runnable {})
        pauseBtn = Button(this, R.drawable.pause, screenSizeWidth/2-25, ligne[0], 50, 50, Runnable {})
    }

    fun rightBtnSettings(x:Int, y:Int, w:Int, h:Int){
        rightBtn.x = x
        rightBtn.y = y
        rightBtn.w = w
        rightBtn.h = h
    }
    fun leftBtnSettings(x:Int, y:Int, w:Int, h:Int){
        leftBtn.x = x
        leftBtn.y = y
        leftBtn.w = w
        leftBtn.h = h
    }
    fun attackBtnSettings(x:Int, y:Int, w:Int, h:Int){
        attackBtn.x = x
        attackBtn.y = y
        attackBtn.w = w
        attackBtn.h = h
    }
    fun jumpBtnSettings(x:Int, y:Int, w:Int, h:Int){
        jumpBtn.x = x
        jumpBtn.y = y
        jumpBtn.w = w
        jumpBtn.h = h
    }
}