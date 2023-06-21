package com.example.helloapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloapp.data.Contato

@Composable
fun ContatoItem(
    contato: Contato,
    onClick: (Long) -> Unit
) {
    Card(
        Modifier.clickable { onClick(contato.id) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(Modifier.padding(16.dp)) {
            AsyncImagePerfil(
                urlImagem = contato.fotoPerfil,
                modifier = Modifier
                    .padding(48.dp)
                    .clip(CircleShape)
            )
            Column(
                Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = contato.nome,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = contato.sobrenome
                )
            }
        }
    }
}

@Preview
@Composable
fun ContatoItemPreview() {
    ContatoItem(contato = Contato(
        0L,
        "nome",
        "sobrenome",
        "124587",
    ), onClick = {})
}