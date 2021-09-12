package com.example.flamemathnew.mid.settings.matrix

import com.example.flamemathnew.back.parameters.createDetMethod
import com.example.flamemathnew.mid.exceptions.MatrixDimensionMismatchException

object Det {
    private var second : com.example.flamemathnew.back.parameters.Det =
        com.example.flamemathnew.back.parameters.Det.BASIC
    private var thirst : com.example.flamemathnew.back.parameters.Det =
        com.example.flamemathnew.back.parameters.Det.LAPLASS
    private var fourth : com.example.flamemathnew.back.parameters.Det =
        com.example.flamemathnew.back.parameters.Det.LAPLASS
    private var border = 5
    fun setdefaultSettings() {
        second = com.example.flamemathnew.back.parameters.Det.BASIC
        thirst = com.example.flamemathnew.back.parameters.Det.SARUSS
        fourth = com.example.flamemathnew.back.parameters.Det.LAPLASS
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
    fun getCurMethod(n: Int): com.example.flamemathnew.back.parameters.Det {
        if (n > 0) {
            if (n >= border) return com.example.flamemathnew.back.parameters.Det.TRIANGLE
            else {
                if (n > 3) return com.example.flamemathnew.back.parameters.Det.LAPLASS
                else {
                    when (n) {
                        2 -> return second
                        3 -> return thirst
                        else -> return com.example.flamemathnew.back.parameters.Det.BASIC
                    }
                }
            }
        }
        else {
            throw MatrixDimensionMismatchException()
        }
    }
}