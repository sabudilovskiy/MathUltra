package com.example.flamemathnew.mid

import MRV.Computer
import Parameters.*

object Settings {
    object matrix {
        object Det {
            private var second : Parameters.Det = Parameters.Det.BASIC
            private var thirst : Parameters.Det = Parameters.Det.SARUSS
            private var fourth : Parameters.Det = Parameters.Det.LAPLASS
            private var border = 5
            fun setdefaultSettings() {
                second = Parameters.Det.BASIC
                thirst = Parameters.Det.SARUSS
                fourth = Parameters.Det.LAPLASS
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
            fun get_cur_method(n: Int): Parameters.Det {
                if (n > 0) {
                    if (n >= border) return Parameters.Det.TRIANGLE
                    else {
                        if (n > 3) return Parameters.Det.LAPLASS
                        else {
                            when (n) {
                                2 -> return second
                                3 -> return thirst
                                else -> return Parameters.Det.BASIC
                            }
                        }
                    }
                }
                else {
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
        object Inverse{
            internal var method = Parameters.Inverse.GAUSS
            fun setmethod(key: String){
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
        var type : Parameters.Number = Parameters.Number.PROPER
        var use_improrer_fraction = true;
        fun setType(key : String){
           type = createNumberType(key)
        }
    }
}

