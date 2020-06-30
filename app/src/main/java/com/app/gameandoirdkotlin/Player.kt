package com.app.gameandoirdkotlin

import android.graphics.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread


const val timeJump = 13


class Player(surface: Drawing, x:Int, y:Int, idImage:Int? = null) {



    var x:Int = x
    var y:Int = y
    val rectPlayer:Rect
    var canToRight = true
    var canToLeft = true
    var canJump = true
    val image:Bitmap
    var surface:Drawing? = null
    var isJumping = false
    var cntJump = 0

    init {
        this.surface = surface
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, idImage!!)
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

    fun deplacement(toRight:Boolean, toLeft:Boolean, list: List<Rectangle>){
        if(toLeft && canToLeft) {
            move(-15,list)
        }
        if(toRight && canToRight){
            move(15,list)
        }
        if(isJumping && cntJump <= timeJump) {
            cntJump++
            y -= (surface!!.height/3)/timeJump
            update()
        }
        else{
            isJumping = false
            cntJump = 0
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
        if(!isJumping && canJump) {
            isJumping = true
        }
    }
    fun gravity(l:List<Rectangle>){
        if (rectPlayer.bottom < surface!!.height && !touchOne(l) && !isJumping){
            y += 15
            update()
            if(touchOne(l)) {
                y -= 15
                canJump = true
            }
            else canJump = false
        }
        else{
            canJump = true
        }

        update()
    }
}