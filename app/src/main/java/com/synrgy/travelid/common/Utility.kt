package com.synrgy.travelid.common

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.preference.PreferenceManager
import androidx.core.content.edit
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

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

private const val FILENAME_FORMAT = "dd-MMM-yyyy"

private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun uriToFile(
    selectedImage: Uri,
    context: Context,
): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()

    return myFile
}

private fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun formatDate(timestamp: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    val date = sdf.parse(timestamp)
    date?.let {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - date.time
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days >= 1 -> "${days.toInt()} hari yang lalu"
            hours >= 1 -> "${hours.toInt()} jam yang lalu"
            minutes >= 1 -> "${minutes.toInt()} menit yang lalu"
            else -> "Baru saja"
        }
    }
    return ""
}

object BadgePreferencesHelper {
    private const val KEY_BADGE_READ = "badge_read"

    fun isBadgeRead(context: Context, notificationId: Int): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean("$KEY_BADGE_READ$notificationId", false)
    }

    fun markBadgeAsRead(context: Context, notificationId: Int) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit {
            putBoolean("$KEY_BADGE_READ$notificationId", true)
        }
    }
}