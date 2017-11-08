package com.daijie.ksyllabusapp.repository.lcimage

import com.daijie.ksyllabusapp.data.LCFile
import rx.Observable
import java.io.File

/**
 * Created by daidaijie on 17-10-29.
 */
interface LCImageDataSource {

    fun uploadImage(imageFile: File, filename: String? = null): Observable<LCFile>
}