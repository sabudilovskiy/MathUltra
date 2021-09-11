package com.example.flamemathnew.backend.my

import com.example.flamemathnew.local.LexemeDao
import com.example.flamemathnew.local.entity.LexemeEntity
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RoomDataProvider @Inject constructor(private val dao: LexemeDao) : LocalDataProvider {
    override fun insertLexeme(item: String) =
        dao.insertLexeme(
            LexemeEntity(item)
        )


    override fun getAllLexemes(): Flowable<List<String>> =
        dao.getAllLexemes()
            .map { lexemes ->
                lexemes.map { item ->
                    item.expression
                }
            }

    override fun getLexemeById(id: Long): Single<String> =
        dao.getLexemeById(id)
            .map { lexeme ->
                lexeme.expression
            }

    override fun deleteLexeme(item: String) {
        dao.deleteLexeme(LexemeEntity(item))
    }
}
