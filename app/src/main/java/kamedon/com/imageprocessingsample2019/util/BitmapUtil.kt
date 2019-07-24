package kamedon.com.imageprocessingsample.util

import android.graphics.Bitmap

/**
 * Created by kamei.hidetoshi on 2017/04/15.
 */

val Bitmap.halfWidth get() = width / 2
val Bitmap.halfHeight get() = height / 2
val Bitmap.halfSize get() = halfWidth to halfHeight
