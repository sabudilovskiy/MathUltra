package com.example.flamemathnew.back.lexemes

class Lexeme {
    var key: String = ""
        protected set
    @JvmField protected var id: IdLexemes = IdLexemes.NULL
    @JvmField protected var values = mutableListOf<Double>()
    var begin = 0
    var end = 0

    constructor() {
        values.add(3.0)
    }

    constructor(id: IdLexemes, values: MutableList<Double>) {
        this.values = values
        this.id = id
        key = code()
    }

    constructor(id: IdLexemes) {
        this.id = id
        key = code()
    }

    constructor(key: String) {
        id = IdLexemes.VARIABLE
        this.key = key
    }

    fun getId(): IdLexemes {
        return id
    }

    fun getValue(i: Int): Double {
        return values[i]
    }

    fun getValue(): Double {
        return values[0]
    }

    fun getValues(): MutableList<Double> {
        return values
    }

    private fun code(): String {
        var A = ""
        if (id === IdLexemes.ARGUMENT) {
            if (values.size > 1) {
                A += "("
                A += values[0]
                for (i in 1 until values.size) {
                    A += ","
                    A += java.lang.Double.toString(values[i])
                }
                A += "("
            } else {
                A += java.lang.Double.toString(values[0])
            }
        } else if (id === IdLexemes.LEFT_BR) A += "(" else if (id === IdLexemes.RIGHT_BR) A += ")" else if (id === IdLexemes.VARIABLE) A += "x" else {
            A = Archieve.code(id)
        }
        return A
    }
}