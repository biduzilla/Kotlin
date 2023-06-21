package com.example.notecompose.model

import java.time.LocalDateTime
import java.util.UUID

class Note(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val entryData: LocalDateTime = LocalDateTime.now()
) {
}