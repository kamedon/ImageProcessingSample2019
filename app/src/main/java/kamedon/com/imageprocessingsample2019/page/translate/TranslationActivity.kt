package kamedon.com.imageprocessingsample2019.page.translate

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityTranslationBinding

class TranslationActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityTranslationBinding>(R.layout.activity_translation)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.surfaceView.setup(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher))
        binding.surfaceView.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.surfaceView.translate(event.x, event.y)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.surfaceView.translate(event.x, event.y)
                }
            }
            false

        }
    }
}
