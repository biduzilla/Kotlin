package com.example.notecomposecleanarquit.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notecomposecleanarquit.feature_note.domain.model.Note
import com.example.notecomposecleanarquit.feature_note.domain.use_case.NotesUseCases
import com.example.notecomposecleanarquit.feature_note.domain.util.NoteOrder
import com.example.notecomposecleanarquit.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private val _state = mutableStateOf<NoteState>(NoteState())
    val state: State<NoteState> = _state

    private var getNotesJob: Job? = null
    private var recentlyNoteDeleted: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType
                ) {
                    return
                }

                getNotes(event.noteOrder)
            }
            is NotesEvent.Delete -> {
                viewModelScope.launch {
                    notesUseCases.deleteNote(event.note)
                    recentlyNoteDeleted = event.note
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(recentlyNoteDeleted ?: return@launch)
                    recentlyNoteDeleted = null
                }
            }
            is NotesEvent.ToggleOrderSection -> {
                _state.value = _state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(noteOrder).onEach { notes ->
            _state.value = state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }
}