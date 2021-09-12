package com.example.flamemathnew.back.parameters

import com.example.flamemathnew.mid.exceptions.UnknownMethodException

enum class SLE {
    KRAMER_RULE, GAUSS
}
fun createSLEMethod(key : String): SLE {
    val arr : Array<SLE> = SLE.values()
    for (sle in arr) if (sle.name == key) return sle
    throw UnknownMethodException()
}