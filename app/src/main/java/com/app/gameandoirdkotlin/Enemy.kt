package com.app.gameandoirdkotlin

import android.graphics.*



class Enemy(surface: Drawing, x:Int, y:Int, player: Player, sizeColone:Int, sizeLigne:Int): Entity(surface, x, y, sizeColone, sizeLigne) {

    override val lifeMax: Int = 100
    override var life: Int = lifeMax

    override val rect:Rect
    override val rectAttackRight: Rect
    override val rectAttackLeft: Rect
    val player:Player = player

    override val imageToRight:Bitmap
    override val imageToLeft:Bitmap


    init {
        var resize = 0.4
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.enemy1_right)
        imageToRight = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*resize).toInt(),
            (bitmapTemp.height*resize).toInt(),true)

        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.enemy1_left)
        imageToLeft = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*resize).toInt(),
            (bitmapTemp.height*resize).toInt(),true)
        rect = Rect(this.x,this.y,this.x+imageToRight.width,this.y+imageToRight.height)
        rectAttackRight = Rect(rect.centerX(), rect.top,rect.right + rangAttack, rect.bottom)
        rectAttackLeft = Rect(rect.left - rangAttack, rect.top,rect.centerX(), rect.bottom)

    }

    override fun actions(list: List<Rectangle>){
        if(canMove){
            if(player.rect.centerX() < rect.centerX()) {
                direction = 0
                move(-speedEnemy, list)
            }
            if(player.rect.centerX() > rect.centerX()){
                direction = 1
                move(speedEnemy, list)
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