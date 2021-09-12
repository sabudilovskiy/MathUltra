package com.example.flamemathnew.mid.exceptions

class InputValuesLexemesException() : Exception() {
    var number_value: Int = -1

    constructor(_error: Int) : this() {
        number_value = _error
    }
}