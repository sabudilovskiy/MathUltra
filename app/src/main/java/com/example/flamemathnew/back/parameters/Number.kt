package com.example.flamemathnew.back.parameters

import com.example.flamemathnew.mid.exceptions.UnknownNumberException

enum class Number {
    PROPER, DEC
}
fun createNumberType(key : String): Number {
    val arr : Array<Number> = Number.values()
    for (numb in arr) if (numb.name == key) return numb
    throw UnknownNumberException()
}