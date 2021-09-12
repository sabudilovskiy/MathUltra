package com.example.flamemathnew.back.polynom

import com.example.flamemathnew.back.logger.Log.commit
import com.example.flamemathnew.back.logger.Tag
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.numbers.Numb
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.allComb
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.findDividers
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.isInteger
import com.example.flamemathnew.back.support.Support.Companion.CTP
import com.example.flamemathnew.back.support.Support.Companion.createSingleArrayList
import com.example.flamemathnew.mid.exceptions.CannotDivException
import com.example.flamemathnew.mid.exceptions.HaveNotSolutionsException
import com.example.flamemathnew.mid.exceptions.NonComplianceTypesException


class Polynom() : Ring() {
    var key : String = ""
    var cofs : MutableList<Ring> = createSingleArrayList({ createNumb(0.0) }, 1)
    constructor(_cofs: MutableList<Ring>, _key : String ) : this(){
        key = _key
        var i : Int = _cofs.size - 1
        while (_cofs[i].equals(0.0) && i > 0){
            i--
        }
        cofs[0] = _cofs[0]
        for (j in 1 until i + 1) cofs.add(_cofs[j])
    }
    override fun plus(right: Ring): Ring {
        if (right is Polynom){
            if (right.key == key){
                val left = this
                val temp_arr : MutableList<Ring>
                if (left.cofs.size != right.cofs.size)
                {
                    temp_arr = createSingleArrayList({ createNumb(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
                }
                else temp_arr = createSingleArrayList({ createNumb(0.0) }, left.cofs.size)
                for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
                for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] + right.cofs[i]
                return Polynom(temp_arr, key)
            }
            else throw NonComplianceTypesException()
        }
        else if (right is Numb){
            val temp_arr : MutableList<Ring> = createSingleArrayList({ createNumb(0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i]+=cofs[i]
            temp_arr[0] -= right
            return Polynom(temp_arr, key)
        }
        else throw NonComplianceTypesException()
    }
    override fun minus(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
                val left = this
                val temp_arr : MutableList<Ring>
                if (left.cofs.size != right.cofs.size)
                {
                    temp_arr = createSingleArrayList({ createNumb(0.0) }, if(left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
                }
                else temp_arr = createSingleArrayList({ createNumb(0.0) }, left.cofs.size)
                for (i in 0 until left.cofs.size) temp_arr[i] = temp_arr[i] + left.cofs[i]
                for (i in 0 until right.cofs.size) temp_arr[i] = temp_arr[i] - right.cofs[i]
                return Polynom(temp_arr, key)
            }
            else throw NonComplianceTypesException()

        }
        else if (right is Numb){
            val temp_arr : MutableList<Ring> = createSingleArrayList({ createNumb(0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i]+=cofs[i]
            temp_arr[0] -= right
            return Polynom(temp_arr, key)
        }
        else throw NonComplianceTypesException()
    }
    override fun times(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
                val left = this
                val temp_arr : MutableList<Ring>  = createSingleArrayList({ createNumb(0.0) }, left.maxpower() + right.maxpower() + 1)
                for (i in 0 until left.cofs.size) for (j in 0 until right.cofs.size) temp_arr[i+j] = temp_arr[i+j] + left.cofs[i] * right.cofs[j]
                return Polynom(temp_arr, key)
            }
            else throw NonComplianceTypesException()
        }
        else if (right is Numb){
            val temp_arr : MutableList<Ring>  = createSingleArrayList({ createNumb(0.0) }, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] * right
            return Polynom(temp_arr, key)
        }
        else throw NonComplianceTypesException()
    }
    override fun div(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
                var left = Polynom(cofs, key)
                if (left.maxpower() == 0) throw CannotDivException()
                val res_div : MutableList<Ring> = createSingleArrayList({ createNumb(0L)}, left.maxpower())
                while (left.cofs.size >= right.cofs.size){
                    val n : Int = left.cofs.size - right.cofs.size
                    var temp : Polynom = Polynom(right.cofs, key)
                    val cof : Ring = left.cofs[left.cofs.size - 1] / temp.cofs[temp.cofs.size - 1]
                    res_div[n] = cof
                    temp.incpower(n)
                    temp = (temp * cof) as Polynom
                    left = (left - temp) as Polynom
                }
                if (left.cofs.size == 1 && left.cofs[0].equals(0.0)){
                    return Polynom(res_div, key)
                }
                else throw CannotDivException()
            }
            else throw NonComplianceTypesException()
        }
        else if (right is Numb){
            val temp_arr : MutableList<Ring> = createSingleArrayList({createNumb(0.0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] / right
            return Polynom(temp_arr, key)
        }
        else throw NonComplianceTypesException()
    }
    fun maxpower() : Int {return cofs.size - 1}
    fun incpower(power : Int){
        val temparr : MutableList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size + power)
        for (i in 0 until cofs.size) temparr[i+power] = cofs[i]
        cofs = temparr
    }
    override fun equals(other: Any?): Boolean {
        if (other is Polynom){
            if (maxpower() == other.maxpower()){
                for (i in 0 until maxpower()) if (cofs[i]!=other.cofs[i]) return false
                return true
            }
            else return false
        }
        else if (other is Numb) return maxpower() == 0 && cofs[0] == other
        else if (other is Number) {
            val temp : Double = other.toDouble()
            return this == createNumb(temp) as Ring
        }
        else return false
    }
    override fun unaryMinus(): Ring {
        val temp_cofs : MutableList<Ring> = createSingleArrayList({createNumb(0.0)},  cofs.size)
        for (i in 0 until cofs.size) temp_cofs[i] = -cofs[i]
        return Polynom(temp_cofs, key)
    }
    override fun toString(): String {
        if (cofs.size > 1){
            var answer = ""
            var i : Int = maxpower()
            val zero = createNumb(0)
            val one = createNumb(1)
            while (i > -1){
                if (cofs[i] != zero){
                    if (cofs[i] as Numb > zero && i < maxpower()) answer += "+"
                    if (cofs[i] != one && cofs[i] != -one){
                        answer += cofs[i].toString()
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i==1) answer += key
                    }
                    else if (cofs[i] == -one && i != 0){
                        answer += "-"
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i==1) answer += key
                    }
                    else if (cofs[i] == one && i!= 0)
                    {
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i==1) answer += key
                    }
                    else{
                        answer += cofs[0].toString()
                    }
                }
                i--
            }
            return answer
        }
        else return cofs[0].toString()
    }
    fun onlyintcofs() : Boolean{
        for (cof in cofs){
            if (!isInteger(cof as Numb)) return false
        }
        return true
    }
    fun solve() : MutableList<Ring>{
        if (maxpower() > 0){
            log_this()
            val roots = mutableListOf<Ring>()
            var mod : Polynom = Polynom(cofs, key)
            val oneArr : MutableList<Ring> = mutableListOf(createNumb(0.0) , createNumb(1.0))
            val one : Polynom = Polynom(oneArr, key)
            val havezero : Boolean = mod.cofs[0].equals(0.0)
            if (havezero) commit("Так как свободный член равен нулю, то корнем является ноль. Разделим многочлен, чтобы избавится от нулевых корней",
                Tag.PROCEEDING)
            while (mod.cofs[0].equals(0.0)){
                mod = (mod / one) as Polynom
                roots.add(createNumb(0.0))
            }
            if (havezero) mod.log_this()
            if (onlyintcofs()){
                if (cofs[maxpower()] == createNumb(1L) || cofs[maxpower()] == createNumb(-1L)){
                    commit("Проверим ${key} = -1", Tag.PROCEEDING)
                    var temp_pol = Polynom()
                    try {
                        val temp_cofs : MutableList<Ring> = createSingleArrayList({createNumb(1)}, 2)
                        temp_pol = Polynom(temp_cofs, key)
                        while (true) {
                            mod = (mod / temp_pol) as Polynom
                            commit("Деление на ${temp_pol} успешно. -1 является корнем",
                                Tag.SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(-1))
                        }
                    }
                    catch (error : CannotDivException) {
                        commit("Деление на ${temp_pol} неуспешно. -1 не является корнем",
                            Tag.SKIPPED)
                    }
                    commit("Проверим ${key} = 1", Tag.PROCEEDING)
                    try {
                        val temp_cofs : MutableList<Ring> = createSingleArrayList({createNumb(1)}, 2)
                        temp_cofs[0] = createNumb(-1)
                        val temp_pol = Polynom(temp_cofs, key)
                        while (true) {
                            mod = (mod / temp_pol) as Polynom
                            commit("Деление на ${temp_pol} успешно. 1 является корнем",
                                Tag.SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(1))
                        }
                    }
                    catch (error : CannotDivException) {commit("Деление на ${temp_pol} неуспешно. 1 не является корнем",
                        Tag.SKIPPED)}
                    if (roots.size < maxpower()){
                        commit("Проверим делителей свободного члена.", Tag.PROCEEDING)
                        val dividers = findDividers(mod.cofs[0] as Numb)
                        val possible_roots = allComb(dividers)
                        var temp : String = ""
                        for (root in possible_roots) temp+="±$root "
                        commit("Возможные кандидаты: $temp", Tag.PROCEEDING)
                        var i : Int = 1
                        val n = possible_roots.size
                        while (i < n && roots.size < maxpower()){
                            val temp_cofs : MutableList<Ring> = createSingleArrayList({createNumb(1L)}, 2)
                            var cur_root : Ring = possible_roots[i]
                            temp_cofs[0] = -cur_root
                            temp_pol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / temp_pol) as Polynom
                                commit("Деление на ${temp_pol} успешно. $cur_root является корнем",
                                    Tag.SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            }
                            catch (error : CannotDivException){
                                commit("Деление на ${temp_pol} неуспешно. $cur_root не является корнем",
                                    Tag.SOLUTION)
                            }
                            cur_root = -cur_root
                            temp_cofs[0] = cur_root
                            temp_pol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / temp_pol) as Polynom
                                commit("Деление на ${temp_pol} успешно. $cur_root является корнем",
                                    Tag.SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            }
                            catch (error : CannotDivException){
                                commit("Деление на ${temp_pol} неуспешно. $cur_root не является корнем",
                                    Tag.SOLUTION)
                            }
                            i++
                        }
                    }
                    return roots
                }
                else {
                    TODO("реализовать позже")
                }
            }
            else{
                TODO("реализовать позже")
            }
        }
        else throw HaveNotSolutionsException()
    }
}