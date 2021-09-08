package com.example.flamemathnew.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lexeme")
data class LexemeEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val expression: String,
)