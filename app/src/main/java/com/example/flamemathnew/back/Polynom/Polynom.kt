package com.example.flamemathnew.back.Polynom

import MRV.Computer
import MathObject.Ring
import Number.Dec_Number
import Number.FractionalNumber
import Number.createNumber
import Support.createSingleArrayList
import android.os.Build

class Polynom() : Ring() {
    var cofs : ArrayList <Ring> = createSingleArrayList({ createNumber(0.0) }, 1)
    constructor(_cofs: ArrayList<Ring>) : this(){
        var i : Int = _cofs.size - 1
        while (_cofs[i].equals(0.0) && i > 0){
            i--
        }
        if (i == _cofs.size - 1) cofs = _cofs
        else {
            cofs[0] = _cofs[0]
            for (j in 1 until i + 1) cofs.add(_cofs[i])
        }
    }
    override fun plus(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr : ArrayList<Ring>
            if (left.cofs.size != right.cofs.size)
            {
                temp_arr = createSingleArrayList({ createNumber(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
            }
            else temp_arr = createSingleArrayList({ createNumber(0.0) }, left.cofs.size)
            for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
            for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] + right.cofs[i]
            return Polynom(temp_arr)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun minus(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr : ArrayList<Ring>
            if (left.cofs.size != right.cofs.size)
            {
                temp_arr = createSingleArrayList({ createNumber(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
            }
            else temp_arr = createSingleArrayList({ createNumber(0.0) }, left.cofs.size)
            for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
            for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] - right.cofs[i]
            return Polynom(temp_arr)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun times(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr = createSingleArrayList({ createNumber(0.0) }, left.cofs.size + right.cofs.size + 1)
            for (i in 0 until left.cofs.size) for (j in 0 until right.cofs.size) temp_arr[i+j] = temp_arr[i+j] + left.cofs[i] * right.cofs[j]
            return Polynom(temp_arr)
        }
        else if (right is FractionalNumber || right is Dec_Number){
            val temp_arr = createSingleArrayList({ createNumber(0.0) }, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] * right
            return Polynom(temp_arr)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun div(right: Ring): Ring {
        if (right is Polynom){
            var left = Polynom(cofs)
            val res_div : ArrayList<Ring> = arrayListOf()
            while (left.cofs.size >= right.cofs.size){
                val n : Int = left.cofs.size - right.cofs.size
                var temp : Polynom = Polynom(right.cofs)
                val cof : Ring = left.cofs[left.cofs.size - 1] / temp.cofs[temp.cofs.size - 1]
                res_div.add(cof)
                temp.incpower(n)
                temp = (temp * cof) as Polynom
                left = (left - temp) as Polynom
            }
            if (left.cofs.size == 1 && left.cofs[0].equals(0.0)){
                res_div.reverse()
                return Polynom(res_div)
            }
            else throw Computer.BAD_ARGUMENTS()
        }
        else if (right is FractionalNumber || right is Dec_Number){
            val temp_arr = createSingleArrayList({createNumber(0.0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] / right
            return Polynom(temp_arr)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    fun incpower(power : Int){
        val temparr = createSingleArrayList({ createNumber(0.0) }, cofs.size + power)
        for (i in 0 until cofs.size) temparr[i+power] = cofs[i]
        cofs = temparr
    }
    override fun equals(other: Any?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unaryMinus(): Ring {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        TODO("Not yet implemented")
    }
}