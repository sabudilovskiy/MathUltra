package com.example.flamemathnew.backend.back.Numbers

import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.mid.Computer
import com.example.flamemathnew.backend.back.Support.find_GCD
import com.example.flamemathnew.backend.back.Polynom.Polynom
import com.example.flamemathnew.backend.mid.Settings
import java.lang.Math.abs

class FractionalNumb : Numb {
    var numerator: Long = 1;
    var denominator: Long = 1;

    constructor(value: Long) {
        numerator = value;
    }
    constructor(value: Int) {
        numerator = value.toLong();
    }

    constructor(_numerator: Long, _denominator: Long) {
        val GCD: Long = find_GCD(_numerator, _denominator)
        numerator = _numerator / GCD;
        denominator = _denominator / GCD;
        if (denominator < 0) {
            numerator = -numerator
            denominator = -denominator
        }
    }
    constructor(number: Double) {
        if (number != 0.0) {
            var temp: Double = number
            while (abs(temp - temp.toLong().toDouble()) != 0.0) {
                temp *= 10
                denominator *= 10
            }
            numerator = temp.toLong()
        } else numerator = 0L
    }

    override operator fun compareTo(right: Numb): Int {
        return when (right) {
            is DecNumb -> -right.compareTo(this)
            is FractionalNumb -> {
                val difference: FractionalNumb = (this - right) as FractionalNumb
                when {
                    difference.numerator > 0 -> 1
                    difference.numerator == 0L -> 0
                    else -> -1
                }
            }
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override operator fun plus(right: Ring): Ring {

        return when (right) {
            is FractionalNumb -> {
                val left: FractionalNumb = this
                val GCD: Long = find_GCD(left.denominator, right.denominator)
                val LCM: Long = left.denominator * right.denominator / GCD
                val new_numerator: Long = left.numerator * LCM / denominator + right.numerator * LCM / right.denominator
                FractionalNumb(new_numerator, LCM)
            }
            is Polynom -> right + this
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override operator fun minus(right: Ring): Ring {
        if (right is FractionalNumb) {
            val left: FractionalNumb = this
            val GCD: Long = find_GCD(left.denominator, right.denominator)
            val LCM: Long = left.denominator * right.denominator / GCD
            val new_numerator: Long = left.numerator * LCM / denominator - right.numerator * LCM / right.denominator
            return FractionalNumb(new_numerator, LCM)
        } else if (right is Polynom) {
            return -(right - this)
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override operator fun times(right: Ring): Ring {
        if (right is FractionalNumb) {
            val left: FractionalNumb = this
            var leftNumerator = left.numerator
            var leftDenominator = left.denominator
            var rightNumerator = right.numerator
            var rightDenominator = right.denominator

            var GCD = find_GCD(leftNumerator, rightDenominator)

            if (GCD != 1L) {
                leftNumerator /= GCD
                rightDenominator /= GCD
            }
            GCD = find_GCD(rightNumerator, leftDenominator)
            if (GCD != 1L) {
                leftDenominator /= GCD
                rightNumerator /= GCD
            }

            val newNumerator = leftNumerator * rightNumerator
            val newDenominator = leftDenominator * rightDenominator
            return FractionalNumb(newNumerator, newDenominator)
        } else if (right is Polynom) {
            return right * this
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun div(right: Ring): Ring {
        if (right is FractionalNumb) return this / right
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        else if (other is FractionalNumb) return denominator == other.denominator && numerator == other.numerator
        else if (other is Double) return this == FractionalNumb(other)
        else return false
    }

    override fun unaryMinus(): Ring {
        return FractionalNumb(-numerator, denominator)
    }

    operator fun div(b: FractionalNumb): FractionalNumb {
        val left: FractionalNumb = this
        val right: FractionalNumb = b
        var leftNumerator = left.numerator
        var leftDenominator = left.denominator
        var rightNumerator = right.numerator
        var rightDenominator = right.denominator
        var GCD = find_GCD(leftNumerator, rightDenominator)

        if (GCD != 1L) {
            leftNumerator /= GCD
            rightDenominator /= GCD
        }
        GCD = find_GCD(leftDenominator, rightNumerator)
        if (GCD != 1L) {
            leftDenominator /= GCD
            rightNumerator /= GCD
        }
        val new_numerator = leftNumerator * rightDenominator
        val new_denominator = left.denominator * right.numerator
        return FractionalNumb(new_numerator, new_denominator)
    }

    override fun toString(): String {
        if (!Settings.numbers.use_improrer_fraction) {
            val intValue = numerator / denominator
            val trueNumerator = numerator % denominator
            if (denominator != 1L) return "$intValue $trueNumerator/$denominator"
            else return numerator.toString()
        } else {
            return if (denominator != 1L) "  $numerator/$denominator"
            else numerator.toString()
        }
    }

    fun toDouble(): Double {
        return numerator.toDouble() / denominator.toDouble()
    }
}