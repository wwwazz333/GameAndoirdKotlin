package com.app.gameandoirdkotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

abstract class Entity(surface: Drawing, x:Int, y:Int) {
    var x:Int = x
    var y:Int = y
    abstract val rect: Rect
    var canJump = true
    abstract val imageToRight: Bitmap
    abstract val imageToLeft: Bitmap
    var surface:Drawing? = null
    var isJumping = false
    var cntJump = 0
    var direction:Int = 1

    init {
        this.surface = surface
    }

    fun update(){
        rect.set(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)

    }
    fun draw(canvas: Canvas?){
        update()

        if(direction == 1)canvas!!.drawBitmap(imageToRight,rect.left.toFloat(),rect.top.toFloat(), Paint())
        else if(direction == 0)canvas!!.drawBitmap(imageToLeft,rect.left.toFloat(),rect.top.toFloat(), Paint())
    }

    abstract fun actions(toRight:Boolean, toLeft:Boolean, list: List<Rectangle>)
    abstract fun actions(player:Player, list: List<Rectangle>)

    fun move(nbr:Int, list:List<Rectangle>){
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
        return this.rect.right >= rect.x && this.rect.left <= rect.x+rect.w && this.rect.top <= rect.y+rect.h && this.rect.bottom >= rect.y
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
        if (rect.bottom < surface!!.height && !touchOne(l) && !isJumping){
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