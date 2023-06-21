package com.example.notecompose.ui.state

import com.example.notecompose.model.Note

data class NoteScreenUiState(
    var title: String = "",
    var description: String = "",
    val listNotes: List<Note> = emptyList(),
    val onTitleChange:(String)-> Unit = {},
    val onDescriptionChange:(String)-> Unit = {},
)
