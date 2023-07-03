package com.example.helloapp.database

import androidx.room.Insert
import androidx.room.Query
import com.example.helloapp.data.Contato

interface ContatoDao {

    @Insert
    fun insert(contato:Contato)

    @Query("SELECT * FROM Contato")
    fun buscaTodos():List<Contato>
}