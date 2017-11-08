package com.daijie.ksyllabusapp.ui.dymatic.circle.send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.daijie.ksyllabusapp.App
import com.daijie.ksyllabusapp.R
import com.daijie.ksyllabusapp.adapter.SchoolDymaticPhotoSelectedAdapter
import com.daijie.ksyllabusapp.base.BaseActivity
import com.daijie.ksyllabusapp.base.LoadingView
import com.daijie.ksyllabusapp.base.LoadingViewImpl
import com.daijie.ksyllabusapp.data.LCFile
import com.daijie.ksyllabusapp.ext.dp2px
import com.daijie.ksyllabusapp.ext.toastE
import com.daijie.ksyllabusapp.ext.toastS
import com.daijie.ksyllabusapp.littlevp.selectimg.SelectImageView
import com.daijie.ksyllabusapp.littlevp.selectimg.SelectImageViewImpl
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImageContract
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImageModule
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImagePresenter
import com.daijie.ksyllabusapp.littlevp.uploadimage.UploadImageViewProxy
import com.daijie.ksyllabusapp.ui.photo.PhotoPagerActivity
import com.daijie.ksyllabusapp.ui.photo.PhotoPagerDeleteActivity
import com.daijie.ksyllabusapp.utils.DefaultGson
import com.library.flowlayout.FlowLayoutManager
import com.library.flowlayout.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_school_circle_send.*
import kotlinx.android.synthetic.main.view_toolbar.*
import javax.inject.Inject

/**
 * Created by daidaijie on 17-10-28.
 */
class SchoolCircleSendActivity : BaseActivity(), SchoolCircleSendContract.View, SelectImageView {

    companion object {
        const val REQUEST_MATISSE = 200

        @JvmStatic
        fun newIntent(context: Context) = Intent(context, SchoolCircleSendActivity::class.java)
    }

    override val contentViewId = R.layout.activity_school_circle_send

    private val schoolDymaticPhotoSelectedAdapter = SchoolDymaticPhotoSelectedAdapter()

    private val uploadImageView: UploadImageContract.View by lazy {
        UploadImageViewProxy(this)
    }

    private val selectImageView: SelectImageView by lazy {
        SelectImageViewImpl(this, schoolDymaticPhotoSelectedAdapter)
    }

    private val loadingView: LoadingView by lazy {
        LoadingViewImpl(this)
    }

    override var titleToolbar: Toolbar? = null
        get() = appToolbar
    override var titleText: TextView? = null
        get() = titleTextView

    @Inject
    lateinit var schoolCircleSendPresenter: SchoolCircleSendPresenter

    @Inject
    lateinit var uploadImagePresenter: UploadImagePresenter

    override fun initView(savedInstanceState: Bundle?) {
        setTitle("发布动态")

        appToolbar.inflateMenu(R.menu.menu_send)
        appToolbar.setOnMenuItemClickListener {
            toastS("发送")
            sendImage()
            true
        }

        photoRecyclerView.layoutManager = FlowLayoutManager()
        photoRecyclerView.addItemDecoration(SpaceItemDecoration(dp2px(8f)))
        photoRecyclerView.adapter = schoolDymaticPhotoSelectedAdapter
        schoolDymaticPhotoSelectedAdapter.setOnItemClickListener { adapter, view, position ->
            val uri = schoolDymaticPhotoSelectedAdapter.data[position]
            if (uri.isNotEmpty()) {
                startActivity(PhotoPagerActivity.newIntent<PhotoPagerDeleteActivity>(this,
                        schoolDymaticPhotoSelectedAdapter.realData.toTypedArray(), position))
            } else {
                pickImages()
            }
        }

        initInject()
    }

    private fun initInject() {
        DaggerSchoolCircleSendComponent.builder()
                .userComponent(App.userComponent)
                .uploadImageModule(UploadImageModule(uploadImageView))
                .schoolCircleSendModule(SchoolCircleSendModule(this))
                .build().inject(this)
    }

    override fun onDestroy() {
        uploadImagePresenter.unsubscribe()
        schoolCircleSendPresenter.unsubscribe()
        super.onDestroy()
    }

    override fun showSuccess(msg: String) {
        when (msg) {
            SchoolCircleSendContract.MESSAGE_SEND_CIRCLE_SUCCESS -> {
                toastS(msg)
                finish()
            }
        }
    }

    override fun showError(msg: String) {
        toastE(msg)
    }

    override fun onSuccess(lcFiles: List<LCFile>) {
        schoolCircleSendPresenter.postCircle(
                schoolDymaticEditText.text.toString(), DefaultGson.gson.toJson(
                hashMapOf(
                        "photo_list" to lcFiles.map {
                            return@map hashMapOf("size_big" to it.url, "size_small" to it.url)
                        }
                )
        ), "iphoneX"
        )
    }

    override fun pickImages() {
        selectImageView.pickImages()
    }

    override fun onImagePickupOrDelete(requestCode: Int, resultCode: Int, data: Intent?) {
        selectImageView.onImagePickupOrDelete(requestCode, resultCode, data)
    }

    override fun showLoadingDialog(msg: String?) {
        loadingView.showLoadingDialog(msg)
    }

    override fun hideLoadingDialog() {
        loadingView.hideLoadingDialog()
    }

    private fun sendImage() {
        uploadImagePresenter.uploadImages(schoolDymaticPhotoSelectedAdapter.realData)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onImagePickupOrDelete(requestCode, resultCode, data)
    }
}