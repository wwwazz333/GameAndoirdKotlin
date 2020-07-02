package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight:Int) : View(context) {


    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0
    val player:Player
    val enemy:Enemy

    var allRect:List<Rectangle>
    val white:Paint = Paint()

    //TODO("creat IA and diviser surface")

    init {
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight

        println("$screenSizeWidth * $screenSizeHeight")

        player = Player(this,0,500)
        enemy = Enemy(this,screenSizeWidth-100,500, player)
        enemy.canMove = false

        val w = Rectangle(((screenSizeWidth/3)*0.5).toInt(),(screenSizeHeight/3)*2,screenSizeWidth/5, 30, Color.RED)
        val w1 = Rectangle(((screenSizeWidth/3)*1.5).toInt(),(screenSizeHeight/3)*2,screenSizeWidth/5, 30, Color.RED)
        allRect = listOf<Rectangle>(w, w1)

        white.color = Color.WHITE



        update()
    }

    fun update(){
        //perso.set(x,y,x+50,y+50)
    }
    

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val pointerCount: Int = event!!.pointerCount


        for(i in 0..pointerCount-1){
            if(event.getX(i) > (screenSizeWidth / 5) && event.getX(i) < (screenSizeWidth/5)*2){
                player.toRight = true
                player.toLeft = false
            }

            if (event.getX(i) < screenSizeWidth / 5){
                player.toLeft = true
                player.toRight = false
            }

            if (event.getX(i) > (screenSizeWidth/5)*4 && event.getY(i) > screenSizeHeight/2){
                player.jump()
            }
            if(event.getX(i) > (screenSizeWidth/5)*4 && event.getY(i) < screenSizeHeight/2){
                player.attack(enemy)
            }
        }


        when (event.action) {

            MotionEvent.ACTION_DOWN -> {

            }
            MotionEvent.ACTION_UP -> {
                player.toRight = false

                player.toLeft = false

            }
        }

        return true
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        cnt++

        player.actions(allRect)

        enemy.actions(allRect)


        //dessin
        canvas!!.drawColor(Color.BLACK)//background

        player.draw(canvas)
        enemy.draw(canvas)
        for(rect in allRect){
            rect.draw(canvas)
        }
        canvas.drawLine((screenSizeWidth / 5).toFloat(), 0F, (screenSizeWidth / 5).toFloat(), screenSizeHeight.toFloat(),white)
        canvas.drawLine(((screenSizeWidth/5)*2).toFloat(), 0F, ((screenSizeWidth / 5)*2).toFloat(), screenSizeHeight.toFloat(),white)
        canvas.drawLine(((screenSizeWidth/5)*4).toFloat(), 0F, ((screenSizeWidth / 5)*4).toFloat(), screenSizeHeight.toFloat(),white)

        invalidate()
    }
}

