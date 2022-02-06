package com.example.flamemathnew.backend.back.Parameters

import com.example.flamemathnew.backend.mid.Computer

enum class Inverse {
    GAUSS, ALGEBRAIC_COMPLEMENT
}

fun createInverseMethod(key: String): Inverse {
    val arr: Array<Inverse> = Inverse.values()
    for (inv in arr) if (inv.name == key) return inv
    throw Computer.UNKNOWN_METHOD()
}