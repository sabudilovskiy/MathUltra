package com.example.flamemathnew.back.Polynom

import MRV.Computer
import MathObject.Ring
import Number.DecNumb
import Number.FractionalNumb
import Number.Numb
import Number.createNumb
import Support.createSingleArrayList

class Polynom() : Ring() {
    var key : String = ""
    var cofs : ArrayList <Ring> = createSingleArrayList({ createNumb(0.0) }, 1)
    constructor(_cofs: ArrayList<Ring>, _key : String ) : this(){
        key = _key
        var i : Int = _cofs.size - 1
        while (_cofs[i].equals(0.0) && i > 0){
            i--
        }
        cofs[0] = _cofs[0]
        for (j in 1 until i + 1) cofs.add(_cofs[i])
    }
    override fun plus(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr : ArrayList<Ring>
            if (left.cofs.size != right.cofs.size)
            {
                temp_arr = createSingleArrayList({ createNumb(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
            }
            else temp_arr = createSingleArrayList({ createNumb(0.0) }, left.cofs.size)
            for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
            for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] + right.cofs[i]
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun minus(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr : ArrayList<Ring>
            if (left.cofs.size != right.cofs.size)
            {
                temp_arr = createSingleArrayList({ createNumb(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
            }
            else temp_arr = createSingleArrayList({ createNumb(0.0) }, left.cofs.size)
            for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
            for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] - right.cofs[i]
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun times(right: Ring): Ring {
        if (right is Polynom){
            val left = this
            val temp_arr : ArrayList<Ring>  = createSingleArrayList({ createNumb(0.0) }, left.cofs.size + right.cofs.size + 1)
            for (i in 0 until left.cofs.size) for (j in 0 until right.cofs.size) temp_arr[i+j] = temp_arr[i+j] + left.cofs[i] * right.cofs[j]
            return Polynom(temp_arr, key)
        }
        else if (right is FractionalNumb || right is DecNumb){
            val temp_arr : ArrayList<Ring>  = createSingleArrayList({ createNumb(0.0) }, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] * right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun div(right: Ring): Ring {
        if (right is Polynom){
            var left = Polynom(cofs, key)
            val res_div : ArrayList<Ring> = arrayListOf()
            while (left.cofs.size >= right.cofs.size){
                val n : Int = left.cofs.size - right.cofs.size
                var temp : Polynom = Polynom(right.cofs, key)
                val cof : Ring = left.cofs[left.cofs.size - 1] / temp.cofs[temp.cofs.size - 1]
                res_div.add(cof)
                temp.incpower(n)
                temp = (temp * cof) as Polynom
                left = (left - temp) as Polynom
            }
            if (left.cofs.size == 1 && left.cofs[0].equals(0.0)){
                res_div.reverse()
                return Polynom(res_div, key)
            }
            else throw Computer.BAD_ARGUMENTS()
        }
        else if (right is FractionalNumb || right is DecNumb){
            val temp_arr : ArrayList<Ring> = createSingleArrayList({createNumb(0.0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] / right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    fun incpower(power : Int){
        val temparr : ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size + power)
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
        var answer = ""
        var i : Int = cofs.size - 1
        while (i > 0){
            answer += key +"^" + i + " + "
            i--
        }
        answer += cofs[0]
        return answer
    }
    fun solve() : ArrayList<Ring>{
        TODO("необходимые функции только в процессе")
    }
}