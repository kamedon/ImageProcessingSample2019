package kamedon.com.imageprocessingsample.util

import android.graphics.Canvas
import android.view.SurfaceView

/**
 * Created by kamei.hidetoshi on 2017/04/11.
 */

inline fun SurfaceView.useCanvas(action: (Canvas.() -> Unit)) {
    holder?.lockCanvas()?.let {
        it.action()
        holder.unlockCanvasAndPost(it)
    }
}
