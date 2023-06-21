package com.example.notecompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.notecompose.data.converter.DateConverter
import com.example.notecompose.data.converter.UUIDConverter
import com.example.notecompose.data.dao.NoteDataBaseDao
import com.example.notecompose.model.Note

@Database(
    entities = [
        Note::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDataBaseDao
}