package Parameters

import MRV.Computer

enum class Inverse {
    GAUSS, ALGEBRAIC_COMPLEMENT
}
fun createInverseMethod(key : String): Inverse {
    val arr : Array<Inverse> = Inverse.values()
    for (inv in arr) if (inv.name == key) return inv
    throw Computer.UNKNOWN_METHOD()
}