package com.agungtriu.gxsales.utils

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.CancellationSignal
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

object PhotoUriManager {
    private const val PHOTOS_DIR = "photos"
    private const val FILE_PROVIDER = "file_provider"
    private fun generateFilename() = "${System.currentTimeMillis()}.jpg"

    fun buildNewUri(appContext: Context): Uri {
        val photosDir = File(appContext.cacheDir, PHOTOS_DIR)
        photosDir.mkdirs()
        val photoFile = File(photosDir, generateFilename())
        val authority = "${appContext.packageName}.$FILE_PROVIDER"
        return FileProvider.getUriForFile(appContext, authority, photoFile)
    }

    fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, "temp_file.jpg")
        file.createNewFile()

        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun getFileSize(context: Context, uri: Uri): String {
        var size: Long = -1

        try {
            val contentResolver: ContentResolver = context.contentResolver

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                size = getSizeForUriQ(contentResolver, uri)
            } else {
                if (!uri.path.isNullOrBlank()) {
                    size = File(uri.path.toString()).length()
                }
            }
        } catch (e: IOException) {
            Log.e("FileSize", "Error getting file size", e)
        }

        return byteToDisplay(size)
    }

    private val listSize = listOf("B", "KB", "MB", "GB", "TB", "PB")
    private const val BYTE = 1024
    fun byteToDisplay(size: Long): String {
        var step = 0
        var temp = size
        while (temp / BYTE > 0) {
            temp /= BYTE
            step++
        }
        return "$temp ${listSize[step]}"
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getSizeForUriQ(contentResolver: ContentResolver, uri: Uri): Long {
        val cursor = contentResolver.query(uri, null, null, null, null, CancellationSignal())
        cursor?.use {
            val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
            if (it.moveToFirst() && sizeIndex != -1) {
                return it.getLong(sizeIndex)
            }
        }
        return -1
    }

    fun getImageName(uri: Uri, context: Context): String? {
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
            cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.let {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                    return it.getString(columnIndex)
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }
}
