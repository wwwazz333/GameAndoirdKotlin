package com.app.gameandoirdkotlin

import android.graphics.*

class Player(surface: Drawing, x:Int, y:Int, idImage:Int? = null) {
    //TODO("rect + bitmap => sync")

    var x:Int = x
    var y:Int = y
    val rectPlayer:Rect
    var canToRight = true
    var canToLeft = true
    var canJump = true
    val image:Bitmap
    var surface:Drawing? = null

    init {
        this.surface = surface
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

    fun deplacement(toRight:Boolean, toLeft:Boolean, list: List<Rectangle>){
        if(toLeft && canToLeft) {
            move(-15,list)
        }
        if(toRight && canToRight){
            move(15,list)
        }
    }

    fun move(nbr:Int,list:List<Rectangle>){
        if(!touchOne(list)){
            x+=nbr
            update()
            if(touchOne(list)) x-=nbr
        }
        else{
            x+=nbr
            update()
            if(touchOne(list)) x-=nbr
        }
        update()
    }

    fun touch(rect: Rectangle):Boolean{
        return rectPlayer.right >= rect.x && rectPlayer.left <= rect.x+rect.w && rectPlayer.top <= rect.y+rect.h && rectPlayer.bottom >= rect.y
    }

    fun touchOne(list:List<Rectangle>):Boolean{
        for(rect in list){
            if(touch(rect)) return true
        }
        return false
    }

    fun jump(){
        y-=500
        update()
    }
    fun gravity(l:List<Rectangle>){
        if (rectPlayer.bottom < surface!!.height && !touchOne(l)){
            y += 10
            update()
            if(touchOne(l)) y -= 10
        }


        update()
    }
}