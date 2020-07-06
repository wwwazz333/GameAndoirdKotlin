package com.app.gameandoirdkotlin

import android.graphics.*



class Enemy(surface: Drawing, x:Int, y:Int, player: Player, sizeColone:Int, sizeLigne:Int): Entity(surface, x, y, sizeColone, sizeLigne) {

    override val lifeMax: Int = 100
    override var life: Int = lifeMax

    var cntTimePlayerUpper:Int = 0

    override val rect:Rect

    val player:Player = player

    override val imageToRight:Bitmap
    override val imageToLeft:Bitmap
    override val imageAttackRight: Bitmap
    override val imageAttackLeft: Bitmap


    init {
        var resize = 0.1
        var bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.enemy1_right)
        imageToRight = Bitmap.createScaledBitmap(bitmapTemp, (bitmapTemp.width*resize).toInt(),
            (bitmapTemp.height*resize).toInt(),true)

        bitmapTemp = BitmapFactory.decodeResource(surface.resources, R.drawable.enemy1_left)
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
            if(player.rect.centerX() < rect.centerX()) {//si player a gauche
                direction = 0
                if(player.rect.centerX() < rectAttackLeft.left+10){
                    move(-speedEnemy, list)
                }else if(attackTouch(player)){
                    attack(player)
                }
            }
            if(player.rect.centerX() > rect.centerX()){//si player a droite
                direction = 1
                if(player.rect.centerX() > rectAttackRight.right-10){
                    move(speedEnemy, list)
                }else if(attackTouch(player)){
                    attack(player)
                }
            }
            if(player.rect.bottom < rect.top){
                cntTimePlayerUpper++
                if(cntTimePlayerUpper>30){
                    cntTimePlayerUpper = 0
                    jump()
                }
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