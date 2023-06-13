package com.example.deliverycomposeui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.fonts.Font
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliverycomposeui.ui.theme.DeliveryComposeUiTheme
import com.example.deliverycomposeui.ui.theme.Purple200
import com.example.deliverycomposeui.ui.theme.Purple500
import com.example.deliverycomposeui.ui.theme.Teal200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeliveryComposeUiTheme {
                Surface {
                    ProductSection()
                }
            }

        }
    }
}

@Composable
fun ProductItem() {
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
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Imagem do produto",
                    Modifier
                        .size(imgSize)
                        .offset(y = imgSize / 2)
                        .clip(CircleShape)
                        .align(BottomCenter)
                )
            }
            Spacer(modifier = Modifier.height(imgSize / 2))
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = LoremIpsum(50).values.first(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "R$ 15.90",
                    Modifier.padding(top = 8.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400)
                )
            }
        }

    }
}

@Composable
fun ProductSection() {
    Column {
        Text(
            text = "Promoções",
            Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight(400)
        )
        Row(
            Modifier
                .padding(top = 16.dp, bottom = 16.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
            , horizontalArrangement =Arrangement.spacedBy(16.dp)) {
            Spacer(modifier = Modifier)
            ProductItem()
            ProductItem()
            ProductItem()
            Spacer(modifier = Modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductSectionPreview() {
    ProductSection()
}

@Preview(showBackground = true)
@Composable
private fun ProductItemPreview() {
    ProductItem()
}