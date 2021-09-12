package com.example.flamemathnew.mid.exceptions

class InputKeysLexemesException() : Exception() {
    var number_key: Int = -1

    constructor(_error: Int) : this() {
        number_key = _error
    }
}