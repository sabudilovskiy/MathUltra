package Parameters

import MRV.Computer

enum class Number {
    PROPER, DEC
}
fun createNumberType(key : String): Number {
    val arr : Array<Number> = Number.values()
    for (numb in arr) if (numb.name == key) return numb
    throw Computer.UNKNOWN_NUMBER()
}