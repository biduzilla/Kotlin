package com.example.notecomposecleanarquit.feature_note.domain.use_case

import com.example.notecomposecleanarquit.feature_note.domain.use_case.AddNote
import com.example.notecomposecleanarquit.feature_note.domain.use_case.DeleteNote
import com.example.notecomposecleanarquit.feature_note.domain.use_case.GetNotes

data class NotesUseCases(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)