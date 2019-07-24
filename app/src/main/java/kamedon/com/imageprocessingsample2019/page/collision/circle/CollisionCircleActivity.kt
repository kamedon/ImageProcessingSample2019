package kamedon.com.imageprocessingsample2019.page.collision.circle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityCollisionCircleBinding

class CollisionCircleActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityCollisionCircleBinding>(R.layout.activity_collision_circle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.surfaceView.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.surfaceView.collide(event.x, event.y)
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.surfaceView.collide(event.x, event.y)
                }
            }
            false
        }
    }
}
