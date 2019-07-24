package kamedon.com.imageprocessingsample2019.page.frame

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample.page.frame.DrawRectF
import kamedon.com.imageprocessingsample.page.frame.Frame
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityFrameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class FrameActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityFrameBinding>(R.layout.activity_frame)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {

            val bitmap = withContext(Dispatchers.Main) {
                //テスト用画像を作成
                val bitmap = Bitmap.createBitmap(540, 960, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                canvas.drawColor(Color.GRAY)
                //写真の位置を切り抜く
                Bitmap.createBitmap(250, 250, Bitmap.Config.ARGB_8888).apply {
                    val paint = Paint()
                    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                    paint.isAntiAlias = true
                    val matrix = Matrix()
                    matrix.postRotate(30f, width / 2f, height / 2f)
                    matrix.postTranslate(150f, 100f)
                    canvas.drawBitmap(this, matrix, paint)
                    recycle()
                }
                //写真の位置を切り抜く
                Bitmap.createBitmap(250, 200, Bitmap.Config.ARGB_8888).apply {
                    val paint = Paint()
                    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                    paint.isAntiAlias = true
                    val matrix = Matrix()
                    matrix.postRotate(355f, width / 2f, height / 2f)
                    matrix.postTranslate(150f, 550f)
                    canvas.drawBitmap(this, matrix, paint)
                    recycle()
                }

                bitmap

            }

            val drawer = withContext(Dispatchers.IO) {
                val frame = Frame.define(bitmap) {
                    //フレームで写真を配置する部分を記述
                    add(DrawRectF(RectF(149f, 99f, 250f + 151f, 250f + 101f), 30f))
                    add(DrawRectF(RectF(149f, 549f, 250f + 151f, 200f + 551f), 355f))
                }

                FrameDrawer.define(frame) {}
            }

            binding.surfaceView.setup(drawer)
        }

        binding.surfaceView.setOnTouchListener { v, event ->
            event?.run {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        binding.surfaceView.touch(event.x, event.y)
                        return@setOnTouchListener true
                    }
                    else -> {
                    }
                }
            }
            return@setOnTouchListener false
        }

        binding.surfaceView.focusListener = { layer ->
            GlobalScope.launch(Dispatchers.Main) {
                val bitmap = withContext(Dispatchers.IO) {
                    try {
                        BitmapFactory.decodeResource(resources, R.drawable.cat)
                    } catch (e: Exception) {
                        null
                    }

                }

                bitmap?.let {
                    layer.photo(it)
                    binding.surfaceView.update()
                }
            }
        }

    }
}

