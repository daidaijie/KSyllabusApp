package com.daijie.ksyllabusapp.littlevp.selectimg

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SchoolDymaticPhotoSelectedAdapter
import com.daijie.ksyllabusapp.ui.dymatic.circle.send.SchoolCircleSendActivity
import com.daijie.ksyllabusapp.utils.MatissePathUtils
import com.tbruyelle.rxpermissions.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import java.io.File
import java.util.*

/**
 * Created by daidaijie on 2017/11/7.
 */
class SelectImageViewImpl(
        private val activity: Activity,
        private val schoolDymaticPhotoSelectedAdapter: SchoolDymaticPhotoSelectedAdapter
) : SelectImageView {

    private fun getImageMaxSize() = schoolDymaticPhotoSelectedAdapter.getMaxSelectedNum()

    private fun onImagePickup(uris: List<String>) {
        schoolDymaticPhotoSelectedAdapter.addUris(uris)
    }

    override fun pickImages() {
        val rxPermissions = RxPermissions(activity)
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe({
                    if (it) {
                        Matisse.from(activity)
                                .choose(EnumSet.of(MimeType.JPEG, MimeType.PNG))
                                .theme(R.style.Matisse_Zhihu)
                                .countable(true)
                                .maxSelectable(getImageMaxSize())
                                .imageEngine(GlideEngine())
                                .forResult(SchoolCircleSendActivity.REQUEST_MATISSE)
                    }
                }, {

                })
    }

    override fun onImagePickupOrDelete(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == SchoolCircleSendActivity.REQUEST_MATISSE) {
                val selected = Matisse.obtainResult(data)
                val images = selected.map {
                    MatissePathUtils.getPath(activity, it)
                }.map {
                    Uri.fromFile(File(it)).toString()
                }
                onImagePickup(images)
            }
        }
    }
}