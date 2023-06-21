package com.example.notecompose.ui.screens

import android.content.res.Resources.Theme
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notecompose.ui.components.InputText
import com.example.notecompose.data.NoteDataSource
import com.example.notecompose.model.Note
import com.example.notecompose.ui.components.ButtonCompose
import com.example.notecompose.ui.components.NoteItem

@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(Modifier.padding(6.dp)) {
        TopAppBar(
            title = {
                Text(text = "Lucky Notas")
            },
            actions = {
                Icon(
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = null,
                    Modifier.padding(horizontal = 8.dp)
                )
            }, backgroundColor = MaterialTheme.colors.primary
        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputText(text = title, label = "Título", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) title = it.trim()
            })
            InputText(text = description, label = "Descrição", onTextChange = {
                if (it.all { char ->
                        char.isLetter() || char.isWhitespace()
                    }) description = it.trim()
            })

            ButtonCompose(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Salvar",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        onAddNote(
                            Note(
                                title = title,
                                description = description,
                            )
                        )
                        title = ""
                        description = ""

                        Toast.makeText(context, "Note Add", Toast.LENGTH_SHORT).show()
                    }
                })

            Divider(Modifier.padding(10.dp))
            LazyColumn {
                items(notes) {
                    NoteItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        note = it,
                        onClicked = { note ->
                            onRemoveNote(note)
                        })
                }
            }
        }
    }
}

@Preview
@Composable
fun NoteScreenPreview() {
    NoteScreen(
        notes = NoteDataSource().loadNotes(),
        onAddNote = {},
        onRemoveNote = {},
    )
}