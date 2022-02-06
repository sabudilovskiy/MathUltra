package Parameters

import com.example.flamemathnew.backend.mid.Computer

enum class SLE {
    KRAMER_RULE, GAUSS
}

fun createSLEMethod(key: String): SLE {
    val arr: Array<SLE> = SLE.values()
    for (sle in arr) if (sle.name == key) return sle
    throw Computer.UNKNOWN_METHOD()
}