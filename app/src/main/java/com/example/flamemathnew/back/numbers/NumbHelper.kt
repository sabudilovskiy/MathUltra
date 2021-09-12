package com.example.flamemathnew.back.numbers

import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.parameters.Number
import com.example.flamemathnew.back.support.Support.Companion.createSingleArrayList
import com.example.flamemathnew.back.support.Support.Companion.toBinary
import com.example.flamemathnew.mid.exceptions.NonComplianceTypesException
import com.example.flamemathnew.mid.settings.Numbers
import kotlin.math.abs

class NumbHelper {
    companion object
    {
        public fun createNumb(value : Double) : Numb {
            val cur_set : com.example.flamemathnew.back.parameters.Number = Numbers.type;
            if (cur_set.equals(com.example.flamemathnew.back.parameters.Number.PROPER)) {
                return FractionalNumb(value)
            }
            else {
                return DecNumb(value)
            }
        }
        public fun createNumb(value : Long) : Numb {
            val cur_set : com.example.flamemathnew.back.parameters.Number = Numbers.type;
            if (cur_set.equals(com.example.flamemathnew.back.parameters.Number.PROPER)) {
                return FractionalNumb(value)
            }
            else {
                return DecNumb(value)
            }
        }
        public fun createNumb(value : Int) : Numb {
            val cur_set : com.example.flamemathnew.back.parameters.Number = Numbers.type;
            if (cur_set.equals(Number.PROPER)) {
                return FractionalNumb(value)
            }
            else {
                return DecNumb(value)
            }
        }
        public fun findDividers(numb : Numb) : MutableList<Ring>{
            var temp : Long
            val answer = mutableListOf<Ring>()
            if (isInteger(numb)) {
                if (numb is FractionalNumb) temp = numb.numerator
                else if (numb is DecNumb) temp = numb.value.toLong()
                else throw NonComplianceTypesException()
            }
            else throw NonComplianceTypesException()
            temp = abs(temp)
            var l : Long = 2L
            while (temp % l == 0L){
                temp = temp/l
                answer.add(createNumb(l))
            }
            l++
            while (l <= temp ){
                while (temp % l == 0L){
                    temp = temp/l
                    answer.add(createNumb(l))
                }
                l+=2L
            }
            return answer
        }
        public fun isInteger(numb: Numb) : Boolean{
            if (numb is DecNumb){
                return numb.value.toLong().toDouble() - numb.value == 0.0
            }
            else if (numb is FractionalNumb){
                return numb.denominator == 1L
            }
            else throw NonComplianceTypesException()
        }
        public fun max(left : Ring, right : Ring): Ring {
            if ((left is FractionalNumb || left is DecNumb) && (right is FractionalNumb || right is DecNumb) ){
                val temp_left : Double;
                val temp_right : Double;
                if (left is FractionalNumb) temp_left = left.toDouble()
                else temp_left = (left as DecNumb).value
                if (right is FractionalNumb) temp_right = right.toDouble()
                else temp_right = (right as DecNumb).value
                if (temp_left>temp_right) return createNumb(temp_left)
                else return createNumb(temp_right)
            }
            else throw NonComplianceTypesException()
        }
        fun allComb(dividers : MutableList<Ring>) : MutableList<Ring>{
            var i : Int = 1
            val comb = Math.pow(2.0, dividers.size.toDouble()).toInt()
            val answer : MutableList<Ring> = mutableListOf()
            while (i < comb) {
                val temp_cofs: MutableList<Ring> = createSingleArrayList({ createNumb(1L) }, 2)
                val cur_comb = i.toBinary(dividers.size)
                var cur_root: Ring = createNumb(1L)
                for (j in 0 until dividers.size) if (cur_comb[j] == '1') cur_root *= dividers[j]
                answer.add(cur_root)
                i++
            }
            return answer
        }
    }
}