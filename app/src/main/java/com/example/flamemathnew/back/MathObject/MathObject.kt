package MathObject.MathObject

import Logger.Log.commit
import Logger.Tag
import Logger.Tag.OBJECT
import Logger.Tag.SOLUTION

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