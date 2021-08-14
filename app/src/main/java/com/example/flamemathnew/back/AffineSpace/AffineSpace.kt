package AffineSpace

import MRV.Computer
import MathObject.MathObject.MathObject
import Matrix.Matrix

class AffineSpace(_v0: Matrix, arr: ArrayList<Matrix>) : MathObject() {
    var v0 : Matrix = _v0
    var base : ArrayList<Matrix> = arr
    init {
        var m : Int = v0.m
        for (vector in arr){
            if (vector.m != m || vector.m != 1) throw Computer.MATRIX_ERROR()
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