package com.example.notecompose.ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notecompose.model.Note
import com.example.notecompose.repository.NoteRepository
import com.example.notecompose.ui.state.NoteScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

//    private val _uiState: MutableStateFlow<NoteScreenUiState> = MutableStateFlow(
//        NoteScreenUiState()
//    )
//
//    val uiState get() = _uiState.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllNotes().distinctUntilChanged().collect { listNotes ->
                if (listNotes.isEmpty()) {
                    Log.d("NOTEVIEWMODEL", "note list is empty!!!")
                }
                _noteList.value = listNotes
            }
        }

//        _uiState.update { currentState ->
//            currentState.copy(
//                listNotes = _noteList.value,
//                onTitleChange = {
//                    _uiState.value = _uiState.value.copy(
//                        title = it,
//                    )
//                },
//                onDescriptionChange = {
//                    _uiState.value = _uiState.value.copy(
//                        description = it,
//                    )
//                }
//            )
//        }
    }

    fun addNote(note: Note) = viewModelScope.launch { repository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { repository.updateNote(note) }
    fun deleteNote(note: Note) = viewModelScope.launch { repository.deleteNote(note) }

//    fun salvarNota() {
//        _uiState.value.run {
//            val nota = Note(
//                title = title.trim(),
//                description = description.trim(),
//            )
//            viewModelScope.launch { repository.addNote(nota) }
//        }
//    }
//
//    fun removerNota(note: Note) {
//        _uiState.value.run {
//            viewModelScope.launch { repository.deleteNote(note) }
//        }
//    }
}