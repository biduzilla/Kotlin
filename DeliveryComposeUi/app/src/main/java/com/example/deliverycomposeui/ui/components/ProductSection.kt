package com.example.deliverycomposeui.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliverycomposeui.R
import com.example.deliverycomposeui.model.Product
import com.example.deliverycomposeui.sampleData.sampleProduct
import java.math.BigDecimal

@Composable
fun ProductSection(title: String, products: List<Product>) {
    Column {
        Text(
            text = title,
            Modifier.padding(start = 16.dp, end = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(400)
        )
        Row(
            Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier)
            for (p in products) {
                ProductItem(product = p)
            }
            Spacer(modifier = Modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductSectionPreview() {
    ProductSection("books", sampleProduct)
}