package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.*
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight: Int) : View(context) {

    var toLeft = false
    var toRight = false
    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0
    val p:Player
    val w:Rectangle

    init {
        p = Player(this,0,0,R.drawable.stickman)
        w = Rectangle(500,1000,50, 50, Color.RED)

        update()
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight
    }

    fun update(){
        //perso.set(x,y,x+50,y+50)
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
        p.touch(w)
        p.deplacement(toRight, toLeft)



        //update()

        canvas!!.drawColor(Color.BLACK)//background

        //canvas!!.drawBitmap(perso,persoX,persoY,Paint())
        p.draw(canvas)
        w.draw(canvas)


        //canvas!!.drawRect(perso, persoColor)

        invalidate()
    }
}

