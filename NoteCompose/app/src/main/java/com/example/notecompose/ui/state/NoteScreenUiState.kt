package com.example.notecompose.ui.state

import com.example.notecompose.model.Note

data class NoteScreenUiState(
    val title: String = "",
    val description: String = "",
    val listNotes: List<Note> = emptyList(),
)
