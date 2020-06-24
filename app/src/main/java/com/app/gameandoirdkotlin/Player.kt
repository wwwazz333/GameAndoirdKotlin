package com.app.gameandoirdkotlin

import android.graphics.*

class Player(surface: Drawing, x:Int, y:Int, idImage:Int) {
    //TODO("rect + bitmap => sync")

    var x:Int = x
    var y:Int = y
    val rectPlayer:Rect

    val image:Bitmap

    init {
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, idImage)
        image = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*0.2).toInt(),
            (bitmapTemp.height*0.2).toInt(),true)

        rectPlayer = Rect(this.x,this.y,this.x+image.width,this.y+image.height)
    }

    fun update(){
        rectPlayer.set(this.x,this.y,this.x+image.width,this.y+image.height)
    }
    fun draw(canvas: Canvas?){
        update()
        canvas!!.drawBitmap(image,rectPlayer.left.toFloat(),rectPlayer.top.toFloat(), Paint())
    }

    fun deplacement(toRight:Boolean, toLeft:Boolean){
        if(toLeft) {
            move(-15)
        }
        if(toRight){
            move(15)
        }
    }

    fun move(nbr:Int){
        x+=nbr
    }

}