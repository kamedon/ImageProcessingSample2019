package kamedon.com.imageprocessingsample2019.page.translate

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.useCanvas

/**
 * Created by kamei.hidetoshi on 2017/04/12.
 */

class ImageTranslationView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val bitmaMatrix = Matrix()

    init {
        holder.addCallback(this)
    }

    var bitmap: Bitmap? = null

    fun setup(bitmap: Bitmap) {
        this.bitmap = bitmap
        invalidate()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        useCanvas {
            drawBg(this)
            bitmap?.let { image ->
                bitmaMatrix.setTranslate((-image.width + width) / 2f, (-image.height + height) / 2f)
                drawBitmap(image, bitmaMatrix, Paint())
            }
        }
    }

    fun translate(x: Float, y: Float) {
        useCanvas {
            drawBg(this)
            bitmap?.let { image ->
                bitmaMatrix.setTranslate(-image.width / 2f + x, -image.height  / 2f+ y)
                drawBitmap(image, bitmaMatrix, Paint())
            }
        }
    }

    fun drawBg(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }


}
