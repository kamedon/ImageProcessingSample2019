package kamedon.com.imageprocessingsample2019.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.wada811.databinding.dataBinding
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by dataBinding<ActivityMainBinding>(R.layout.activity_main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.adapter = PageAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
