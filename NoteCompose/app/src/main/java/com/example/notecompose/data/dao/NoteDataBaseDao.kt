package com.example.notecompose.data.dao

import androidx.room.*
import com.example.notecompose.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDataBaseDao {

    @Query("SELECT * from notes_tab")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * from notes_tab where id =:id")
    suspend fun getNoteById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNote(note: Note)

    @Query("DELETE from notes_tab")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteNote(note: Note)
}