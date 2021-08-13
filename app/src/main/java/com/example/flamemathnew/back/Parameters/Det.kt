package Parameters

import MRV.Computer
import MRV.Computer.UNKNOWN_METHOD

enum class Det{
    BASIC, SARUSS, LAPLASS, TRIANGLE
}

fun createDetMethod(key : String): Det {
    val arr : Array<Det> = Det.values()
    for (det in arr) if (det.name == key) return det
    throw UNKNOWN_METHOD()
}
