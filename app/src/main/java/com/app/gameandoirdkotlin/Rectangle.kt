package com.app.gameandoirdkotlin

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Rectangle(x:Int, y:Int, w:Int, h:Int, color:Int) {

    val rect:Rect
    val paint:Paint
    var x = x
    var y = y
    var w = w
    var h = h
    var color = color
    init {
        rect = Rect(x,y,x+w,y+h)
        paint = Paint()
        paint.color = this.color
    }
    fun draw(canvas: Canvas?){
        canvas!!.drawRect(rect,paint)
    }
}