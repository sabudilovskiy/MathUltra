package com.example.flamemathnew.repository

import io.reactivex.Flowable
import io.reactivex.Single

interface HistoryRepository {
    fun insertLexeme(item : String)
    fun getAllLexemes() : Flowable<List<String>>
    fun getLexemeById(id : Long) : Single<String>
    fun deleteLexeme(item : String)
}