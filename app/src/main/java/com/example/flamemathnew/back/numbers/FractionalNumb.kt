package com.example.flamemathnew.back.numbers

import com.example.flamemathnew.back.polynom.Polynom
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.support.Support
import com.example.flamemathnew.mid.settings.Numbers.use_improrer_fraction
import com.example.flamemathnew.mid.exceptions.NonComplianceTypesException
import java.lang.Math.abs


class FractionalNumb : Numb {
    var numerator : Long = 1;
    var denominator : Long = 1;
    constructor(value : Long){
        numerator = value;
    }
    constructor(value : Int){
        numerator = value.toLong();
    }
    constructor(_numerator : Long, _denominator : Long){
        val GCD : Long = Support.findGCD(_numerator, _denominator)
        numerator = _numerator/GCD;
        denominator = _denominator/GCD;
        if (denominator < 0) {
            numerator = -numerator
            denominator = -denominator
        }
    }
    constructor(number : Double){
        if (number != 0.0)
        {
            var temp : Double = number
            while (abs(temp - temp.toLong().toDouble()) != 0.0) {
                temp*=10
                denominator*=10
            }
            numerator = temp.toLong()
        }
        else numerator = 0L

    }

    override operator fun compareTo(right: Numb) : Int{
        if (right is DecNumb) return -right.compareTo(this)
        else if (right is FractionalNumb) {
            val difference : FractionalNumb = (this - right) as FractionalNumb
            if (difference.numerator > 0) return 1
            else if (difference.numerator == 0L) return 0
            else return -1
        }
        else throw NonComplianceTypesException()
    }

    override operator fun plus(right : Ring) : Ring {
        if (right is FractionalNumb) {
            val left: FractionalNumb = this
            val GCD: Long = Support.findGCD(left.denominator, right.denominator)
            val LCM: Long = left.denominator * right.denominator / GCD
            val new_numerator: Long = left.numerator * LCM / denominator + right.numerator * LCM / right.denominator
            return FractionalNumb(new_numerator, LCM)
        }
        else if (right is Polynom) return right + this
        else throw NonComplianceTypesException()
    }
    override operator fun minus(right : Ring) : Ring {
        if (right is FractionalNumb){
            val left: FractionalNumb = this
            val GCD: Long = Support.findGCD(left.denominator, right.denominator)
            val LCM: Long = left.denominator * right.denominator / GCD
            val new_numerator: Long = left.numerator * LCM / denominator - right.numerator * LCM / right.denominator
            return FractionalNumb(new_numerator, LCM)
        }
        else if (right is Polynom){
            return -(right - this)
        }
        else throw NonComplianceTypesException()
    }
    override operator fun times(right : Ring) : Ring {
        if (right is FractionalNumb){
            val left : FractionalNumb = this
            var left_numerator = left.numerator
            var left_denominator = left.denominator
            var right_numerator = right.numerator
            var right_denominator = right.denominator
            var GCD = Support.findGCD(left_numerator, right_denominator)
            if (GCD!=1L){
                left_numerator/=GCD
                right_denominator/=GCD
            }
            GCD = Support.findGCD(right_numerator, left_denominator)
            if (GCD!=1L){
                left_denominator/=GCD
                right_numerator/=GCD
            }
            val new_numerator = left_numerator*right_numerator
            val new_denominator = left_denominator*right_denominator
            return FractionalNumb(new_numerator, new_denominator)
        }
        else if (right is Polynom) {
            return right * this
        }
        else throw NonComplianceTypesException()
    }
    override fun div(right: Ring): Ring {
        if (right is FractionalNumb) return this/right
        else throw NonComplianceTypesException()
    }
    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        else if (other is FractionalNumb) return denominator == other.denominator && numerator == other.numerator
        else if (other is Double) return this == FractionalNumb(other)
        else return false
    }
    override fun unaryMinus() : Ring {
        return FractionalNumb(-numerator, denominator)
    }
    operator fun div(b : FractionalNumb) : FractionalNumb {
        val left : FractionalNumb = this
        val right : FractionalNumb = b
        var left_numerator = left.numerator
        var left_denominator = left.denominator
        var right_numerator = right.numerator
        var right_denominator = right.denominator
        var GCD = Support.findGCD(left_numerator, right_denominator)
        if (GCD!=1L){
            left_numerator/=GCD
            right_denominator/=GCD
        }
        GCD = Support.findGCD(left_denominator, right_numerator)
        if (GCD!=1L){
            left_denominator/=GCD
            right_numerator/=GCD
        }
        val new_numerator = left_numerator*right_denominator
        val new_denominator = left.denominator*right.numerator
        return FractionalNumb(new_numerator, new_denominator)
    }
    override fun toString(): String {
        if (!use_improrer_fraction){
            val int_value = numerator/denominator
            val true_numerator = numerator%denominator
            if (denominator != 1L) return "$int_value $true_numerator/$denominator"
            else return numerator.toString()
        }
        else {
            if (denominator != 1L) return "  $numerator/$denominator"
            else return numerator.toString()
        }
    }
    fun toDouble() : Double{
        return numerator.toDouble()/denominator.toDouble()
    }
}