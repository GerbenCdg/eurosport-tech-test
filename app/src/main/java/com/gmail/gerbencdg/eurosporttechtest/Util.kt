package com.gmail.gerbencdg.eurosporttechtest

import android.content.Context
import android.os.Build
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToLong

fun formatHumanReadableDate(date: Double, context: Context) : String {

//    val now = Calendar.getInstance().time.time.toDouble()
//    val elapsed = now - date
//
    val dateFormat = SimpleDateFormat.getDateTimeInstance(
        DateFormat.DEFAULT,
        DateFormat.DEFAULT,
        getCurrentLocale(context)
    )
    val javaDate = Date().apply {
        time = date.roundToLong()
    }

    return dateFormat.format(javaDate)
}

fun getCurrentLocale(context: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context.resources.configuration.locales[0]
    } else {
        context.resources.configuration.locale
    }
}