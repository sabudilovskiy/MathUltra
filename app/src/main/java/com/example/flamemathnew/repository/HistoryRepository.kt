package com.example.flamemathnew.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flamemathnew.local.entity.LexemeEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface HistoryRepository {
    fun insertLexeme(item : String)
    fun getAllLexemes() : Flowable<List<String>>
    fun getLexemeById(id : Long) : Single<String>
    fun deleteLexeme(item : String)
}