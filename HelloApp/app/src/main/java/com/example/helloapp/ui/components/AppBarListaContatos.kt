package com.example.helloapp.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloapp.R

@Composable
fun AppBarListaContatos(onClickDesloga: () -> Unit) {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.app_name))
    },
        actions = {
            IconButton(onClick = onClickDesloga) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    tint = Color.White,
                    contentDescription = stringResource(id = R.string.deslogar)
                )

            }
        })
}

@Preview
@Composable
fun AppBarListaContatosPreview() {
    AppBarListaContatos({})
}