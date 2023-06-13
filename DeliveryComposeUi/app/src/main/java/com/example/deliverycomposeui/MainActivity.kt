package com.example.deliverycomposeui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.example.deliverycomposeui.ui.screens.HomeScreen
import com.example.deliverycomposeui.ui.theme.DeliveryComposeUiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    DeliveryComposeUiTheme {
        Surface {
            HomeScreen()
        }
    }
}
