package kamedon.com.imageprocessingsample2019.page

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kamedon.com.imageprocessingsample2019.R
import kamedon.com.imageprocessingsample2019.page.collision.circle.CollisionCircleActivity
import kamedon.com.imageprocessingsample2019.page.collision.square.CollisionSquareActivity


/**
 * Created by kamei.hidetoshi on 2017/04/19.
 */

sealed class Page(val activity: Class<out Activity>) {
    fun title() = activity.simpleName
}

//object RotationPage : Page(RotationActivity::class.java)
//object FrameRotationPage : Page(FrameRotationActivity::class.java)
//object FrameDebugPage : Page(FrameDebugActivity::class.java)
//object TranslationPage : Page(TranslationActivity::class.java)
object CollisionCirclePage : Page(CollisionCircleActivity::class.java)

object CollisionSquarePage : Page(CollisionSquareActivity::class.java)
//object FramePage : Page(FrameActivity::class.java)
//object FrameUIPage : Page(FrameUIActivity::class.java)
//object EditPage : Page(EditActivity::class.java)


class PageAdapter(val context: Context) : RecyclerView.Adapter<PageAdapter.ViewHolder>() {
    //    val pages = listOf(RotationPage, FrameRotationPage, TranslationPage, CollisionCirclePage, CollisionSquarePage, FramePage, FrameUIPage, FrameDebugPage, EditPage)
    val pages = listOf(CollisionCirclePage, CollisionSquarePage)
    val inflater = LayoutInflater.from(context)!!


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pages[position])
        holder.textView.setOnClickListener { context.startActivity(Intent(context, pages[position].activity)) }
    }

    override fun getItemCount(): Int = pages.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(inflater.inflate(R.layout.list_page, parent, false))


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textView: Button = itemView.findViewById(R.id.text) as Button

        fun bind(page: Page) {
            textView.text = page.title()
        }

    }
}