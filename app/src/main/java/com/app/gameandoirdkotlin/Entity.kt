package com.app.gameandoirdkotlin

import android.graphics.*
import kotlin.concurrent.thread

abstract class Entity(surface: Drawing, x:Int, y:Int, sizeColone:Int, sizeLigne:Int) {
    var x:Int = x
    var y:Int = y
    var sizeColone = sizeColone
    var sizeLigne = sizeLigne
    var cntJump = 0
    var direction:Int
    abstract val lifeMax:Int
    abstract var life:Int


    var canMove = true
    var isJumping = false
    var canJump = true
    var canAttack = true
    var alive = true
    var attacking = false

    abstract val rect: Rect
    val rectAttackRight:Rect = Rect()
    val rectAttackLeft:Rect = Rect()
    var rectAttack:Rect = rectAttackRight

    var surface:Drawing? = null
    abstract val imageToRight: Bitmap
    abstract val imageToLeft: Bitmap
    abstract val imageAttackRight:Bitmap
    abstract val imageAttackLeft:Bitmap




    init {
        this.surface = surface
        direction = if(x <= surface.width) 1
        else 0
    }

    fun update(){
        rect.set(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)
        rectAttackRight.set(rect.centerX(), rect.top,rect.centerX() + rect.width(), rect.bottom)
        rectAttackLeft.set(rect.centerX() - rect.width(), rect.top,rect.centerX(), rect.bottom)
        if(direction == 1) rectAttack = rectAttackRight
        if(direction == 0) rectAttack = rectAttackLeft

    }
    fun draw(canvas: Canvas?){
        if(life > 0){
            update()

            if(direction == 1){//vers la droite
                if (attacking) canvas!!.drawBitmap(imageAttackRight,rectAttackRight.left.toFloat(), rectAttackRight.top.toFloat(), Paint())
                canvas!!.drawBitmap(imageToRight,rect.left.toFloat(),rect.top.toFloat(), Paint())

            }
            else if(direction == 0){//vers la gauche
                if (attacking) canvas!!.drawBitmap(imageAttackLeft,rectAttackLeft.left.toFloat(), rectAttackLeft.top.toFloat(), Paint())
                canvas!!.drawBitmap(imageToLeft,rect.left.toFloat(),rect.top.toFloat(), Paint())

            }
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
        if(rect.type == "wall"){
            return this.rect.right >= rect.x && this.rect.left <= rect.x+rect.w && this.rect.top <= rect.y+rect.h && this.rect.bottom >= rect.y
        }
        else if(rect.type == "platforme"){
            return this.rect.right >= rect.x && this.rect.left <= rect.x+rect.w && this.rect.top <= rect.y+rect.h && this.rect.bottom >= rect.y && this.rect.bottom < rect.rect.bottom
        }
        else{
            return false
        }
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
                attacking = true
                if(attackTouch(enemy)){
                    enemy.life -= 25
                    println("life ${enemy} : ${enemy.life}")
                }
                else{
                    println("miss...")
                }
                Thread.sleep(250)
                attacking = false
                Thread.sleep(250)
                canAttack = true
            }
        }

    }

}