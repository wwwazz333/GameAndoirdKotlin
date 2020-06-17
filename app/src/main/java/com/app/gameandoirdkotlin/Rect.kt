package com.app.gameandoirdkotlin

import android.widget.ImageView

class Rect(imageView: ImageView,x:Float, y:Float) {
    var rect:ImageView? = null
    var previousTick = System.currentTimeMillis()
    var wait = 0L
    init {
        rect = imageView
        rect!!.x = x
        rect!!.y = y
    }

    fun deplacement(mtL:Boolean, mtR:Boolean){
        previousTick = System.currentTimeMillis()
        if(mtL) move(-4F)
        if(mtR) move(4F)
        Thread.sleep(wait)
        if(System.currentTimeMillis()<previousTick+16) wait = previousTick+16-System.currentTimeMillis()
        else if(System.currentTimeMillis()>=previousTick+16) wait = 0
    }

    fun touch(r:Rect):Boolean {
        return rect!!.x <= r.rect!!.x + r.rect!!.width && rect!!.x + rect!!.width >= r.rect!!.x && rect!!.y <= r.rect!!.y + r.rect!!.height && rect!!.y + rect!!.height >= r.rect!!.y
    }
    fun touchWich(r:Rect):Int{
        if (touch(r) && rect!!.x + rect!!.width <= r.rect!!.x) return 1 //aller à droite
        else if (touch(r) && rect!!.x <= r.rect!!.x + r.rect!!.width) return 2//aller à gauche
        else return 0
    }

    fun move(n:Float){
        rect!!.x += n
    }

    fun show(){
        rect!!.visibility = ImageView.VISIBLE
    }
    fun hide(){
        rect!!.visibility = ImageView.INVISIBLE
    }
}