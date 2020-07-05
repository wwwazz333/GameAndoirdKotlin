package com.app.gameandoirdkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.graphics.scale

class Button(surface: Drawing, idImage:Int, x:Int, y:Int, w:Int, h:Int, action: Runnable) {
    //TODO("class button")
    var x:Int
    var y:Int
    var w:Int
    var h:Int

    var image:Bitmap

    var action:Runnable

    init {
        this.x = x
        this.y = y
        this.w = w
        this.h = h

        this.action = action

        var resize = 0.2
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, idImage)

        var width = bitmapTemp.width
        var height = bitmapTemp.height

        while(width > w) {
            width-=5
            height-=5
        }
        while(width < w) {
            width+=5
            height+=5
        }
        image = Bitmap.createScaledBitmap(bitmapTemp, (width).toInt(),
            (height).toInt(),true)

        //image = Bitmap.createScaledBitmap(bitmapTemp, (w).toInt(), (h).toInt(),true)
    }


    fun draw(canvas: Canvas?){
        canvas!!.drawBitmap(image,x.toFloat(),y.toFloat(), Paint())
    }

    fun action(){
        action.run()
    }

    fun onClick(eventX: Float, eventY: Float){
        if(eventX > x && eventY > y && eventX < x+image.width && eventY < y+image.height){
            action()
        }
    }

}