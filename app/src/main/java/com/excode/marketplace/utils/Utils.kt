package com.excode.marketplace.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.excode.marketplace.R
import com.excode.marketplace.data.remote.response.DefaultResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

private val timeStamp = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

private val fileName = "IMG-$timeStamp-"

fun getString(context: Context, id: Int): String {
    return context.getString(id)
}

fun getMessage(httpException: HttpException): String {
    val body = httpException.response()?.errorBody()
    val adapter = Gson()
        .getAdapter(DefaultResponse::class.java)
    val errorParser = adapter.fromJson(body?.string())
    return errorParser.message
}

fun createCustomTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(fileName, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver = context.contentResolver
    val file = createCustomTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream = FileOutputStream(file)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return file
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int

    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

var dialog: AlertDialog? = null

fun showProgress(context: Context) {
    val view = LayoutInflater.from(context).inflate(R.layout.layout_progress, null)
    dialog = AlertDialog.Builder(context).create()
    dialog?.setView(view)
    dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    dialog?.setCancelable(false)
    dialog?.show()
}

fun hideProgress() {
    dialog?.dismiss()
}


