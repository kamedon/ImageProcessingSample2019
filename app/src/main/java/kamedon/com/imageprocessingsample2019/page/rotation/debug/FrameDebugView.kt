package kamedon.com.imageprocessingsample2019.page.rotation.debug

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.fx
import kamedon.com.imageprocessingsample.util.fy
import kamedon.com.imageprocessingsample.util.useCanvas

/**
 * Created by kamei.hidetoshi on 2017/04/21.
 */
class FrameDebugView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    lateinit var baseBitmap: Bitmap
    val wBaseMatrix = Matrix()
    val hBaseMatrix = Matrix()
    val sqBaseMatrix = Matrix()

    lateinit var wBitmap: Bitmap
    val wMatrix = Matrix()
    lateinit var hBitmap: Bitmap
    val hMatrix = Matrix()
    lateinit var sqBitmap: Bitmap
    val sqMatrix = Matrix()


    var degree = 0f

    val alphaPaint = Paint().apply {
        alpha = 128
        isAntiAlias = true
    }

    val paint = Paint()

    init {
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    var offsetX: Float = 0f

    var offsetY: Float = 0f

    override fun surfaceCreated(holder: SurfaceHolder?) {
        val (w, h) = width / 8f to height / 8f

        offsetX = width / 2f
        offsetY = height / 7f

        baseBitmap = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888).apply { drawColor(Color.BLACK) }
        wBitmap = Bitmap.createBitmap(w.toInt(), h.toInt() + 150, Bitmap.Config.ARGB_8888).apply { drawColor(Color.BLUE) }
        hBitmap = Bitmap.createBitmap(w.toInt() + 150, h.toInt(), Bitmap.Config.ARGB_8888).apply { drawColor(Color.BLUE) }
        sqBitmap = Bitmap.createBitmap(h.toInt(), h.toInt(), Bitmap.Config.ARGB_8888).apply { drawColor(Color.BLUE) }
        postRotate(0f)
    }

    fun postRotate(d: Float) {
        this.degree = d


        wBaseMatrix.reset()
        hBaseMatrix.reset()
        sqBaseMatrix.reset()
        wBaseMatrix.postTranslate(offsetX - baseBitmap.width / 2f, offsetY - baseBitmap.height / 2f)
        hBaseMatrix.postTranslate(offsetX - baseBitmap.width / 2f, offsetY * 3 - baseBitmap.height / 2f)
        sqBaseMatrix.postTranslate(offsetX - baseBitmap.width / 2f, offsetY * 5 - baseBitmap.height / 2f)

        var rectF = RectF(0f, 0f, baseBitmap.width.toFloat(), baseBitmap.height.toFloat())
        val rad = Math.toRadians(degree.toDouble())
        var cRectF = circumscribedSquare(rectF, rad)

        var s = Math.max(cRectF.width() / wBitmap.width, cRectF.height() / wBitmap.height)
        wMatrix.reset()
        wMatrix.postRotate(degree, wBitmap.width / 2f, wBitmap.height / 2f)
        wMatrix.postScale(s, s, wBitmap.width / 2f, wBitmap.height / 2f)
        wMatrix.postTranslate(offsetX - wBitmap.width / 2f, offsetY - wBitmap.height / 2f)

        s = Math.max(cRectF.width() / hBitmap.width, cRectF.height() / hBitmap.height)
        hMatrix.reset()
        hMatrix.postRotate(degree, hBitmap.width / 2f, hBitmap.height / 2f)
        hMatrix.postScale(s, s, hBitmap.width / 2f, hBitmap.height / 2f)
        hMatrix.postTranslate(offsetX - hBitmap.width / 2f, offsetY * 3 - hBitmap.height / 2f)

        s = Math.max(cRectF.width() / sqBitmap.width, cRectF.height() / sqBitmap.height)
        sqMatrix.reset()
        sqMatrix.postRotate(degree, sqBitmap.width / 2f, sqBitmap.height / 2f)
        sqMatrix.postScale(s, s, sqBitmap.width / 2f, sqBitmap.height / 2f)
        sqMatrix.postTranslate(offsetX - sqBitmap.width / 2f, offsetY * 5 - sqBitmap.height / 2f)

        update()

    }

    fun update() {
        useCanvas {
            drawColor(Color.WHITE)
            drawBitmap(baseBitmap, wBaseMatrix, paint)
            drawBitmap(wBitmap, wMatrix, alphaPaint)

            drawBitmap(baseBitmap, hBaseMatrix, paint)
            drawBitmap(hBitmap, hMatrix, alphaPaint)

            drawBitmap(baseBitmap, sqBaseMatrix, paint)
            drawBitmap(sqBitmap, sqMatrix, alphaPaint)
        }
    }

    fun Bitmap.drawColor(color: Int) {
        Canvas(this).run {
            drawColor(color)
        }
    }

    fun circumscribedSquare(rectF: RectF, rad: Double): RectF {
        val (cx, cy) = rectF.width() / 2 to rectF.height() / 2

        val (x1, y1) = fx(-cx, -cy, rad) to fy(-cx, -cy, rad)
        val (x2, y2) = fx(-cx, cy, rad) to fy(-cx, cy, rad)
        val (x3, y3) = fx(cx, -cy, rad) to fy(cx, -cy, rad)
        val (x4, y4) = fx(cx, cy, rad) to fy(cx, cy, rad)

        return RectF(listOf(x1, x2, x3, x4).min()!!.toFloat(), listOf(y1, y2, y3, y4).min()!!.toFloat(), listOf(x1, x2, x3, x4).max()!!.toFloat(), listOf(y1, y2, y3, y4).max()!!.toFloat())
    }


}
