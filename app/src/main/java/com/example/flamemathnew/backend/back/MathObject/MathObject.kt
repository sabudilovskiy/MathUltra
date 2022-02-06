package com.example.flamemathnew.backend.back.MathObject

import com.example.flamemathnew.backend.back.Logger.Log.commit
import com.example.flamemathnew.backend.back.Logger.Tag
import com.example.flamemathnew.backend.back.Logger.Tag.OBJECT
import com.example.flamemathnew.backend.back.Logger.Tag.SOLUTION

abstract class MathObject {
    public abstract override fun toString(): String

    fun log_this(commit: String) {
        commit(toString(), OBJECT)
        commit(toString(), SOLUTION)
    }

    fun log_this() {
        commit(toString(), OBJECT)
    }

    fun log_this(tag: Tag) {
        commit(toString(), tag)
    }
}
