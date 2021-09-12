package com.example.flamemathnew.back.point

import com.example.flamemathnew.back.mathobject.MathObject
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.support.Support.Companion.createSingleArrayList

class Point : MathObject {
    var coords = mutableListOf<Ring>()
    var n = 0

    internal constructor(n: Int) {
        coords = createSingleArrayList({createNumb(0.0)}, n)
        this.n = n
    }

    constructor(_arr: MutableList<Ring>){
        coords = _arr
    }



    fun getCoord(i: Int): Ring {
        return if (0 <= i && i < n) coords[i] else throw IllegalArgumentException()
    }
    override fun toString(): String {
        var temp : String = "("
        for (i in coords){
            temp += "$i , "
        }
        temp = temp.substring(0, temp.length-3)
        return temp
    }

    companion object
    {
        fun generateFromDigits(_arr: MutableList<Double>) : Point
        {
            var coords = mutableListOf<Ring>()
            coords = createSingleArrayList({ createNumb(0.0) },_arr.size)
            for (i in 0 until _arr.size) coords[i] = createNumb(_arr[i])
            return Point(coords)
        }
    }
}