package com.example.flamemathnew.backend.back.Numbers

import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.Parameters.Number
import com.example.flamemathnew.backend.back.Parameters.Number.PROPER
import com.example.flamemathnew.backend.back.Support.createSingleArrayList
import com.example.flamemathnew.backend.back.Support.toBinary
import com.example.flamemathnew.backend.mid.Computer
import com.example.flamemathnew.backend.mid.Settings.numbers.type
import kotlin.math.abs
import kotlin.math.pow

fun createNumb(value: Double): Numb {
    val curSet: Number = type
    return if (curSet == PROPER) {
        FractionalNumb(value)
    } else {
        DecNumb(value)
    }
}

fun createNumb(value: Long): Numb {
    val curSet: Number = type
    return if (curSet == PROPER) {
        FractionalNumb(value)
    } else {
        DecNumb(value)
    }
}

fun createNumb(value: Int): Numb {
    val curSet: Number = type
    return if (curSet == PROPER) {
        FractionalNumb(value)
    } else {
        DecNumb(value)
    }
}

fun findDividers(numb: Numb): ArrayList<Ring> {
    val answer = arrayListOf<Ring>()
    var temp: Long = if (isInteger(numb)) {
        when (numb) {
            is FractionalNumb -> numb.numerator
            is DecNumb -> numb.value.toLong()
            else -> throw Computer.NON_COMPLIANCE_TYPES()
        }
    } else throw Computer.NON_COMPLIANCE_TYPES()
    temp = abs(temp)
    var l = 2L
    while (temp % l == 0L) {
        temp /= l
        answer.add(createNumb(l))
    }
    l++
    while (l <= temp) {
        while (temp % l == 0L) {
            temp /= l
            answer.add(createNumb(l))
        }
        l += 2L
    }
    return answer
}

fun isInteger(numb: Numb): Boolean {
    return when (numb) {
        is DecNumb -> {
            numb.value.toLong().toDouble() - numb.value == 0.0
        }
        is FractionalNumb -> {
            numb.denominator == 1L
        }
        else -> throw Computer.NON_COMPLIANCE_TYPES()
    }
}

fun max(left: Ring, right: Ring): Ring {
    return if ((left is FractionalNumb || left is DecNumb) && (right is FractionalNumb || right is DecNumb)) {
        val tempLeft: Double = if (left is FractionalNumb) left.toDouble() else (left as DecNumb).value
        val tempRight: Double = if (right is FractionalNumb) right.toDouble() else (right as DecNumb).value

        if (tempLeft > tempRight) createNumb(tempLeft) else createNumb(tempRight)

    } else throw Computer.NON_COMPLIANCE_TYPES()
}

fun allComb(dividers: ArrayList<Ring>): ArrayList<Ring> {
    var i = 1
    val comb = 2.0.pow(dividers.size.toDouble()).toInt()
    val answer: ArrayList<Ring> = arrayListOf()
    while (i < comb) {
        val temp_cofs: ArrayList<Ring> = createSingleArrayList({ createNumb(1L) }, 2)
        val curComb = i.toBinary(dividers.size)
        var curRoot: Ring = createNumb(1L)
        for (j in 0 until dividers.size) if (curComb[j] == '1') curRoot *= dividers[j]
        answer.add(curRoot)
        i++
    }
    return answer
}

abstract class Numb : Ring() {
    abstract operator fun compareTo(right: Numb): Int
}