package com.example.deliverycomposeui.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.deliverycomposeui.R
import com.example.deliverycomposeui.model.Product
import com.example.deliverycomposeui.ui.components.ProductSection
import com.example.deliverycomposeui.ui.components.sampleProduct
import java.math.BigDecimal

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier)
        ProductSection("books 1", sampleProduct)
        ProductSection(
            "books 2", listOf(
                Product(
                    "Book 1",
                    price = BigDecimal("14.99"),
                    image = R.drawable.book1
                ),
            )
        )
        ProductSection("books 3", sampleProduct)
        Spacer(modifier = Modifier)
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}