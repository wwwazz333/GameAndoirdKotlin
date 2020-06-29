package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.*
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight:Int) : View(context) {

    var toLeft = false
    var toRight = false
    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0
    val p:Player
    val w:Rectangle
    var allRect:List<Rectangle>
    val white:Paint = Paint()

    init {
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight

        p = Player(this,0,0,R.drawable.stickman)
        w = Rectangle(500,screenSizeHeight-100,50, 50, Color.RED)

        allRect = listOf<Rectangle>(w)

        white.color = Color.WHITE



        update()
    }

    fun update(){
        //perso.set(x,y,x+50,y+50)
    }
    

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //TODO("multi touch")
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event!!.x < screenSizeWidth / 5) toLeft = true
                if (event!!.x > (screenSizeWidth / 5) && event!!.x < (screenSizeWidth/5)*2) toRight = true
                if (event!!.x > (screenSizeWidth/5)*4) p.jump()

            }
            MotionEvent.ACTION_UP -> {
                toLeft = false
                toRight = false

            }
        }

        return true
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        cnt++

        //p.coll(allRect)
        p.update()
        p.deplacement(toRight, toLeft,allRect)
        p.gravity(allRect)



        //update()
        //dessin
        canvas!!.drawColor(Color.BLACK)//background

        p.draw(canvas)
        for(rect in allRect){
            rect.draw(canvas)
        }
        canvas!!.drawLine((screenSizeWidth / 5).toFloat(), 0F, (screenSizeWidth / 5).toFloat(), screenSizeHeight.toFloat(),white)
        canvas!!.drawLine(((screenSizeWidth/5)*2).toFloat(), 0F, ((screenSizeWidth / 5)*2).toFloat(), screenSizeHeight.toFloat(),white)
        canvas!!.drawLine(((screenSizeWidth/5)*4).toFloat(), 0F, ((screenSizeWidth / 5)*4).toFloat(), screenSizeHeight.toFloat(),white)

        invalidate()
    }
}

