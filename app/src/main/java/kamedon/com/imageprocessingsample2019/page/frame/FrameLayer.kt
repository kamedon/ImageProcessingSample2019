package kamedon.com.imageprocessingsample2019.page.frame

import android.graphics.*
import kamedon.com.imageprocessingsample.page.frame.DrawRectF
import kamedon.com.imageprocessingsample.util.isCollideDotRect

/**
 * Created by kamei.hidetoshi on 2017/04/18.
 */
class FrameDrawLayer(rectF: DrawRectF, bitmap: Bitmap) : DrawLayer(rectF, bitmap) {
    fun setup(screenWidth: Int, screenHeight: Int) {
        scale = Math.min(screenWidth / width.toFloat(), screenHeight / height.toFloat())
        offsetX = (width * scale - screenWidth) / 2f
        offsetY = (height * scale - screenHeight) / 2f
        updateMatrix()
    }
}

class PhotoDrawLayer(rectF: DrawRectF, bitmap: Bitmap) : DrawLayer(rectF, bitmap) {
    init {
        canvas.drawColor(Color.BLUE)
    }

    var photo: Bitmap? = null
    val photoMatrix: Matrix = Matrix()
    var photoScale = 1f

    fun touch(touchX: Float, touchY: Float): Boolean {
        val (x, y) = (touchX + offsetX) / scale to (touchY + offsetY) / scale
        return isCollideDotRect(x.toDouble(), y.toDouble(), rectF.rectF, Math.toRadians(rectF.degree.toDouble()))
    }

    fun setup(rate: Float, offsetX: Float, offsetY: Float) {
        scale = rate
        this.offsetX = offsetX
        this.offsetY = offsetY
        updateMatrix()
    }

    fun photo(bitmap: Bitmap) {
        photo?.recycle()
        photo = null
        photo = bitmap
        photo?.let {
            photoScale = Math.max(width / it.width.toFloat(), height / it.height.toFloat())
            photoMatrix.reset()
            photoMatrix.postScale(photoScale, photoScale)
            photoMatrix.postTranslate((width - it.width * photoScale) / 2f, (height - it.height * photoScale) / 2)
            canvas.drawBitmap(it, photoMatrix, Paint())
        }
    }

    override fun destroy() {
        super.destroy()
        photo?.recycle()
        photo = null
    }

    fun unFocus() {
    }

    fun focus() {
    }
}


open class DrawLayer(val rectF: DrawRectF, val bitmap: Bitmap) {
    var paint = Paint()

    var matrix = Matrix()

    val canvas = Canvas(bitmap)

    var offsetX = 0f
    var offsetY = 0f

    var scale = 1f

    val centerX by lazy {
        width / 2f
    }

    val centerY by lazy {
        height / 2f
    }

    val width by lazy {
        bitmap.width
    }

    val height by lazy {
        bitmap.height
    }

    open fun destroy() {
        bitmap.recycle()
    }


    fun clear() {
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
    }


    open fun updateMatrix() {
        matrix.reset()
        matrix.postRotate(rectF.degree, centerX, centerY)
        matrix.postScale(scale, scale)
        matrix.postTranslate(rectF.rectF.left * scale - offsetX, rectF.rectF.top * scale - offsetY)
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, matrix, paint)
    }

}
