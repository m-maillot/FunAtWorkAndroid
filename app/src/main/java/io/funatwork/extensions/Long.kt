package io.funatwork.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.showHumanDateFromSeconds(): String {
    return SimpleDateFormat("d.MM.YYYY", Locale.getDefault()).format(Date(this * 1000))
}