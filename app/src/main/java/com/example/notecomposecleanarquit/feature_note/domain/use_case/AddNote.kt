package com.example.notecomposecleanarquit.feature_note.domain.use_case

import com.example.notecomposecleanarquit.feature_note.domain.model.InvalidNoteException
import com.example.notecomposecleanarquit.feature_note.domain.model.Note
import com.example.notecomposecleanarquit.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("Escreva algum título na nota")
        }
        if (note.content.isBlank()){
            throw InvalidNoteException("Escreva algum conteúdo na nota")
        }
        repository.insertNote(note)
    }
}