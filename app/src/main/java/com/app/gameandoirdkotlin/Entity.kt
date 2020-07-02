package com.app.gameandoirdkotlin

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import kotlin.concurrent.thread

abstract class Entity(surface: Drawing, x:Int, y:Int) {
    var x:Int = x
    var y:Int = y
    var cntJump = 0
    var direction:Int
    abstract val lifeMax:Int
    abstract var life:Int


    var canMove = true
    var isJumping = false
    var canJump = true
    var canAttack = true
    var alive = true

    abstract val rect: Rect
    abstract val rectAttackRight:Rect
    abstract val rectAttackLeft:Rect
    var rectAttack:Rect = rectAttackRight

    var surface:Drawing? = null
    abstract val imageToRight: Bitmap
    abstract val imageToLeft: Bitmap




    init {
        this.surface = surface
        direction = if(x <= surface.width) 1
        else 0

        var test = Rect()
    }

    fun update(){
        rect.set(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)
        rectAttackRight.set(rect.centerX(), rect.top,rect.right + rangAttack, rect.bottom)
        rectAttackLeft.set(rect.left - rangAttack, rect.top,rect.centerX(), rect.bottom)
        if(direction == 1) rectAttack = rectAttackRight
        if(direction == 0) rectAttack = rectAttackLeft

    }
    fun draw(canvas: Canvas?){
        if(life > 0){
            update()

            if(direction == 1)canvas!!.drawBitmap(imageToRight,rect.left.toFloat(),rect.top.toFloat(), Paint())
            else if(direction == 0)canvas!!.drawBitmap(imageToLeft,rect.left.toFloat(),rect.top.toFloat(), Paint())
        }
        else{
            alive = false
        }
    }

    abstract fun actions(list: List<Rectangle>)

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
    fun attackTouch(entity: Entity):Boolean{
        update()
        return rectAttack.right >= entity.rect.left && rectAttack.left <= entity.rect.right && rectAttack.top <= entity.rect.bottom && rectAttack.bottom >= entity.rect.top && entity.alive

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

    fun attack(enemy:Entity){
        if(canAttack && alive){
            thread{
                canAttack = false
                if(attackTouch(enemy)){
                    enemy.life -= 25
                    println("life ${enemy} : ${enemy.life}")
                }
                else{
                    println("miss...")
                }
                Thread.sleep(500)
                canAttack = true
            }
        }

    }

}