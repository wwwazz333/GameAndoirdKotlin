package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.Surface
import android.view.View


class Drawing(context: Context?, screenSizeWidth:Int, screenSizeHeight:Int) : View(context) {


    val screenSizeWidth:Int
    val screenSizeHeight:Int
    var cnt = 0
    var pause = false
    val player:Player
    val enemy:Enemy

    var rightBtn:Button
    var leftBtn:Button
    var jumpBtn:Button
    var attackBtn:Button
    var pauseBtn:Button

    var rectList:List<Rectangle>
    val colones:ArrayList<Int> = ArrayList()
    val ligne:ArrayList<Int> = ArrayList()
    val btnList:List<Button>

    val white:Paint = Paint()


    init {
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight

        var nbrColones = 7
        var nbrLigne = 7
        for(i in 0 until nbrColones){
            colones.add((this.screenSizeWidth/nbrColones)*i)
            for(j in 0 until nbrLigne){
                ligne.add((this.screenSizeHeight/nbrLigne)*j)
            }
        }



        println("$screenSizeWidth * $screenSizeHeight")

        player = Player(this,0,500,colones[1], ligne[1])
        enemy = Enemy(this,screenSizeWidth-100,500, player,colones[1], ligne[1])
        enemy.canMove = false

        val w = Rectangle("platforme", colones[1],ligne[5],colones[2], ligne[1]/10, Color.RED)
        val w1 = Rectangle("platforme", colones[4],ligne[5],colones[2], ligne[1]/10, Color.RED)
        rectList = listOf<Rectangle>(w, w1)


        rightBtn = Button(this, R.drawable.right, colones[1], (ligne[6]-(ligne[1]/1.5)).toInt(), (colones[1]/1.5).toInt(), ligne[1], Runnable {
            player.toRight = true
            player.toLeft = false })
        leftBtn = Button(this, R.drawable.left, colones[0], (ligne[6]-(ligne[1]/1.5)).toInt(), (colones[1]/1.5).toInt(), ligne[1], Runnable {
            player.toLeft = true
            player.toRight = false })
        jumpBtn = Button(this, R.drawable.jump, colones[6], ligne[5], colones[1], ligne[2], Runnable { player.jump() })
        attackBtn = Button(this, R.drawable.sword, colones[6], ligne[4], colones[1]/2, ligne[1], Runnable { player.attack(enemy) })
        pauseBtn = Button(this, R.drawable.pause, screenSizeWidth/2-25, ligne[0], 50, 50, Runnable { pause = true })




        btnList = listOf(rightBtn, leftBtn, jumpBtn, attackBtn, pauseBtn)


        white.color = Color.WHITE

    }

    fun rightBtnSettings(x:Int, y:Int, w:Int){
        rightBtn = Button(this, R.drawable.right, x, y, w, ligne[1], Runnable {
            player.toRight = true
            player.toLeft = false })
    }
    fun leftBtnSettings(x:Int, y:Int, w:Int){
        leftBtn = Button(this, R.drawable.left, x, y, w, ligne[1], Runnable {
            player.toLeft = true
            player.toRight = false })
    }
    fun attackBtnSettings(x:Int, y:Int, w:Int){
        jumpBtn = Button(this, R.drawable.jump, x, y, w, ligne[1], Runnable { player.jump() })
    }
    fun jumpBtnSettings(x:Int, y:Int, w:Int){
        attackBtn = Button(this, R.drawable.sword, x, y, w, ligne[1], Runnable { player.attack(enemy) })
    }

    fun actions(){
        player.actions(rectList)

        enemy.actions(rectList)
    }
    

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val pointerCount: Int = event!!.pointerCount

        if(!pause){
            for(i in 0..pointerCount-1){

                for (btn in btnList){
                    btn.onClick(event.getX(i), event.getY(i))
                }
            }
        }



        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if(pause) pause = false

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

            canvas!!.drawText("Pause", colones[2].toFloat(), ligne[1].toFloat(),white)


        }
        else{
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

