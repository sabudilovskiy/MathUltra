package com.example.flamemathnew.backend.mid

import Parameters.*
import com.example.flamemathnew.backend.back.Parameters.Number
import com.example.flamemathnew.backend.back.Parameters.createDetMethod
import com.example.flamemathnew.backend.back.Parameters.createInverseMethod
import com.example.flamemathnew.backend.back.Parameters.createNumberType

object Settings {

    object matrix {
        object Det {
            private var second: com.example.flamemathnew.backend.back.Parameters.Det = com.example.flamemathnew.backend.back.Parameters.Det.BASIC
            private var thirst: com.example.flamemathnew.backend.back.Parameters.Det = com.example.flamemathnew.backend.back.Parameters.Det.LAPLASS
            private var fourth: com.example.flamemathnew.backend.back.Parameters.Det = com.example.flamemathnew.backend.back.Parameters.Det.LAPLASS
            private var border = 5

            fun setdefaultSettings() {
                second = com.example.flamemathnew.backend.back.Parameters.Det.BASIC
                thirst = com.example.flamemathnew.backend.back.Parameters.Det.SARUSS
                fourth = com.example.flamemathnew.backend.back.Parameters.Det.LAPLASS
            }

            fun setSettings(_second: String, _thirst: String, _fourth: String, _border: Int) {
                second = createDetMethod(_second)
                thirst = createDetMethod(_thirst)
                fourth = createDetMethod(_fourth)
                border = _border
            }

            fun setBorder(_border: Int) {
                border = _border
            }

            fun getCurMethod(n: Int): com.example.flamemathnew.backend.back.Parameters.Det {
                if (n > 0) {
                    if (n >= border) return com.example.flamemathnew.backend.back.Parameters.Det.TRIANGLE
                    else {
                        if (n > 3) return com.example.flamemathnew.backend.back.Parameters.Det.LAPLASS
                        else {
                            when (n) {
                                2 -> return second
                                3 -> return thirst
                                else -> return com.example.flamemathnew.backend.back.Parameters.Det.BASIC
                            }
                        }
                    }
                } else {
                    throw Computer.MATRIX_DIMENSION_MISSMATCH()
                }
            }
        }
        object Rank {
            internal var method = Parameters.Rank.ELEMENTAL_ROW
            fun setmethod(key: String) {
                method = createRankMethod(key)
            }
        }
        object Inverse {
            internal var method = com.example.flamemathnew.backend.back.Parameters.Inverse.GAUSS
            fun setmethod(key: String) {
                method = createInverseMethod(key)
            }
        }
        object SLE {
            internal var method = Parameters.SLE.GAUSS
            fun setmethod(key: String) {
                method = createSLEMethod(key)
            }
        }
    }
    object numbers {
        var type: Number = Number.PROPER
        var use_improrer_fraction = true;
        fun setType(key: String) {
            type = createNumberType(key)
        }
    }
}

