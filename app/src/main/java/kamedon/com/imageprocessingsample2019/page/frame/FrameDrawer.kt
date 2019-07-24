package kamedon.com.imageprocessingsample2019.page.frame

import android.graphics.*
import kamedon.com.imageprocessingsample.page.frame.Frame

/**
 * Created by kamei.hidetoshi on 2017/04/17.
 */
class FrameDrawer(val frameLayer: FrameDrawLayer, val photoLayer: List<PhotoDrawLayer>) {
    var drawRate = 0f


    companion object {
        fun define(frame: Frame, init: FrameDrawerBuilder.() -> Unit) = FrameDrawerBuilder(frame).apply {
            init()
        }.build()
    }

    fun draw(canvas: Canvas) {
        photoLayer.forEach {
            it.draw(canvas)
        }
        frameLayer.draw(canvas)
    }

    fun touch(x: Float, y: Float): PhotoDrawLayer? {
        photoLayer.forEach {
            if (it.touch(x, y)) {
                return it
            }
        }
        return null
    }

    fun destroy() {
        frameLayer.destroy()
        photoLayer.forEach(PhotoDrawLayer::destroy)
    }

    fun setup(width: Int, height: Int) {
        frameLayer.setup(width, height)
        photoLayer.forEach { it.setup(frameLayer.scale, frameLayer.offsetX, frameLayer.offsetY) }
    }

}

class FrameDrawerBuilder(var frame: Frame) {

    fun build() = FrameDrawer(
            frame.createFrameLayer(),
            frame.createPhotoDrawLayer())
}

