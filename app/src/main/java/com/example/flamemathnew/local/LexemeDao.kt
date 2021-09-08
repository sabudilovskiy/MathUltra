package com.example.flamemathnew.local

import androidx.room.*
import com.example.flamemathnew.local.entity.LexemeEntity
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LexemeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLexeme(item : LexemeEntity)

    @Query("Select * from `lexeme`" )
    fun getAllLexemes() : Flowable<List<LexemeEntity>>

    @Query(value = "Select * from `lexeme` where id = :id " )
    fun getLexemeById(id : Long) : Single<LexemeEntity>

    @Delete()
    fun deleteLexeme(item : LexemeEntity)

}