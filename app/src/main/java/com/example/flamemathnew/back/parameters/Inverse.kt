package com.example.flamemathnew.back.parameters

import com.example.flamemathnew.mid.exceptions.UnknownMethodException

enum class Inverse {
    GAUSS, ALGEBRAIC_COMPLEMENT
}
fun createInverseMethod(key : String): Inverse {
    val arr : Array<Inverse> = Inverse.values()
    for (inv in arr) if (inv.name == key) return inv
    throw UnknownMethodException()
}