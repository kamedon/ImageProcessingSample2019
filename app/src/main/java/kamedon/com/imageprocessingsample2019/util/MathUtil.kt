package kamedon.com.imageprocessingsample.util

import android.graphics.RectF

fun isCollideDotRect(x: Double, y: Double, rect: RectF, rad: Double): Boolean {
    val (pX, pY) = x - rect.centerX() to y - rect.centerY()
    val len = Math.sqrt(pX * pX + pY * pY)
    val rad2 = Math.atan2(pY, pX)
    val (pX2, pY2) = len * Math.cos(rad2 - rad) + rect.centerX() to len * Math.sin(rad2 - rad) + rect.centerY()
    return rect.contains(pX2.toFloat(), pY2.toFloat())
}


//xを回転
fun fx(x: Float, y: Float, rad: Double) = x * Math.cos(rad) - y * Math.sin(rad)

//yを回転
fun fy(x: Float, y: Float, rad: Double) = x * Math.sin(rad) + y * Math.cos(rad)
