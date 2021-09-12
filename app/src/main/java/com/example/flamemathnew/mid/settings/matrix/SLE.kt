package com.example.flamemathnew.mid.settings.matrix

import com.example.flamemathnew.back.parameters.createSLEMethod
import com.example.flamemathnew.back.parameters.SLE.GAUSS

object SLE {
        internal var method = GAUSS
        fun setmethod(key: String) {
            method = createSLEMethod(key)
        }
    }
