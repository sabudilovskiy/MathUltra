package com.example.flamemathnew.mid.settings.matrix

import com.example.flamemathnew.back.parameters.Inverse
import com.example.flamemathnew.back.parameters.createInverseMethod

object Inverse{
    internal var method = Inverse.GAUSS
    fun setmethod(key: String){
        method = createInverseMethod(key)
    }
}