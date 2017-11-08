package com.daijie.ksyllabusapp.ui.photo

import android.os.Bundle
import com.daijie.ksyllabusapp.R
import kotlinx.android.synthetic.main.view_toolbar.*

/**
 * Created by daidaijie on 17-10-29.
 */
class PhotoPagerDeleteActivity : PhotoPagerActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        appToolbar.inflateMenu(R.menu.menu_delete)
        appToolbar.setOnMenuItemClickListener {
            when (it.itemId) {

            }
        }
    }
}