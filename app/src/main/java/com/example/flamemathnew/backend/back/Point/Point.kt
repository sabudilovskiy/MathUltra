package com.example.flamemathnew.backend.back.Point

import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.MathObject.MathObject
import com.example.flamemathnew.backend.back.Numbers.createNumb
import com.example.flamemathnew.backend.back.Support.createSingleArrayList

class Point : MathObject {
    var coords = ArrayList<Ring>(0)
    var n = 0

    internal constructor(n: Int) {
        coords = createSingleArrayList({ createNumb(0.0) }, n)
        this.n = n
    }

    constructor(arr: MutableList<Double>) {
        coords = createSingleArrayList({ createNumb(0.0) }, arr.size)
        for (i in 0 until arr.size) coords[i] = createNumb(arr[i])
    }

    constructor(_arr: ArrayList<Ring>) {
        coords = _arr
    }

    fun getCoord(i: Int): Ring {
        return if (i in 0 until n) coords[i] else throw IllegalArgumentException()
    }

    override fun toString(): String {
        var temp: String = "("
        for (i in coords) {
            temp += "$i , "
        }
        temp = temp.substring(0, temp.length - 3)
        return temp
    }
}