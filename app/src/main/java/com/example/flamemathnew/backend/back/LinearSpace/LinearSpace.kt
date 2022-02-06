package com.example.flamemathnew.backend.back.LinearSpace

import com.example.flamemathnew.backend.mid.Computer
import com.example.flamemathnew.backend.back.MathObject.MathObject
import com.example.flamemathnew.backend.back.Matrix.Matrix

class LinearSpace : MathObject {
    lateinit var base: ArrayList<Matrix>

    constructor (arr: ArrayList<Matrix>) {
        var m: Int = arr[0].m;
        for (matrix in arr) {
            if (matrix.m != m || matrix.n != 1) throw Computer.MATRIX_ERROR()
        }
        base = arr;
    }

    override fun toString(): String {
        var temp: String = "Линейное пространство: \n"
        var i: Int = 1
        for (vector in base) {
            temp += "v$i = (" + vector.toString().substring(0, vector.toString().length - 2) + ")\n"
            i++
        }
        return temp
    }
}
