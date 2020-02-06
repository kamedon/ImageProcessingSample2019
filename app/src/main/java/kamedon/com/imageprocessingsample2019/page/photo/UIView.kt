package kamedon.com.imageprocessingsample2019.page.photo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kamedon.com.imageprocessingsample2019.R

class UIView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val popupView = View.inflate(context, R.layout.popup, null)

    init {
        addView(popupView)
        popupView.visibility = View.INVISIBLE
        popupView.layoutParams = popupView.layoutParams.also {
            it.width = (100 * resources.displayMetrics.density).toInt()
        }
    }


    fun showPopup(x: Float, y: Float, items: List<PersonLocation>) {
        Log.d("test", "popup: ${popupView.width} , ${popupView.height}")
        popupView.visibility = View.VISIBLE
        popupView.x = x - popupView.width / 2
        popupView.y = y + 20
        popupView.findViewById<TextView>(R.id.text).text = items.map { it.name }.joinToString("\n")
    }

    fun hidePopup() {
        popupView.visibility = View.GONE
    }
}