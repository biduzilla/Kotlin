package com.example.helloapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.helloapp.data.Contato

@Database(
    entities = [
        Contato::class
    ],
    version = 1
)
abstract class HelloAppDatabase : RoomDatabase() {
    abstract fun contatoDao(): ContatoDao

    fun getDataBase(context: Context): HelloAppDatabase {
        return Room.databaseBuilder(
            context,
            HelloAppDatabase::class.java,
            "helloApp.db"
        ).build()
    }
}