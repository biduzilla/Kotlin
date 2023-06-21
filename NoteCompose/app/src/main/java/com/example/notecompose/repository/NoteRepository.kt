package com.example.notecompose.repository

import com.example.notecompose.data.NoteDatabase
import com.example.notecompose.data.dao.NoteDataBaseDao
import com.example.notecompose.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDatabaseDao: NoteDataBaseDao) {
    suspend fun addNote(note: Note) = noteDatabaseDao.insertNote(note)
    suspend fun updateNote(note: Note) = noteDatabaseDao.updateNote(note)
    suspend fun deleteNote(note: Note) = noteDatabaseDao.deleteNote(note)
    suspend fun deleteAllNotes() = noteDatabaseDao.deleteAll()
    fun getAllNotes(): Flow<List<Note>> = noteDatabaseDao.getAll().flowOn(Dispatchers.IO).conflate()
}