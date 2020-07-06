package com.app.gameandoirdkotlin

import android.graphics.*



class Player(surface: Drawing, x:Int, y:Int, sizeColone:Int, sizeLigne:Int):Entity(surface, x, y, sizeColone, sizeLigne) {

    override val lifeMax: Int = 100
    override var life: Int = lifeMax


    override val rect:Rect

    override val imageToRight:Bitmap
    override val imageToLeft:Bitmap
    override val imageAttackRight: Bitmap
    override val imageAttackLeft: Bitmap

    var toRight = false
    var toLeft = false

    init {
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.player1)

        var heightFinal:Float = (sizeLigne).toFloat()
        var heightStart:Float = bitmapTemp.height.toFloat()
        var resize:Float = heightFinal/heightStart


        imageToRight = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*resize).toInt(),
               (bitmapTemp.height*resize).toInt(),true)

        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.player1_left)
        imageToLeft = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*resize).toInt(),
            (bitmapTemp.height*resize).toInt(),true)
        rect = Rect(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)


        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.attack_right)
        imageAttackRight = Bitmap.createScaledBitmap(bitmapTemp, rect.width(),
            rect.height(),true)

        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.attack_left)
        imageAttackLeft = Bitmap.createScaledBitmap(bitmapTemp, rect.width(),
            rect.height(),true)
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
            y -= ((sizeColone*1.2)/timeJump).toInt()
            update()
        }
        else{
            isJumping = false
            cntJump = 0
        }
        gravity(list)
    }

}