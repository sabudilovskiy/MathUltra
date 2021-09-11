package com.example.flamemathnew.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lexeme")
data class LexemeEntity(
    val expression: String,
){
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
}