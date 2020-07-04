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
    val colones:ArrayList<Int> = ArrayList()
    val ligne:ArrayList<Int> = ArrayList()
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

