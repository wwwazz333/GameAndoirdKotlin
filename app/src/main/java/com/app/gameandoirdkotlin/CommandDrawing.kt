package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent

class CommandDrawing(context: Context, screenSizeWidth:Int, screenSizeHeight:Int, exit:Runnable):Draw(context, screenSizeWidth, screenSizeHeight) {

    val btnList:List<Button>

    init {
        pauseBtn.action = Runnable { exit.run() }

        btnList = listOf(rightBtn, leftBtn, jumpBtn, attackBtn)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val pointerCount: Int = event!!.pointerCount
        for(btn in btnList){
            if(btn.selected){
                btn.x = (event.x-(btn.w/2)).toInt()
                btn.y = (event.y-(btn.h/2)).toInt()
            }
        }
        pauseBtn.onClick(event.x, event.y)


        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                for (btn in btnList){
                    btn.onClick(event.x, event.y)
                    if(btn.clicked){


                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                for(btn in btnList){
                    btn.selected = false
                }
            }
        }


        return true
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawColor(Color.BLACK)//background
        for (btn in btnList){
            btn.draw(canvas)
        }
        pauseBtn.draw(canvas)
        invalidate()
    }
}