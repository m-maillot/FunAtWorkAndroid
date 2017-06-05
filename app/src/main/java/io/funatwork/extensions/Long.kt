package io.funatwork.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.showHumanDateFromSeconds(): String =
        SimpleDateFormat("d.MM.yyyy", Locale.ENGLISH).format(Date(this * 1000))