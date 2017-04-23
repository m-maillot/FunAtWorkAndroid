package io.funatwork.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.showHumanDateFromSeconds(): String {
    return SimpleDateFormat("d MMM HH:mm:ss", Locale.getDefault()).format(Date(this * 1000))
}