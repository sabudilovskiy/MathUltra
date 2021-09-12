package com.example.flamemathnew.back.affinespace

import com.example.flamemathnew.back.mathobject.MathObject
import com.example.flamemathnew.back.matrix.Matrix
import com.example.flamemathnew.mid.exceptions.MatrixErrorException
import kotlin.collections.*

class AffineSpace(_v0: Matrix, arr: MutableList<Matrix>) : MathObject() {
    var v0 : Matrix = _v0
    var base : MutableList<Matrix> = arr
    init
    {
        var m : Int = v0.m
        for (vector in arr){
            if (vector.m != m || vector.m != 1) throw MatrixErrorException()
        }
    }
    override fun toString(): String {
        var temp : String = "Афинное пространство: \n v0 = ("
        temp+=v0.toString().substring(0,v0.toString().length-2) + ")\n"
        var i : Int = 1
        for (vector in base) {
            temp+="v$i = ("+ vector.toString().substring(0, vector.toString().length-2)+")\n"
            i++
        }
        return temp
    }
}