package com.example.flamemathnew.back.Polynom

import Logger.Log.commit
import Logger.Tag.*
import MRV.Computer
import MathObject.Ring
import Number.*
import Support.CTP
import Support.createSingleArrayList
import Support.toBinary
import java.lang.Math.pow

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
        for (j in 1 until i + 1) cofs.add(_cofs[j])
    }
    override fun plus(right: Ring): Ring {
        if (right is Polynom){
            if (right.key == key){
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
        else if (right is Numb){
            val temp_arr : ArrayList<Ring> = createSingleArrayList({ createNumb(0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i]+=cofs[i]
            temp_arr[0] -= right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    override fun minus(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
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
        else if (right is Numb){
            val temp_arr : ArrayList<Ring> = createSingleArrayList({ createNumb(0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i]+=cofs[i]
            temp_arr[0] -= right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    override fun times(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
                val left = this
                val temp_arr : ArrayList<Ring>  = createSingleArrayList({ createNumb(0.0) }, left.maxpower() + right.maxpower() + 1)
                for (i in 0 until left.cofs.size) for (j in 0 until right.cofs.size) temp_arr[i+j] = temp_arr[i+j] + left.cofs[i] * right.cofs[j]
                return Polynom(temp_arr, key)
            }
            else throw Computer.NON_COMPLIANCE_TYPES()
        }
        else if (right is Numb){
            val temp_arr : ArrayList<Ring>  = createSingleArrayList({ createNumb(0.0) }, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] * right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    override fun div(right: Ring): Ring {
        if (right is Polynom){
            if (key == right.key){
                var left = Polynom(cofs, key)
                if (left.maxpower() == 0) throw Computer.CANNOTDIV()
                val res_div : ArrayList<Ring> = createSingleArrayList({ createNumb(0L)}, left.maxpower())
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
                else throw Computer.CANNOTDIV()
            }
            else throw Computer.NON_COMPLIANCE_TYPES()
        }
        else if (right is Numb){
            val temp_arr : ArrayList<Ring> = createSingleArrayList({createNumb(0.0)}, cofs.size)
            for (i in 0 until cofs.size) temp_arr[i] = cofs[i] / right
            return Polynom(temp_arr, key)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    fun maxpower() : Int {return cofs.size - 1}
    fun incpower(power : Int){
        val temparr : ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size + power)
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
        val temp_cofs : ArrayList<Ring> = createSingleArrayList({createNumb(0.0)},  cofs.size)
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
    fun solve() : ArrayList<Ring>{
        if (maxpower() > 0){
            log_this()
            val roots = arrayListOf<Ring>()
            var mod : Polynom = Polynom(cofs, key)
            val oneArr : ArrayList<Ring> = arrayListOf(createNumb(0.0), createNumb(1.0))
            val one : Polynom = Polynom(oneArr, key)
            val havezero : Boolean = mod.cofs[0].equals(0.0)
            if (havezero) commit("Так как свободный член равен нулю, то корнем является ноль. Разделим многочлен, чтобы избавится от нулевых корней", PROCEEDING)
            while (mod.cofs[0].equals(0.0)){
                mod = (mod / one) as Polynom
                roots.add(createNumb(0.0))
            }
            if (havezero) mod.log_this()
            if (onlyintcofs()){
                if (cofs[maxpower()] == createNumb(1L) || cofs[maxpower()] == createNumb(-1L)){
                    commit("Проверим ${key} = -1", PROCEEDING)
                    var temp_pol = Polynom()
                    try {
                        val temp_cofs : ArrayList<Ring> = createSingleArrayList({createNumb(1)}, 2)
                        temp_pol = Polynom(temp_cofs, key)
                        while (true) {
                            mod = (mod / temp_pol) as Polynom
                            commit("Деление на ${temp_pol} успешно. -1 является корнем", SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(-1))
                        }
                    }
                    catch (error : Computer.CANNOTDIV) {
                        commit("Деление на ${temp_pol} неуспешно. -1 не является корнем", SKIPPED)
                    }
                    commit("Проверим ${key} = 1", PROCEEDING)
                    try {
                        val temp_cofs : ArrayList<Ring> = createSingleArrayList({createNumb(1)}, 2)
                        temp_cofs[0] = createNumb(-1)
                        val temp_pol = Polynom(temp_cofs, key)
                        while (true) {
                            mod = (mod / temp_pol) as Polynom
                            commit("Деление на ${temp_pol} успешно. 1 является корнем", SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(1))
                        }
                    }
                    catch (error : Computer.CANNOTDIV) {commit("Деление на ${temp_pol} неуспешно. 1 не является корнем", SKIPPED)}
                    if (roots.size < maxpower()){
                        commit("Проверим делителей свободного члена.", PROCEEDING)
                        val possible_roots = findDividers(mod.cofs[0] as Numb)
                        var temp : String = ""
                        for (root in possible_roots) {
                            temp+="±$root "
                        }
                        commit("Возможные кандидаты + $temp", PROCEEDING )
                        var i : Int = 1
                        val comb = pow(2.0, possible_roots.size.toDouble()).toInt()
                        while (i < comb && roots.size < maxpower()){
                            val temp_cofs : ArrayList<Ring> = createSingleArrayList({createNumb(1L)}, 2)
                            val cur_comb = i.toBinary(possible_roots.size)
                            var cur_root : Ring = createNumb(1L)
                            for (i in 0 until possible_roots.size) if (cur_comb[i] == '1') cur_root *= possible_roots[i]
                            temp_cofs[0] = -cur_root
                            temp_pol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / temp_pol) as Polynom
                                commit("Деление на ${temp_pol} успешно. $cur_root является корнем", SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            }
                            catch (error : Computer.CANNOTDIV){
                                commit("Деление на ${temp_pol} неуспешно. $cur_root не является корнем", SOLUTION)
                            }
                            temp_cofs[0] = cur_root
                            temp_pol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / temp_pol) as Polynom
                                commit("Деление на ${temp_pol} успешно. $cur_root является корнем", SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            }
                            catch (error : Computer.CANNOTDIV){
                                commit("Деление на ${temp_pol} неуспешно. $cur_root не является корнем", SOLUTION)
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
        else throw Computer.HAVE_NOT_SOLUTIONS()
    }
}