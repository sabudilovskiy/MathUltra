package com.example.flamemathnew.back.parameters

import com.example.flamemathnew.mid.exceptions.UnknownMethodException


enum class Det{
    BASIC, SARUSS, LAPLASS, TRIANGLE
}

fun createDetMethod(key : String): Det {
    val arr : Array<Det> = Det.values()
    for (det in arr) if (det.name == key) return det
    throw UnknownMethodException()
}
