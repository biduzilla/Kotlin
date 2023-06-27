package com.example.helloapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.helloapp.R

@Composable
fun AsyncImagePerfil(
    urlImagem: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        contentDescription = stringResource(id = R.string.foto_perfil_contato),
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current).data(urlImagem).build(),
        placeholder = painterResource(id = R.drawable.placeholder),
        error = painterResource(id = R.drawable.placeholder),
        contentScale = ContentScale.Crop
    )
}