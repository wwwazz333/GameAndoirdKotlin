package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight: Int) : View(context) {

    val perso:Bitmap
    var persoX = 0F
    var persoY = 0F
    var toLeft = false
    var toRight = false
    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0

    init {
        var bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.stickman)
        perso = Bitmap.createScaledBitmap(bitmap, (bitmap.width*0.2).toInt(),
            (bitmap.height*0.2).toInt(),true)
        println("scale of perso picture : ${perso.width} * ${perso.height}")


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

        if(toLeft) {
            persoX-=15
        }
        if(toRight){
            persoX+=15
        }


        update()

        canvas!!.drawColor(Color.BLACK)


        canvas!!.drawBitmap(perso,persoX,persoY,Paint())


        //canvas!!.drawRect(perso, persoColor)

        invalidate()
    }
}
