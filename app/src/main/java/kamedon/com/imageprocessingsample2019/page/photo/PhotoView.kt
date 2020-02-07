package kamedon.com.imageprocessingsample2019.page.photo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.SurfaceHolder
import android.view.SurfaceView
import kamedon.com.imageprocessingsample.util.useCanvas

fun Canvas.clear() {
    drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
}

class PersonLocation(val x: Int, val y: Int, val name: String = "kamedon")

interface CanvasDrawable {
    var width: Int
    var height: Int

    fun setup(width: Int, height: Int)
    fun draw(canvas: Canvas)
    fun destroy()
}

interface BitmapCanvasDrawable : CanvasDrawable {
    var bitmap: Bitmap?
    val canvas: Canvas? get() = bitmap?.let { Canvas(it) }

    var matrix: Matrix

    override fun draw(canvas: Canvas) {
        bitmap?.let {
            canvas.drawBitmap(it, matrix, Paint())
        }
    }

    override fun setup(width: Int, height: Int) {
        destroy()
        this.width = width
        this.height = height
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    override fun destroy() {
        bitmap?.recycle()
        bitmap = null
    }

}


class BitmapLayer() : BitmapCanvasDrawable {
    override var width: Int = 0
    override var height: Int = 0
    override var bitmap: Bitmap? = null
    override var matrix = Matrix()

}

class BackgroundLayer : CanvasDrawable {
    override var width = 0
    override var height = 0
    var fitScale: Float = 0F
    var fitWidth: Float = 0F
    var fitHeight: Float = 0F

    var bgBitmap: Bitmap? = null
    val paint = Paint()
    val matrix: Matrix = Matrix()

    fun background(bitmap: Bitmap) {
        bgBitmap?.recycle()
        bgBitmap = null
        bgBitmap = bitmap
        fitSize()
    }

    fun reset() {
        matrix.reset()
        matrix.postScale(fitScale, fitScale)
    }


    override fun setup(width: Int, height: Int) {
        this.width = width
        this.height = height
        fitSize()
        reset()
    }

    fun fitSize() {
        this.fitScale = bgBitmap?.let {
            val scale = Math.min(this.width / it.width.toFloat(), this.height / it.height.toFloat())
            this.fitWidth = it.width * scale
            this.fitHeight = it.height * scale
            scale
        } ?: 0f
    }


    override fun draw(canvas: Canvas) {
        bgBitmap?.let {
            canvas.clear()
            canvas.drawBitmap(it, matrix, paint)
        }
    }

    override fun destroy() {
        bgBitmap?.recycle()
        bgBitmap = null
    }
}

class PersonLocationLayer(val layer: BitmapCanvasDrawable = BitmapLayer()) :
    BitmapCanvasDrawable by layer {

    var scale: Float = 1f
    val list = mutableListOf<PersonLocation>(
        PersonLocation(120, 300),
        PersonLocation(300, 100),
        PersonLocation(800, 200),
        PersonLocation(250, 258),
        PersonLocation(500, 280),
        PersonLocation(520, 450),
        PersonLocation(50, 420),
        PersonLocation(800, 70),
        PersonLocation(710, 300),
        PersonLocation(900, 440),
        PersonLocation(258, 250)
    )
    val paint = Paint().apply {
        color = Color.BLUE
        isAntiAlias = true
    }

    fun PersonLocation.draw(canvas: Canvas) {
        canvas.drawCircle(x.toFloat(), y.toFloat(), 10F * scale, paint)
    }

    fun update() {
        val c = canvas ?: return
        c.clear()
        list.forEach {
            it.draw(c)
        }
    }

    fun postScale(s: Float) {
        this.scale /= s
    }

    fun collide(x: Float, y: Float, matrix: Matrix): List<PersonLocation> {
        return list.filter {
            var pl = floatArrayOf(it.x.toFloat(), it.y.toFloat())
            matrix.mapPoints(pl)
            val dx = (pl[0] - x)
            val dy = (pl[1] - y)

            Log.d("test", "[=== colide ===]")
            Log.d("test", "colide:target: $x , $y")// ===> ${target[0]} , ${target[1]}")
            Log.d("test", "colide:pl:  ${it.x} , ${it.y} ===> ${pl[0]}, ${pl[1]}")
            Log.d("test", "colide: $dx , $dy")
            dx * dx + dy * dy <= 900
        }

    }
}

class UILayer(val layer: CanvasDrawable = BitmapLayer()) : CanvasDrawable by layer {

    var personLocations: List<PersonLocation>? = listOf()


    override fun draw(canvas: Canvas) {
    }

    fun touchPersonLocations(personLocations: List<PersonLocation>) {
        this.personLocations = personLocations
    }
}

class PhotoCanvasLayout(val layer: BitmapCanvasDrawable = BitmapLayer()) :
    BitmapCanvasDrawable by layer {

    var offsetX: Float = 0F
    var offsetY: Float = 0F

    val paint = Paint()

    val backgroundLayer = BackgroundLayer()
    val personLocationLayer = PersonLocationLayer()
    val uiLayer = UILayer()

    fun background(bitmap: Bitmap) {
        backgroundLayer.background(bitmap)
        layerSetup(width, height)
    }

    fun reset() {
        matrix.reset()
        matrix.postTranslate(this.offsetX, this.offsetY)
    }

    fun layerSetup(width: Int, height: Int) {
        if (width == 0) return
        backgroundLayer.setup(width, height)
        this.setup(width, height)

        val w = backgroundLayer.fitWidth
        val h = backgroundLayer.fitHeight
        Log.d("test", "s: ${backgroundLayer.fitScale}")
        Log.d("test", "W: $w / $width")
        Log.d("test", "H: $h / $height")
        this.offsetX = (width - w) / 2
        this.offsetY = (height - h) / 2

        reset()
        personLocationLayer.setup(w.toInt(), h.toInt())
        uiLayer.setup(width, height)
        update()
    }

    fun update() {
        personLocationLayer.update()
        canvas?.let {
            it.clear()
            backgroundLayer.draw(it)
            personLocationLayer.draw(it)
        }
    }

    override fun destroy() {
        backgroundLayer.destroy()
        personLocationLayer.destroy()
        uiLayer.destroy()
    }


    fun collide(x: Float, y: Float): List<PersonLocation> {

        val personLocations = personLocationLayer.collide(
            x,
            y,
            matrix
        )
        uiLayer.touchPersonLocations(personLocations)
        return personLocations
    }

    fun postScale(x: Float, y: Float, scale: Float) {
        matrix.postScale(scale, scale, x, y)
        personLocationLayer.postScale(scale)
        update()
    }

    fun postTranslate(diffX: Float, diffY: Float) {
        matrix.postTranslate(diffX, diffY)
        update()
    }

    fun touch(x: Float, y: Float): List<PersonLocation> {
        val items = collide(x, y)
        return items
    }


}

class PhotoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    val layout = PhotoCanvasLayout()
    val listener = ScaleGestureListener(callback = { x, y, scale ->
        layout.postScale(x, y, scale)
        update()
    }, endCallBack = {
        scaleEndTimestamp = System.currentTimeMillis()
    })
    var scaleEndTimestamp: Long = Long.MAX_VALUE

    val scaleGestureDetector = ScaleGestureDetector(context, listener)

    var onTouchItem: ((x: Float, y: Float, items: List<PersonLocation>) -> Unit)? = null
    var uiReset: (() -> Unit)? = null

    var prevX: Float = 0f
    var pervY: Float = 0f


    init {
        holder.addCallback(this)
        setOnTouchListener { v, event ->
            val x = event.x
            val y = event.y

            if (event.pointerCount == 1 && event.action == MotionEvent.ACTION_DOWN) {
                prevX = x
                pervY = y

                val items = layout.touch(x, y)
                if (items.isNotEmpty()) {
                    onTouchItem?.invoke(x, y, items)
                    return@setOnTouchListener true
                }
            }


            if (event.pointerCount == 1 && !scaleGestureDetector.isInProgress) {

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        return@setOnTouchListener true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        uiReset?.invoke()
                        val currentX = x
                        val currentY = y
                        val diffX = currentX - prevX;
                        val diffY = currentY - pervY;
                        Log.d("test", "MOVE $diffX / $diffY")
                        if (Math.abs(scaleEndTimestamp - System.currentTimeMillis()) > 100) {
                            layout.postTranslate(diffX, diffY)
                            update()
                        }
                        prevX = currentX
                        pervY = currentY
                    }
                }
                return@setOnTouchListener false
            }
            return@setOnTouchListener scaleGestureDetector.onTouchEvent(event)


        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        layout.destroy()
    }

    fun update() {
        useCanvas {
            clear()
            layout.draw(this)
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        layout.layerSetup(width, height)
        layout.update()
        update()

    }

    fun photo(cat: Bitmap) {
        layout.background(cat)
        layout.update()
        update()
    }
}


class ScaleGestureListener(
    val callback: (x: Float, y: Float, scale: Float) -> Unit,
    val endCallBack: () -> Unit
) :

    ScaleGestureDetector.SimpleOnScaleGestureListener() {

    var previousScale = 1f
    var focusX: Float = 0f
    var focusY: Float = 0f

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        previousScale = 1f
        focusX = detector.focusX;
        focusY = detector.focusY;
        return super.onScaleBegin(detector)
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {


        val currentScale = detector.scaleFactor
        val scale = 1 - previousScale + currentScale
        Log.d("test", "factor: ${currentScale} , $scale")
        callback(focusX, focusY, scale)
        previousScale = currentScale

        return false
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        super.onScaleEnd(detector)
        endCallBack()
    }

}
