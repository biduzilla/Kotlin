package com.example.notecompose.di

import android.content.Context
import androidx.room.Room
import com.example.notecompose.data.NoteDatabase
import com.example.notecompose.data.dao.NoteDataBaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDao(noteDataBase: NoteDatabase): NoteDataBaseDao = noteDataBase.noteDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): NoteDatabase =
        Room.databaseBuilder(
            context,
            NoteDatabase::
            class.java,
            "notes_db"
        )
            .fallbackToDestructiveMigration()
            .build()
}