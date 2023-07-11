package com.example.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.example.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.example.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.example.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.example.cleanarchitecturenoteapp.feature_note.domain.use_case.DeleteNote
import com.example.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNotes
import com.example.cleanarchitecturenoteapp.feature_note.domain.use_case.NotesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCase(repository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository)
        )
    }
}