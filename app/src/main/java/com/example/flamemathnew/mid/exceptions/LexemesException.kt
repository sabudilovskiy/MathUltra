package com.example.flamemathnew.mid.exceptions

open class LexemesException : Exception {
    var error_begin = -1
        private set
    var error_end = -1
        private set

    constructor() {}
    constructor(begin: Int, end: Int) {
        error_begin = begin
        error_end = end
    }
}