package com.example.helloapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloapp.R


@Composable
fun DetalhesContatoAppBar(
    onClickVoltar: () -> Unit,
    onClickApagar: () -> Unit,
    onClickEditar: () -> Unit
) {
    TopAppBar {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onClickVoltar }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.voltar),
                    tint = Color.White
                )
            }
            Row {
                IconButton(onClick = { onClickVoltar }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.editar),
                        tint = Color.White
                    )
                }

                IconButton(onClick = { onClickVoltar }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.deletar),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DetalhesContatoAppBarPreview() {
    DetalhesContatoAppBar({}, {}, {})
}
