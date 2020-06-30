package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


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
    var touchX = 0F
    var touchY = 0F

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



        val pointerCount: Int = event!!.pointerCount


        for(i in 0..pointerCount-1){
            if(event!!.getX(i) > (screenSizeWidth / 5) && event!!.getX(i) < (screenSizeWidth/5)*2) toRight = true

            if (event!!.getX(i) < screenSizeWidth / 5) toLeft = true

            if (event!!.getX(i) > (screenSizeWidth/5)*4) p.jump()
        }


        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_UP -> {
                toRight = false

                toLeft = false

            }
        }

        return true
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        cnt++

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

