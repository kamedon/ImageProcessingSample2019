package kamedon.com.imageprocessingsample2019.page.photo

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ScaleGestureDetector
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityPhotoActvityBinding
import kotlinx.android.synthetic.main.activity_photo_actvity.view.*

class PhotoActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityPhotoActvityBinding>(R.layout.activity_photo_actvity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val cat = BitmapFactory.decodeResource(resources, R.drawable.cat)
        binding.photoView.photo(cat)

    }
}
