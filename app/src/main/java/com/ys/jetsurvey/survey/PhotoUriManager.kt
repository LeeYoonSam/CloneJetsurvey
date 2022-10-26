package com.ys.jetsurvey.survey

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore

/**
 * 사진 Uri 의 생성을 관리합니다. Uri는 카메라로 찍은 사진을 저장하는 데 사용됩니다.
 */
class PhotoUriManager(private val appContext: Context) {

    private val photoCollection by lazy {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    }

    private val resolver by lazy { appContext.contentResolver }

    fun buildNewUri() = resolver.insert(photoCollection, buildPhotoDetails())

    private fun buildPhotoDetails() = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, generateFilename())
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    /**
     * 사진을 찍은 시간에 따라 고유한 파일 이름 만들기
     */
    private fun generateFilename() = "selfie-${System.currentTimeMillis()}.jpg"
}