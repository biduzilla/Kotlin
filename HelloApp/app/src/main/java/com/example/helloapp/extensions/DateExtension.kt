package com.example.helloapp.extensions

import com.example.helloapp.util.FORMATO_DATA_EXIBIR
import java.text.SimpleDateFormat
import java.util.*

fun Date.converterParaString():String{
    return SimpleDateFormat(
        FORMATO_DATA_EXIBIR,
        Locale.getDefault()
    ).format(this)
}