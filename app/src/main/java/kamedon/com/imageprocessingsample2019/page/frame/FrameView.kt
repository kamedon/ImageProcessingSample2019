package kamedon.com.imageprocessingsample2019.page.frame

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.useCanvas

/**
 * Created by kamei.hidetoshi on 2017/04/15.
 */
class FrameView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val bitmapMatrix = Matrix()
    var focusListener: ((PhotoDrawLayer) -> Unit)? = null
    var unFocusListener: ((PhotoDrawLayer) -> Unit)? = null

    init {
        holder.addCallback(this)
    }

    lateinit var drawer: FrameDrawer

    fun setup(drawer: FrameDrawer) {
        this.drawer = drawer
        drawer.setup(width, height)
        update()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        useCanvas {
            drawBg(this)
        }
    }

    fun update() {
        useCanvas {
            drawBg(this)
            drawer.draw(this)
        }
    }

    fun drawBg(canvas: Canvas) {
        canvas.drawColor(Color.WHITE)
    }

    fun destroy() {
        drawer.destroy()
    }

    var current: PhotoDrawLayer? = null

    fun touch(x: Float, y: Float) {
        val layer = drawer.touch(x, y)
        current?.run {
            unFocus()
            unFocusListener?.invoke(this)
        }
        layer?.run {
            focus()
            focusListener?.invoke(this)
        }
        current = layer
        update()
    }

}
