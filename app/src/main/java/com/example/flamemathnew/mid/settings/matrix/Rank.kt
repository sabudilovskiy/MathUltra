package com.example.flamemathnew.mid.settings.matrix

import com.example.flamemathnew.back.parameters.Rank
import com.example.flamemathnew.back.parameters.createRankMethod

object Rank {
    internal var method = Rank.ELEMENTAL_ROW
    fun setmethod(key: String) {
        method = createRankMethod(key)
    }
}