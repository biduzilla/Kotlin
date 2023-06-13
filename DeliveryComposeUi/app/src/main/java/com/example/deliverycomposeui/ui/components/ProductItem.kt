package com.example.deliverycomposeui.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliverycomposeui.R
import com.example.deliverycomposeui.extension.toBrazilianCurrency
import com.example.deliverycomposeui.model.Product
import com.example.deliverycomposeui.ui.theme.Purple500
import com.example.deliverycomposeui.ui.theme.Teal200
import java.math.BigDecimal

@Composable
fun ProductItem(product: Product) {
    Surface(shape = RoundedCornerShape(15.dp), elevation = 10.dp) {
        Column(
            Modifier
                .heightIn(250.dp, 300.dp)
                .width(200.dp)
        ) {
            val imgSize = 100.dp
            Box(
                modifier = Modifier
                    .height(imgSize)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Purple500,
                                Teal200
                            )
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = "Imagem do produto",
                    Modifier
                        .size(imgSize)
                        .offset(y = imgSize / 2)
                        .clip(CircleShape)
                        .align(Alignment.BottomCenter),
                    contentScale = ContentScale.Crop

                )
            }
            Spacer(modifier = Modifier.height(imgSize / 2))
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = product.price.toBrazilianCurrency(),
                    Modifier.padding(top = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    ProductItem(
        Product(
            LoremIpsum(50).values.first(),
            price = BigDecimal("14.99"),
            image = R.drawable.ic_launcher_background
        )
    )
}