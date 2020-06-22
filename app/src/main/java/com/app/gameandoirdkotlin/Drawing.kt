package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_game.view.*
import kotlin.concurrent.thread

class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight: Int) : View(context) {

    val perso:Rect
    var x:Int = 0
    var y:Int = 0
    var toLeft = false
    var toRight = false
    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0
    val persoColor:Paint


    init {
        perso = Rect()
        persoColor = Paint()
        persoColor.color = Color.RED

        update()
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight
    }

    fun update(){
        perso.set(x,y,x+50,y+50)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event!!.x < screenSizeWidth / 3) toLeft = true
                else if (event!!.x > (screenSizeWidth / 3) * 2) toRight = true

            }
            MotionEvent.ACTION_UP -> {
                toLeft = false
                toRight = false

            }
        }

        invalidate()
        return true
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        cnt++

        if(toLeft) {
            x-=15
        }
        if(toRight){
            x+=15
        }


        update()

        canvas!!.drawColor(Color.BLACK)
        canvas!!.drawRect(perso, persoColor)

        invalidate()
    }
}

