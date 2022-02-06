package com.example.flamemathnew.backend.mid

import Parameters.*
import Parameters.Rank.ELEMENTAL_ROW
import com.example.flamemathnew.backend.back.Parameters.*
import com.example.flamemathnew.backend.back.Parameters.Det.*
import com.example.flamemathnew.backend.back.Parameters.Inverse.GAUSS
import com.example.flamemathnew.backend.back.Parameters.Number

object Settings {

    object matrix {
        object Det {
            private var second = BASIC
            private var thirst = LAPLASS
            private var fourth = LAPLASS
            private var border = 5

            fun setdefaultSettings() {
                second = BASIC
                thirst = SARUSS
                fourth = LAPLASS
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
                    if (n >= border) return TRIANGLE
                    else {
                        if (n > 3) return LAPLASS
                        else {
                            when (n) {
                                2 -> return second
                                3 -> return thirst
                                else -> return BASIC
                            }
                        }
                    }
                } else {
                    throw Computer.MATRIX_DIMENSION_MISSMATCH()
                }
            }
        }
        object Rank {
            internal var method = ELEMENTAL_ROW
            fun setmethod(key: String) {
                method = createRankMethod(key)
            }
        }
        object Inverse {
            internal var method = GAUSS
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

