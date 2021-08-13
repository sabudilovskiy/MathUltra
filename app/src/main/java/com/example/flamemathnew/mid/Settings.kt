package com.example.flamemathnew.mid

import MRV.Computer

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
            fun setSettings(_second: Parameters.Det, _thirst: Parameters.Det, _fourth: Parameters.Det, _border: Int) {
                second = _second
                thirst = _thirst
                fourth = _fourth
                border = _border
            }
            fun setBorder(_border: Int) {
                border = _border
            }
            @Throws(Computer.MATRIX_DIMENSION_MISSMATCH::class)
            fun get_det_method(n: Int): Parameters.Det {
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
            private var Settings = Parameters.Rank.ELEMENTAL_ROW
            fun setSettings(_settings: Parameters.Rank) {
                Settings = _settings
            }
            fun getSettings(): Parameters.Rank {
                return Settings
            }
        }
        object Inverse{
            private var Settings = Parameters.Inverse.GAUSS
            fun getSettings() : Parameters.Inverse{
                return Settings
            }
            fun setSettings(_settings: Parameters.Inverse){
                Settings = _settings
            }
        }
        object SLE {
            private var Settings = Parameters.SLE.GAUSS
            fun getSettings(): Parameters.SLE {
                return Settings
            }

            fun setSettings(settings: Parameters.SLE) {
                Settings = settings
            }
        }
    }
}

