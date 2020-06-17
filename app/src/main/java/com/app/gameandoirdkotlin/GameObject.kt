package com.app.gameandoirdkotlin

import android.graphics.Canvas

interface GameObject {
    fun draw(canvas:Canvas)
    fun update()
}
