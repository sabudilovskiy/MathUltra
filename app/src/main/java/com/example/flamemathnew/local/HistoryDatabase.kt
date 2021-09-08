package com.example.flamemathnew.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flamemathnew.local.entity.LexemeEntity

@Database(entities = [LexemeEntity::class ], version = 1,
    exportSchema = false)
abstract class HistoryDatabase : RoomDatabase(){
    abstract fun lexemeDao() : LexemeDao
}