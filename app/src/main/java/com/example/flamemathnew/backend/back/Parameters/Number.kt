package com.example.flamemathnew.backend.back.Parameters

import com.example.flamemathnew.backend.mid.Computer

enum class Number {
    PROPER, DEC
}

fun createNumberType(key: String): Number {
    val arr: Array<Number> = Number.values()
    for (numb in arr) if (numb.name == key) return numb
    throw Computer.UNKNOWN_NUMBER()
}