package com.example.flamemathnew.mid.exceptions

open class FieldErrorException : Exception {
    public var i: Int = -1
    public var j: Int = -1

    constructor(_i: Int, _j: Int) {
        i = _i
        j = _j
    }
}