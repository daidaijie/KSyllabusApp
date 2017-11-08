package com.daijie.ksyllabusapp.repository.lcimage.remote

import com.daijie.ksyllabusapp.api.LCUploadImageApi
import com.daijie.ksyllabusapp.data.LCFile
import com.daijie.ksyllabusapp.repository.lcimage.LCImageDataSource
import okhttp3.MediaType
import okhttp3.RequestBody
import rx.Observable
import java.io.File

/**
 * Created by daidaijie on 17-10-29.
 */
class LCImageRemoteDataSource(private val lcUploadImageApi: LCUploadImageApi) : LCImageDataSource {

    override fun uploadImage(imageFile: File, filename: String?): Observable<LCFile> {
        val requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile)
        return lcUploadImageApi.uploadImage(requestBody, filename ?: "syllabus_${System.currentTimeMillis()}.jpg")
    }

}