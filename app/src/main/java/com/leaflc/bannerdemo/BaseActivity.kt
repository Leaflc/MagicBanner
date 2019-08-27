package com.leaflc.bannerdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.leaflc.banner.Banner
import com.leaflc.banner.listener.OnItemClickListener
import com.leaflc.banner.snap_helper.AutoPlayRecyclerView
import com.leaflc.banner.snap_helper.ViewPagerLayoutManager
import com.leaflc.magicbanner.holder.CBViewHolderCreator
import com.leaflc.magicbanner.holder.Holder
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity<V : ViewPagerLayoutManager, S : SettingPopUpWindow> :
    AppCompatActivity() {

    lateinit var viewPagerLayoutManager: V

    var settingPopUpWindow: S? = null


    protected abstract fun createLayoutManager(): V

    protected abstract fun createSettingPopUpWindow(): S

    private val localImages = arrayListOf(
        R.drawable.item1,
        R.drawable.item2,
        R.drawable.item3

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        title = intent.getCharSequenceExtra(MainActivity.INTENT_TITLE)
        viewPagerLayoutManager = createLayoutManager()

        //本地图片例子
        (banner as Banner<Int>).setPages(
            object : CBViewHolderCreator<Int> {
                override fun createHolder(itemView: View): Holder<Int> {
                    return LocalImageHolderView(itemView)
                }

                override fun getLayoutId(): Int {
                    return R.layout.item_image
                }

            }, localImages
        ).setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(this@BaseActivity, "点击了第" + position + "个", Toast.LENGTH_SHORT)
                    .show()
            }
        }).setLayoutManager(viewPagerLayoutManager)
            .setPageIndicator(
                intArrayOf(
                    R.drawable.ic_page_indicator,
                    R.drawable.ic_page_indicator_focused
                )
            ).onPageChangeListener = object : ViewPagerLayoutManager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(index: Int) {
                Toast.makeText(this@BaseActivity, "select: $index", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun showDialog() {
        settingPopUpWindow = createSettingPopUpWindow()
        settingPopUpWindow?.showAtLocation(banner, Gravity.CENTER, 0, 0)
    }


    fun getRecyclerView(): AutoPlayRecyclerView {
        return banner.recyclerView
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        val settings = menu?.findItem(R.id.setting)
        val settingIcon =
            VectorDrawableCompat.create(resources, R.drawable.ic_settings_white_48px, null)
        settings?.icon = settingIcon
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                showDialog()
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (settingPopUpWindow != null && settingPopUpWindow!!.isShowing)
            settingPopUpWindow?.dismiss()
    }


    class LocalImageHolderView(itemView: View) : Holder<Int>(itemView) {
        override fun updateUI(data: Int) {
            imageView!!.setImageResource(data)
        }

        override fun initView(itemView: View) {
            imageView = itemView.findViewById(R.id.image)
        }

        private var imageView: ImageView? = null

    }
}
