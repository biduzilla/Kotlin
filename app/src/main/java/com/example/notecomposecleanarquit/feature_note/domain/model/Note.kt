package com.example.notecomposecleanarquit.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.notecomposecleanarquit.theme.BabyBlue
import com.example.notecomposecleanarquit.theme.LightGreen
import com.example.notecomposecleanarquit.theme.RedOrange
import com.example.notecomposecleanarquit.theme.RedPink
import com.example.notecomposecleanarquit.theme.Violet

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null

) {
    companion object {
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink,
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)