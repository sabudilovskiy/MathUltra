package com.example.flamemathnew.backend.back.Numbers

import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.mid.Computer
import com.example.flamemathnew.backend.back.Matrix.Matrix
import com.example.flamemathnew.backend.back.Polynom.Polynom

class DecNumb : Numb {
    var value: Double = 0.0

    constructor(_value: Double) {
        value = _value
    }
    constructor(_value: Int) {
        value = _value.toDouble()
    }
    constructor(_value: Long) {
        value = _value.toDouble()
    }

    override operator fun compareTo(right: Numb): Int {
        return when (right) {
            is DecNumb -> {
                when {
                    value == right.value -> 0
                    value > right.value -> 1
                    else -> -1
                }
            }
            is FractionalNumb -> {
                FractionalNumb(value).compareTo(right)
            }
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override fun plus(right: Ring): Ring {
        return when (right) {
            is DecNumb -> {
                createNumb(value + right.value)
            }
            is Polynom -> {
                right + this
            }
            else -> {
                throw Computer.NON_COMPLIANCE_TYPES()
            }
        }
    }

    override fun minus(right: Ring): Ring {
        return when (right) {
            is DecNumb -> {
                createNumb(value - right.value)
            }
            is Polynom -> {
                this + (-right)
            }
            else -> {
                throw Computer.NON_COMPLIANCE_TYPES()
            }
        }
    }

    override fun times(right: Ring): Ring {
        return when (right) {
            is DecNumb -> {
                createNumb(value * right.value)
            }
            is Matrix -> {
                right * this
            }
            is Polynom -> {
                right * this
            }
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override fun div(right: Ring): Ring {
        return when (right) {
            is DecNumb -> {
                createNumb(value / right.value)
            }
            is Matrix -> {
                right * this
            }
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override fun equals(other: Any?): Boolean {
        return when {
            other === null -> false
            other is DecNumb -> value == other.value
            other is Double -> value == other
            else -> false
        }
    }

    override fun unaryMinus(): Ring {
        return DecNumb(-value)
    }

    override fun toString(): String {
        return value.toString()
    }
}