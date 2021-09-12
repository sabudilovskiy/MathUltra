package com.example.flamemathnew.mid.exceptions

open class FieldErrorSleException(_i: Int, _j: Int, _is_right: Boolean) : FieldErrorException(_i, _j) {
    public var is_right: Boolean = _is_right
}