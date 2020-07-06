package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.Surface
import android.view.View


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight:Int) : Draw(context!!, screenSizeWidth, screenSizeHeight) {



    var cnt = 0
    var pause = false
    var gameOver = false
    val player:Player
    val enemy:Enemy


    val pauseMenuBtn:Button
    val gameOverMenuBtn:Button
    val resumeMenuBtn:Button
    val exitMenuBtn:Button

    var rectList:List<Rectangle>

    val btnList:List<Button>
    val pauseBtnList:List<Button>

    val white:Paint = Paint()


    init {
        println("$screenSizeWidth * $screenSizeHeight")

        player = Player(this,0,500,colones[1], ligne[1])
        enemy = Enemy(this,screenSizeWidth-100,500, player,colones[1], ligne[1])
        enemy.canMove = true

        val w = Rectangle("platforme", colones[1],ligne[5],colones[2], ligne[1]/10, Color.RED)
        val w1 = Rectangle("platforme", colones[4],ligne[5],colones[2], ligne[1]/10, Color.RED)
        rectList = listOf<Rectangle>(w, w1)

        pauseMenuBtn = Button(this,R.drawable.pause_menu,colones[2],ligne[0],500, 250, Runnable {  }, false)
        gameOverMenuBtn = Button(this,R.drawable.game_over_menu,colones[2],ligne[0],500, 250, Runnable {  }, false)
        gameOverMenuBtn.enabled = false
        resumeMenuBtn = Button(this,R.drawable.reprendre_menu,colones[2],ligne[3],500, 250, Runnable {  }, false)
        exitMenuBtn = Button(this,R.drawable.exit_menu,colones[2],ligne[5],500, 250, Runnable {  }, false)

        rightBtn.action = Runnable {
            player.toRight = true
            player.toLeft = false }
        leftBtn.action =  Runnable {
            player.toLeft = true
            player.toRight = false }
        jumpBtn.action =  Runnable { player.jump() }
        attackBtn.action =  Runnable { player.attack(enemy) }
        pauseBtn.action = Runnable { pause = true }






        btnList = listOf(rightBtn, leftBtn, jumpBtn, attackBtn, pauseBtn)
        pauseBtnList = listOf(pauseMenuBtn, gameOverMenuBtn, resumeMenuBtn, exitMenuBtn)


        white.color = Color.WHITE

    }



    fun actions(){
        player.actions(rectList)

        enemy.actions(rectList)
    }
    

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val pointerCount: Int = event!!.pointerCount

        if(!pause){
            for(i in 0 until pointerCount){

                for (btn in btnList){
                    btn.onClick(event.getX(i), event.getY(i))
                }
            }
        }



        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if(pause) {
                    //pause = false
                    for (btn in pauseBtnList){
                        btn.onClick(event.x, event.y)
                    }
                }

            }
            MotionEvent.ACTION_UP -> {
                if(!pause){
                    player.toRight = false

                    player.toLeft = false
                }
            }
        }

        return true
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        if (pause){
            canvas!!.drawColor(Color.BLACK)//background
            white.textSize = 200F
            //canvas.drawText("Pause", colones[2].toFloat(), ligne[1].toFloat(),white)
            for (btn in pauseBtnList){
                btn.draw(canvas)
            }



        }
        else{
            if(!player.alive) {
                pause = true
                pauseMenuBtn.enabled = false
                gameOverMenuBtn.enabled = true
            }
            cnt++

            actions()


            //dessin
            canvas!!.drawColor(Color.BLACK)//background

            player.draw(canvas)
            enemy.draw(canvas)
            for(rect in rectList){
                rect.draw(canvas)
            }
            for (btn in btnList){
                btn.draw(canvas)
            }
        }

        invalidate()
    }

    fun pause(){
        println("pause...")
        pause = true
    }
    fun resume(){
        println("resume...")
    }

}

