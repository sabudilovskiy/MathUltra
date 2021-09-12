package com.example.flamemathnew.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lexeme")
data class LexemeEntity(
    val expression: String,
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L

    constructor(expression: String, id: Long) : this(expression)
    {
        this.id = id
    }
}