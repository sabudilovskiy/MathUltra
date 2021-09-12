package com.example.flamemathnew.mid.settings

import com.example.flamemathnew.back.parameters.Number
import com.example.flamemathnew.back.parameters.createNumberType

object Numbers {
    var type :Number = Number.PROPER
    var use_improrer_fraction = true;
    fun setType(key : String){
        type = createNumberType(key)
    }
}