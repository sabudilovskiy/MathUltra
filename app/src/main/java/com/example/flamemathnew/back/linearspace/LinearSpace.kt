package com.example.flamemathnew.back.linearspace

import com.example.flamemathnew.back.mathobject.MathObject
import com.example.flamemathnew.back.matrix.Matrix
import com.example.flamemathnew.mid.exceptions.MatrixErrorException

class LinearSpace : MathObject {
    lateinit var base : MutableList<Matrix>
    constructor (arr: MutableList <Matrix>){
        var m : Int = arr[0].m;
        for (matrix in arr){
            if (matrix.m != m || matrix.n != 1) throw MatrixErrorException()
        }
        base = arr;
    }
    override fun toString(): String {
        var temp : String = "Линейное пространство: \n"
        var i : Int = 1
        for (vector in base) {
            temp+="v$i = ("+ vector.toString().substring(0, vector.toString().length-2)+")\n"
            i++
        }
        return temp
    }
}