package com.example.quizcompose.data

data class DataOrException<T, Boolean, E : Exception>(
    var data: T? = null,
    val loading: Boolean? = null,
    var e: E? = null
)
