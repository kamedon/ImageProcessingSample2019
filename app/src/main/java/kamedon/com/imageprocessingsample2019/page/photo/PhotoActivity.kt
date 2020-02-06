package kamedon.com.imageprocessingsample2019.page.photo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityPhotoActvityBinding

class PhotoActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityPhotoActvityBinding>(R.layout.activity_photo_actvity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cat = BitmapFactory.decodeResource(resources, R.drawable.cat)
        binding.photoView.photo(cat)
        binding.photoView.onTouchItem = { x, y, items ->
            binding.uiView.showPopup(x, y, items)
        }
        binding.photoView.uiReset= {
            binding.uiView.hidePopup()
        }

    }
}
