package com.app.gameandoirdkotlin

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class RectPlayer(var rectangle: Rect, private var color:Int):GameObject {
    var toLeft:Boolean = false
    var toRight:Boolean = false

    init {
        this.rectangle = rectangle
        this.color = color
    }
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = color
        canvas.drawRect(rectangle, paint)
    }

    override fun update() {
        TODO("Not yet implemented")
    }
    fun update(point: Point){
        rectangle.set(point.x - rectangle.width()/2,
            point.y - rectangle.width()/2,
            point.x + rectangle.width()/2,
            point.y + rectangle.width()/2)
    }
}
