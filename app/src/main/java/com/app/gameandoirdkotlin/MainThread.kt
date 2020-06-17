package com.app.gameandoirdkotlin

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class MainThread(private val surfaceHolder: SurfaceHolder, private val game:Game):Thread() {
    private var running:Boolean = true

    fun setRunning(run:Boolean){
        this.running = run
    }

    override fun run() {
        var previousTick = System.currentTimeMillis()
        var wait = 0L

        while (running){
            previousTick = System.currentTimeMillis()
            canvas = null
            try {
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder){
                    this.game.update()
                    this.game.draw(canvas!!)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                if (canvas != null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
            Thread.sleep(wait)
            if(System.currentTimeMillis()<previousTick+16) wait = previousTick+16-System.currentTimeMillis()
            else if(System.currentTimeMillis()>=previousTick+16) wait = 0
        }


    }
    companion object{
        var canvas:Canvas? = null
    }
}