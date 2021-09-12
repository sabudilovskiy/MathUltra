package com.example.flamemathnew.back.mathobject

import com.example.flamemathnew.back.logger.Log.commit
import com.example.flamemathnew.back.logger.Tag
import com.example.flamemathnew.back.logger.Tag.OBJECT
import com.example.flamemathnew.back.logger.Tag.SOLUTION

abstract class MathObject {
    public abstract override fun toString(): String
    constructor()
    fun log_this(commit: String) {
        commit(toString(), OBJECT)
        commit(toString(), SOLUTION)
    }
    fun log_this() {
        commit(toString(), OBJECT)
    }
    fun log_this(tag : Tag){
        commit(toString(), tag)
    }
}