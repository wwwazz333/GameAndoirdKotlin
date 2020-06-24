package com.app.gameandoirdkotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Player(x:Int, y:Int, w:Int, h:Int, surface: Drawing) {
    //TODO("rect + bitmap => sync")

    var x:Int = x
    var y:Int = y
    var w:Int = w
    var h:Int = h
    val rectPlayer:Rect

    val image:Bitmap

    init {
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.stickman)
        image = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*0.2).toInt(),
            (bitmapTemp.height*0.2).toInt(),true)
        rectPlayer = Rect(this.x,this.y,this.x+this.w,this.y+this.h)
    }

}