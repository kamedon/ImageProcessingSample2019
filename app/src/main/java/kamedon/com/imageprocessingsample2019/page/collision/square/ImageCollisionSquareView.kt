package kamedon.com.imageprocessingsample2019.page.collision.square

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.*

/**
 * Created by kamei.hidetoshi on 2017/04/12.
 */

class ImageCollisionSquareView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val imageMatrix = Matrix()

    init {
        holder.addCallback(this)
    }

    val degree = 30f
    var bitmap: Bitmap? = null

    fun setup(bitmap: Bitmap) {
        this.bitmap = bitmap
    }


    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        drawImage()
    }

    fun drawImage() {
        val paint = Paint(ANTI_ALIAS_FLAG)
        paint.style = Paint.Style.FILL
        val (drawX, drawY) = 50f to 50f
        val rad = degree * 180 / Math.PI
        useCanvas {
            drawBg(this)
            bitmap?.let {
                imageMatrix.postRotate(degree, it.width / 2f, it.height / 2f)
                imageMatrix.postTranslate(drawX, drawY)
                val (w, h) = width / 100f to height / 100f
                val rect= RectF(drawX, drawY, it.width + drawX, it.height + drawY)
                drawBitmap(it, imageMatrix, Paint())
                (0..19).forEach { row ->
                    (0..29).forEach { col ->
                        val (pX, pY) = col * w to row * h
                        if (isCollideDotRect(pX.toDouble(), pY.toDouble(), rect, rad)) {
                            paint.color = Color.RED
                        } else {
                            paint.color = Color.YELLOW
                        }
                        drawCircle(pX, pY, w / 2, paint)
                    }
                }
            }
        }
    }


    fun drawBg(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }


}
