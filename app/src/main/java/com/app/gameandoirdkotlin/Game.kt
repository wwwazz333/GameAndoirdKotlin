package com.app.gameandoirdkotlin

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.Display
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class Game(context:Context, screenSizeWidth:Int, screenSizeHeight: Int): SurfaceView(context),SurfaceHolder.Callback {
    private val thread:MainThread
    private val player:RectPlayer
    private var playerPoint:Point? = null
    val displayMetrics = DisplayMetrics()
    private val screenSizeWidth:Int
    private val screenSizeHeight:Int




    init {
        holder.addCallback(this)
        thread = MainThread(holder, this)
        isFocusable = true
        playerPoint = Point()
        player = RectPlayer(Rect(0,200,200,0),Color.BLUE)
        player.update(playerPoint!!)
        this.screenSizeWidth = screenSizeWidth
        this.screenSizeHeight = screenSizeHeight

    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawColor(Color.BLACK)
        player.draw(canvas)
    }
    fun update(){
        if(player.toLeft) {
            playerPoint!!.set(playerPoint!!.x-15,playerPoint!!.y)
        }
        if(player.toRight){
            playerPoint!!.set(playerPoint!!.x+15,playerPoint!!.y)
        }

        player.update(playerPoint!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->{
                if(event!!.x < screenSizeWidth/3) player.toLeft = true
                else if(event!!.x > (screenSizeWidth/3)*2) player.toRight = true

            }
            MotionEvent.ACTION_UP ->{
                player.toLeft = false
                player.toRight = false

            }


        }

        invalidate()
        return true
    }
    override fun surfaceChanged(holder: SurfaceHolder, p1:Int, p2:Int, p3:Int) {
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while(retry){
            try {
                thread.setRunning(false)
                thread.join()
            }catch (e:Exception){
                e.printStackTrace()
            }
            retry = false
        }

    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        println("created")
    }




}