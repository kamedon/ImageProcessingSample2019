package kamedon.com.imageprocessingsample2019.page.rotation.frame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityFrameRotationBinding
import kotlinx.coroutines.*

class FrameRotationActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityFrameRotationBinding>(R.layout.activity_frame_rotation)

    var job = SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding
    }

    override fun onStart() {
        super.onStart()
        job = SupervisorJob()
        GlobalScope.launch(Dispatchers.Main + job) {
            while (true) {
                val task = async(Dispatchers.IO + job) {
                    binding.surfaceView.postDegree(1f)
                }
                delay(100)
                task.await()
                binding.surfaceView.update()
            }
        }

        binding.surfaceView
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}
