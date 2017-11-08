package com.daijie.ksyllabusapp.ui.photo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bm.library.PhotoView
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.ext.dp2px
import com.daijie.ksyllabusapp.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_photo_pager.*
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 17-10-29.
 */
open class PhotoPagerActivity : BaseActivity() {

    companion object {
        const val EXTRA_PHOTOS = "photos"
        const val EXTRA_POSITION = "position"

        @JvmStatic
        inline fun <reified T : PhotoPagerActivity>
                newIntent(context: Context, photos: Array<String>, position: Int = 0) =
                Intent(context, T::class.java).apply {
                    putExtra(EXTRA_PHOTOS, photos)
                    putExtra(EXTRA_POSITION, position)
                }
    }

    protected val photos: Array<String> by lazy {
        intent.getStringArrayExtra(EXTRA_PHOTOS)
    }

    protected val position: Int by lazy {
        intent.getIntExtra(EXTRA_POSITION, 0)
    }

    protected var currentPosition = 0

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    override val contentViewId = R.layout.activity_photo_pager

    override fun initView(savedInstanceState: Bundle?) {
        if (photos.size <= 1) {
            currentPageTextView.visibility = View.GONE
        }

        photoViewPager.pageMargin = dp2px(8f)
        photoViewPager.adapter = object : PagerAdapter() {

            override fun isViewFromObject(view: View?, `object`: Any?) = view == `object`

            override fun getCount() = photos.size

            override fun instantiateItem(container: ViewGroup?, position: Int): Any {
                val photoView = PhotoView(this@PhotoPagerActivity)
                photoView.scaleType = ImageView.ScaleType.FIT_CENTER
                photoView.enable()
                ImageLoader.loadImage(this@PhotoPagerActivity, photoView, photos[position], null)
                container?.addView(photoView)
                return photoView
            }

            override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
                container?.removeView(`object` as View?)
            }
        }
        setTitle("查看大图")
        currentPageTextView.text = "${position + 1}/${photos.size}"
        photoViewPager.currentItem = position
        currentPosition = position
        photoViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPageTextView.text = "${position + 1}/${photos.size}"
                currentPosition = position
            }

        })
    }
}