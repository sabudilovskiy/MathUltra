package com.example.flamemathnew.backend.my

import io.reactivex.Flowable
import io.reactivex.Single

interface LocalDataProvider {
    fun insertLexeme(item : String)
    fun getAllLexemes() : Flowable<List<String>>
    fun getLexemeById(id : Long) : Single<String>
    fun deleteLexeme(item : String)
}