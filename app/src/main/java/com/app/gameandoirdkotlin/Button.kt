package com.app.gameandoirdkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Button(surface: Draw, idImage:Int, x:Int, y:Int, w:Int, h:Int, action: Runnable, adjustSize:Boolean = true) {
    var x:Int
    var y:Int
    var w:Int
    var h:Int

    var clicked = false
    var selected = false
    var enabled = true

    var image:Bitmap

    var action:Runnable

    init {
        this.x = x
        this.y = y
        this.w = w
        this.h = h

        this.action = action

        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, idImage)

        var width = bitmapTemp.width
        var height = bitmapTemp.height
        if(adjustSize){
            while(width > w) {
                width-=5
                height-=5
            }
            while(width < w) {
                width+=5
                height+=5
            }
        }

        image = Bitmap.createScaledBitmap(bitmapTemp, (width).toInt(),
            (height).toInt(),true)

        //image = Bitmap.createScaledBitmap(bitmapTemp, (w).toInt(), (h).toInt(),true)
    }


    fun draw(canvas: Canvas?){
        if(enabled)canvas!!.drawBitmap(image,x.toFloat(),y.toFloat(), Paint())
    }

    fun action(){
        action.run()
    }

    fun onClick(eventX: Float, eventY: Float){
        if(eventX > x && eventY > y && eventX < x+image.width && eventY < y+image.height){
            clicked = true
            selected = !selected
            action()
        }else{
            clicked = false
        }
    }

}