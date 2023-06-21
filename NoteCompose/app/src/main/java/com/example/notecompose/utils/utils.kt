package com.example.notecompose.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("EEE, d MMM hh:mm aaaa", Locale.getDefault())

    return format.format(date)
}