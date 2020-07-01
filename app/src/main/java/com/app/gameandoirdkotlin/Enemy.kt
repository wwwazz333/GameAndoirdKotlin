package com.app.gameandoirdkotlin

import android.graphics.*



class Enemy(surface: Drawing, x:Int, y:Int, idImage:Int? = null) {



    var x:Int = x
    var y:Int = y
    val rectEnemy:Rect
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
        rectEnemy = Rect(this.x,this.y,this.x+image.width,this.y+image.height)

    }

    fun update(){
        rectEnemy.set(this.x,this.y,this.x+image.width,this.y+image.height)

    }
    fun draw(canvas: Canvas?){
        update()

        canvas!!.drawBitmap(image,rectEnemy.left.toFloat(),rectEnemy.top.toFloat(), Paint())
    }

    fun deplacement(player:Player, list: List<Rectangle>){
        if(player.rectPlayer.centerX() < rectEnemy.centerX()) {
            move(-speedEnemy,list)
        }
        if(player.rectPlayer.centerX() > rectEnemy.centerX()){
            move(speedEnemy,list)
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
        return rectEnemy.right >= rect.x && rectEnemy.left <= rect.x+rect.w && rectEnemy.top <= rect.y+rect.h && rectEnemy.bottom >= rect.y
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
        if (rectEnemy.bottom < surface!!.height && !touchOne(l) && !isJumping){
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