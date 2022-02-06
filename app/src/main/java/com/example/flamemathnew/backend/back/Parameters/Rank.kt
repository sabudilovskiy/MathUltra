package Parameters

import com.example.flamemathnew.backend.mid.Computer

enum class Rank {
    ELEMENTAL_ROW, BORDERING
}
fun createRankMethod(key : String): Rank {
    val arr : Array<Rank> = Rank.values()
    for (rank in arr) if (rank.name == key) return rank
    throw Computer.UNKNOWN_METHOD()
}