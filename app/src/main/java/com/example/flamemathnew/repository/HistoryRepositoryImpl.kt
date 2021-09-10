package com.example.flamemathnew.repository

import io.reactivex.Flowable
import io.reactivex.Single

class HistoryRepositoryImpl : HistoryRepository {
    override fun insertLexeme(item: String) {
        TODO("Not yet implemented")
    }

    override fun getAllLexemes(): Flowable<List<String>> {
        TODO("Not yet implemented")
    }

    override fun getLexemeById(id: Long): Single<String> {
        TODO("Not yet implemented")
    }

    override fun deleteLexeme(item: String) {
        TODO("Not yet implemented")
    }
}