package com.example.helloapp.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.helloapp.R
import com.example.helloapp.sampleData.contatosExemplo
import com.example.helloapp.ui.components.AppBarListaContatos
import com.example.helloapp.ui.components.ContatoItem
import com.example.helloapp.ui.theme.HelloAppTheme

@Composable
fun ListaContatosScreen(
    state: ListaContatosUiState,
    modifier: Modifier = Modifier,
    onClickDesloga: () -> Unit = {},
    onClickAbreDetalhes: (Long) -> Unit = {},
    onClickAbreCadastro: () -> Unit = {}
) {
    Scaffold(topBar = {
        AppBarListaContatos(onClickDesloga = onClickDesloga)
    },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = MaterialTheme.colors.primary,
                onClick = { onClickAbreCadastro },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_novo_contato)
                )
            }
        }) { paddingValues ->

        LazyColumn(modifier.padding(paddingValues)) {
            items(state.contatos) { contato ->
                ContatoItem(contato = contato){idContato->
                    onClickAbreDetalhes(idContato)
                }
            }
        }
    }
}


@Preview
@Composable
fun ListaContatosPreview() {
    HelloAppTheme {
        ListaContatosScreen(state = ListaContatosUiState(contatosExemplo))
    }
}