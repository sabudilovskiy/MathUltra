package com.example.flamemathnew.repository

import com.example.flamemathnew.backend.my.LocalDataProvider
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataProvider: LocalDataProvider
) : HistoryRepository {

    override fun insertLexeme(item: String) {
        localDataProvider.insertLexeme(item)
    }
    override fun getAllLexemes(): Flowable<List<String>> =
        localDataProvider.getAllLexemes()


    override fun getLexemeById(id: Long): Single<String> =
        localDataProvider.getLexemeById(id)


    override fun deleteLexeme(item: String) {
        localDataProvider.deleteLexeme(item)
    }
}