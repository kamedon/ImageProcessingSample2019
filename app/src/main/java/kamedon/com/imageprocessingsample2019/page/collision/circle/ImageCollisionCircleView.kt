package kamedon.com.imageprocessingsample2019.page.collision.circle

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.useCanvas
import kotlin.text.Typography.degree

/**
 * Created by kamei.hidetoshi on 2017/04/12.
 */

class ImageCollisionCircleView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    init {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL

        useCanvas {
            drawBg(this)
            initDraw(this)
        }
    }

    fun collide(x: Float, y: Float) {
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        val color = if (isCollideDotCircle(x, y, (-50f + width) / 2, (-50f + height) / 2, 50f)) {
            Color.RED
        } else {
            Color.YELLOW
        }
        useCanvas {
            drawBg(this)
            initDraw(this)
            paint.color = color
            drawCircle(x, y, 25f, paint)
        }
    }

    /*
     * 2点間の距離が半径以下のとき衝突
     * 距離の二乗のまま比較すると速度アップ
     */
    private fun isCollideDotCircle(x: Float, y: Float, circleX: Float, circleY: Float, r: Float) =
            Math.pow(x - circleX.toDouble(), 2.0) + Math.pow((y - circleY.toDouble()), 2.0) <= r * r

    fun drawBg(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }

    fun initDraw(canvas: Canvas) {
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL
        canvas.drawCircle((-50f + width) / 2, (-50f + height) / 2, 50f, paint)
    }


}
