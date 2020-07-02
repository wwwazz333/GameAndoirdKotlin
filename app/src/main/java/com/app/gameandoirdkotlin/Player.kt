package com.app.gameandoirdkotlin

import android.graphics.*



class Player(surface: Drawing, x:Int, y:Int):Entity(surface, x, y) {

    override val lifeMax: Int = 100
    override var life: Int = lifeMax


    override val rect:Rect
    override val rectAttackRight: Rect
    override val rectAttackLeft: Rect

    override val imageToRight:Bitmap
    override val imageToLeft:Bitmap

    var toRight = false
    var toLeft = false

    init {
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.player1)
        imageToRight = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*0.2).toInt(),
               (bitmapTemp.height*0.2).toInt(),true)

        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.player1_left)
        imageToLeft = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*0.2).toInt(),
            (bitmapTemp.height*0.2).toInt(),true)
        rect = Rect(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)
        rectAttackRight = Rect(rect.centerX(), rect.top,rect.right + rangAttack, rect.bottom)
        rectAttackLeft = Rect(rect.left - rangAttack, rect.top,rect.centerX(), rect.bottom)
        Rect()

    }

    override fun actions(list: List<Rectangle>){
        if(canMove){
            if(toLeft) {
                direction = 0
                move(-speedPlayer,list)
            }
            if(toRight){
                direction = 1
                move(speedPlayer,list)
            }
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
        gravity(list)
    }

}