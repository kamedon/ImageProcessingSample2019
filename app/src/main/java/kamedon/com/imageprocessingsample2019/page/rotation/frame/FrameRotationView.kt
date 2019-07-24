package kamedon.com.imageprocessingsample2019.page.rotation.frame

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.fx
import kamedon.com.imageprocessingsample.util.fy
import kamedon.com.imageprocessingsample.util.useCanvas

/**
 * Created by kamei.hidetoshi on 2017/04/20.
 */
class FrameRotationView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val baseBitmap by lazy {
        Bitmap.createBitmap(width / 4, height / 4, Bitmap.Config.ARGB_8888).apply {
            Canvas(this).run {
                drawColor(Color.BLACK)
            }
        }
    }

    val rotationBitmap by lazy {
        Bitmap.createBitmap(width / 4, height / 4, Bitmap.Config.ARGB_8888).apply {
            Canvas(this).run {
                drawColor(Color.YELLOW)
            }
        }
    }

    val noScaleBitmap by lazy {
        Bitmap.createBitmap(width / 4, height / 4, Bitmap.Config.ARGB_8888).apply {
            Canvas(this).run {
                drawColor(Color.MAGENTA)
            }
        }
    }
    val matrix1: Matrix = Matrix()
    val baseMatrix: Matrix = Matrix()

    val rotationMatrix: Matrix = Matrix()

    var degree = 0f

    val centerY by lazy {
        height / 2f
    }
    val centerX by lazy {
        width / 2f
    }

    val paint by lazy {
        Paint().apply {
            alpha = 128
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        useCanvas {
            baseMatrix.reset()
            rotationMatrix.reset()
            baseMatrix.postTranslate((width - baseBitmap.width) / 2f, (height - baseBitmap.height) / 2f)
            rotationMatrix.postTranslate((width - baseBitmap.width) / 2f, (height - baseBitmap.height) / 2f)
            update(this)
        }
    }

    fun update() {
        useCanvas {
            update(this)
        }
    }

    private fun update(canvas: Canvas) {
        canvas.drawColor(Color.GRAY)
        canvas.drawBitmap(baseBitmap, baseMatrix, Paint())
        canvas.drawBitmap(rotationBitmap, rotationMatrix, paint)
        canvas.drawBitmap(noScaleBitmap, matrix1, paint)
        canvas.drawRect(rectF, Paint().apply {
            color = Color.GREEN
            style = Paint.Style.STROKE
            strokeWidth = 10f
        })

    }


    init {
        holder.addCallback(this)
    }

    private var rectF = RectF()

    fun postDegree(degree: Float) {
        this.degree = (this.degree + degree) % 360f

        val rad = Math.toRadians(this.degree.toDouble())
        val cx = rotationBitmap.width / 2f
        val cy = rotationBitmap.height / 2f
        //外接する四角を計算する
        rectF.left = (listOf(fx(-cx, -cy, rad), fx(-cx, cy, rad), fx(cx, -cy, rad), fx(cx, cy, rad)).min()!! + centerX).toFloat()
        rectF.top = (listOf(fy(-cx, -cy, rad), fy(-cx, cy, rad), fy(cx, -cy, rad), fy(cx, cy, rad)).min()!! + centerY).toFloat()
        rectF.right = (listOf(fx(-cx, -cy, rad), fx(-cx, cy, rad), fx(cx, -cy, rad), fx(cx, cy, rad)).max()!! + centerX).toFloat()
        rectF.bottom = (listOf(fy(-cx, -cy, rad), fy(-cx, cy, rad), fy(cx, -cy, rad), fy(cx, cy, rad)).max()!! + centerY).toFloat()

        //外接する四角の大きさまで拡大する必要がある
        val s = Math.max(rectF.width() / rotationBitmap.width.toFloat(), rectF.height() / rotationBitmap.height.toFloat())
        rotationMatrix.reset()
        rotationMatrix.postRotate(this.degree, rotationBitmap.width / 2f, rotationBitmap.height / 2f)
        rotationMatrix.postScale(s, s, rotationBitmap.width / 2f, rotationBitmap.height / 2f)
        rotationMatrix.postTranslate((width - rotationBitmap.width) / 2f, (height - rotationBitmap.height) / 2f)

        matrix1.reset()
        matrix1.postRotate(this.degree, rotationBitmap.width / 2f, rotationBitmap.height / 2f)
        matrix1.postScale(1f, 1f, rotationBitmap.width / 2f, rotationBitmap.height / 2f)
        matrix1.postTranslate((width - rotationBitmap.width) / 2f, (height - rotationBitmap.height) / 2f)


    }

}
