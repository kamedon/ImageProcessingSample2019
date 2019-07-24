package kamedon.com.imageprocessingsample.page.frame

import android.graphics.Bitmap
import android.graphics.RectF
import kamedon.com.imageprocessingsample2019.page.frame.FrameDrawLayer
import kamedon.com.imageprocessingsample2019.page.frame.PhotoDrawLayer

/**
 * Created by kamei.hidetoshi on 2017/04/16.
 */

class Frame(val bitmap: Bitmap, val rectFs: List<DrawRectF>) {

    companion object {
        fun define(bitmap: Bitmap, init: (FrameBuilder.() -> Unit)): Frame {
            val builder = FrameBuilder(bitmap)
            builder.init()
            return builder.build()
        }
    }

    fun destroy() {
        bitmap.recycle()
    }

    val width by lazy {
        bitmap.width
    }

    val height by lazy {
        bitmap.height
    }

    fun createFrameLayer(): FrameDrawLayer = FrameDrawLayer(DrawRectF(RectF(0f, 0f, bitmap.width.toFloat(), bitmap.height.toFloat()), 0f), bitmap)

    fun createPhotoDrawLayer(): List<PhotoDrawLayer> = rectFs.map {
        PhotoDrawLayer(it, Bitmap.createBitmap(it.rectF.width().toInt(), it.rectF.height().toInt(), Bitmap.Config.ARGB_8888))
    }


}


class FrameBuilder(val bitmap: Bitmap) {
    private val rectFs = mutableListOf<DrawRectF>()

    fun add(rect: DrawRectF) {
        rectFs.add(rect)
    }

    fun build(): Frame {
        return Frame(bitmap, rectFs)
    }
}

/**
 * rectFは位置と大きさ
 * degreeは中心からの角度
 */
data class DrawRectF(val rectF: RectF, val degree: Float)
