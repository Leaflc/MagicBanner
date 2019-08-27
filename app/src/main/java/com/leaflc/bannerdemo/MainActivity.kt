package com.leaflc.bannerdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.leaflc.bannerdemo.carousel.CarouselLayoutActivity
import com.leaflc.bannerdemo.circle.CircleLayoutActivity
import com.leaflc.bannerdemo.circlescale.CircleScaleLayoutActivity
import com.leaflc.bannerdemo.gallery.GalleryLayoutActivity
import com.leaflc.bannerdemo.rotate.RotateLayoutActivity
import com.leaflc.bannerdemo.scale.ScaleLayoutActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    companion object {
        const val INTENT_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_circle.setOnClickListener(this)
        bt_circle_scale.setOnClickListener(this)
        bt_elevate_scale.setOnClickListener(this)
        bt_gallery.setOnClickListener(this)
        bt_rotate.setOnClickListener(this)
        bt_scale.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_circle -> startActivity(CircleLayoutActivity::class.java, v)
            R.id.bt_circle_scale -> startActivity(CircleScaleLayoutActivity::class.java, v)
            R.id.bt_elevate_scale -> startActivity(CarouselLayoutActivity::class.java, v)
            R.id.bt_gallery -> startActivity(GalleryLayoutActivity::class.java, v)
            R.id.bt_rotate -> startActivity(RotateLayoutActivity::class.java, v)
            R.id.bt_scale -> startActivity(ScaleLayoutActivity::class.java, v)
        }
    }

    private fun startActivity(clz: Class<*>, view: View) {
        val intent = Intent(this, clz)
        if (view is Button) {
            intent.putExtra(INTENT_TITLE, (view as Button).text)
        }
        startActivity(intent)
    }
}
