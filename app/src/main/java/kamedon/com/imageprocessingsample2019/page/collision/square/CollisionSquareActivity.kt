package kamedon.com.imageprocessingsample2019.page.collision.square

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityCollisionCircleBinding
import kamedon.com.imageprocessingsample2019.databinding.ActivityCollisionSquareBinding

class CollisionSquareActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityCollisionSquareBinding>(R.layout.activity_collision_square)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.surfaceView.setup(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher))
    }
}
