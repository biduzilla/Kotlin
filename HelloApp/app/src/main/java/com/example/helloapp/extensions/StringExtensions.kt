package com.example.helloapp.extensions

import com.example.helloapp.utils.FORMATO_DATA_DIA_MES_ANO
import java.text.SimpleDateFormat
import java.util.*

fun String.converterParaDate(): Date? {
    return SimpleDateFormat(
        FORMATO_DATA_DIA_MES_ANO,
        Locale.getDefault()
    ).parse(this)
}