package com.app.gameandoirdkotlin

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {
    var lunched:Boolean = true
    var moveToLeft:Boolean = false
    var moveToRight:Boolean = false
    var canMoveToLeft = true
    var canMoveToRight = true

    var player:Rect? = null
    var wall1:Rect? = null
    var t1:Thread? = null
    var t2:Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        hideSystemUI()

        wall.x = 1000F
        leftBtn.setOnTouchListener(View.OnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) moveToLeft = true
            else if (event.action == MotionEvent.ACTION_UP) moveToLeft = false
            true
        })
        rightBtn.setOnTouchListener(View.OnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN) moveToRight = true
            else if (event.action == MotionEvent.ACTION_UP) moveToRight = false
            true
        })
        player = Rect(findViewById<ImageView>(R.id.perso), 0F, 0F)
        t1 = Thread{play()}
        t2 = Thread{coll()}
        t1!!.start()
        t2!!.start()

        wall1 = Rect(findViewById<ImageView>(R.id.wall), 600F, 0F)
    }


    private fun coll(){
        while (lunched){
            when (player!!.touchWich(wall1!!)){
                1 -> {
                    canMoveToRight = false
                }
                2 -> {
                    canMoveToLeft = false
                }
                0 ->{
                    canMoveToLeft = true
                    canMoveToRight = true
                }
            }
        }
    }

    private fun play(){
        while(lunched){
            player!!.deplacement(moveToLeft && canMoveToLeft, moveToRight && canMoveToRight)

        }
    }



    private fun hideSystemUI() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    private fun showSystemUI() {
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    override fun onDestroy() {
        super.onDestroy()
        t1!!.join()
        t2!!.join()
    }
}