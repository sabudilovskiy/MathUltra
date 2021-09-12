package com.example.flamemathnew.back.parameters

import com.example.flamemathnew.mid.exceptions.UnknownMethodException

enum class Rank {
    ELEMENTAL_ROW, BORDERING
}
fun createRankMethod(key : String): Rank {
    val arr : Array<Rank> = Rank.values()
    for (rank in arr) if (rank.name == key) return rank
    throw UnknownMethodException()
}