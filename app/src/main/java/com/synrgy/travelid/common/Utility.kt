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
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

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

fun formatDateOrderHistory(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))

    try {
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date!!)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return inputDate
}

fun formatPriceOrderHistory(totalPrice: Int): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    if (currencyFormat is DecimalFormat) {
        currencyFormat.minimumFractionDigits = 0
        currencyFormat.maximumFractionDigits = 0
    }

    return currencyFormat.format(totalPrice)
}

fun formatDateTimeDetail(inputDateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy - HH.mm", Locale("id", "ID"))

    try {
        val date = inputFormat.parse(inputDateTime)
        return outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return inputDateTime
}

fun formatDateArticle(timestamp: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    sdf.timeZone = TimeZone.getTimeZone("UTC")
    val date = sdf.parse(timestamp)

    date?.let {
        val currentTime = System.currentTimeMillis()
        val timeDifference = currentTime - date.time
        val seconds = timeDifference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return when {
            days >= 7 -> {
                val week = days / 7
                "${week.toInt()} minggu yang lalu"
            }
            days >= 1 -> "${days.toInt()} hari yang lalu"
            hours >= 1 -> "${hours.toInt()} jam yang lalu"
            minutes >= 1 -> "${minutes.toInt()} menit yang lalu"
            else -> "Baru saja"
        }
    }
    return ""
}

fun formatDateListFlight(inputDate: String): String {
    val inputFormatter = SimpleDateFormat("dd MMM yyyy", Locale("id")) // Specify the locale for Indonesian language
    val outputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

    val date = inputFormatter.parse(inputDate)
    return outputFormatter.format(date)
}

fun formatDateListFlightEndDate(inputDate: String): String {
    val inputFormatter = SimpleDateFormat("dd MMM yyyy", Locale("id")) // Specify the locale for Indonesian language
    val outputFormatter = SimpleDateFormat("yyyy-MM-dd'T'23:59:59")

    val date = inputFormatter.parse(inputDate)

    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)

    return outputFormatter.format(calendar.time)
}