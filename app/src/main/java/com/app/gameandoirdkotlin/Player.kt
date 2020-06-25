package com.app.gameandoirdkotlin

import android.graphics.*
import androidx.core.graphics.scale

class Player(surface: Drawing, x:Int, y:Int, idImage:Int? = null) {
    //TODO("rect + bitmap => sync")

    var x:Int = x
    var y:Int = y
    val rectPlayer:Rect
    var canToRight = true
    var canToLeft = true

    val image:Bitmap

    init {
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, idImage!!)
        image = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*0.2).toInt(),
               (bitmapTemp.height*0.2).toInt(),true)

        rectPlayer = Rect(this.x,this.y,this.x+image.width,this.y+image.height)

    }

    fun update(){
        rectPlayer.set(this.x,this.y,this.x+image!!.width,this.y+image!!.height)

    }
    fun draw(canvas: Canvas?){
        update()

        canvas!!.drawBitmap(image,rectPlayer.left.toFloat(),rectPlayer.top.toFloat(), Paint())
    }

    fun deplacement(toRight:Boolean, toLeft:Boolean){
        if(toLeft && canToLeft) {
            move(-15)
        }
        if(toRight && canToRight){
            move(15)
        }
    }

    fun move(nbr:Int){
        x+=nbr
    }

    fun touch(rect: Rectangle){
        if(rectPlayer.right >= rect.x && rectPlayer.left <= rect.x+rect.w && rectPlayer.top <= rect.y+rect.h && rectPlayer.bottom >= rect.y){
            if(rectPlayer.centerX() < rect.rect.centerX()) canToRight = false
            if(rectPlayer.centerX() > rect.rect.centerX()) canToLeft = false
        }else{
            canToLeft = true
            canToRight = true
        }
    }

    fun resize(size:Float){
        image.scale((image.width*size).toInt(), (image.height*size).toInt())
        image.density = size.toInt()
        update()
    }

}