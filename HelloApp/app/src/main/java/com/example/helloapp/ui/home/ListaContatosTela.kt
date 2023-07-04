package com.example.helloapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.helloapp.R
import com.example.helloapp.data.Contato
import com.example.helloapp.sampleData.contatosExemplo
import com.example.helloapp.ui.components.AsyncImagePerfil
import com.example.helloapp.ui.theme.HelloAppTheme

@Composable
fun ListaContatosTela(
    state: ListaContatosUiState,
    modifier: Modifier = Modifier,
    onClickDesloga: () -> Unit = {},
    onClickAbreCadastro: () -> Unit = {},
    onClickAbreDetalhes: (Long) -> Unit = {}
) {
    Scaffold(
        topBar = {
            AppBarListContatos(onClickDesloga)
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = onClickAbreCadastro,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier.padding(paddingValues)) {
            items(state.contatos) { contato ->
                ContatoItem(contato = contato) { idContato ->
                    onClickAbreDetalhes(idContato)
                }
            }
        }
    }
}

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
                urlImagem = contato.fotoPerfil, modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Column(
                Modifier
                    .padding(start = 8.dp)
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

@Composable
fun AppBarListContatos(onClickDesloga: () -> Unit) {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.nome_do_app))
    }, actions = {
        IconButton(onClick = onClickDesloga) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                tint = Color.White
            )

        }
    })
}

@Preview
@Composable
fun ListaContatosTelaPreview() {
    HelloAppTheme {
        ListaContatosTela(state = ListaContatosUiState(contatosExemplo))
    }
}