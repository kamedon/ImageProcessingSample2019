package kamedon.com.imageprocessingsample2019.page.edit

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityEditBinding>(R.layout.activity_edit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.cat)
        binding.image.setImageBitmap(bitmap)
    }
}
