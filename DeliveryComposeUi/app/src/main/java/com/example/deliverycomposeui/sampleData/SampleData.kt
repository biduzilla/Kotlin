package com.example.deliverycomposeui.sampleData

import com.example.deliverycomposeui.R
import com.example.deliverycomposeui.model.Product
import java.math.BigDecimal

val sampleProduct = listOf(
    Product(
        "Book 1",
        price = BigDecimal("14.99"),
        image = R.drawable.book1
    ),
    Product(
        "Book 2",
        price = BigDecimal("14.99"),
        image = R.drawable.book2
    ),
    Product(
        "Book 3",
        price = BigDecimal("14.99"),
        image = R.drawable.book3

    )
)