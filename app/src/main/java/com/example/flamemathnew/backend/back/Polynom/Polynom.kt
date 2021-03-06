package com.example.flamemathnew.backend.back.Polynom

import com.example.flamemathnew.backend.back.Logger.Log.commit
import com.example.flamemathnew.backend.back.Logger.Tag.*
import com.example.flamemathnew.backend.mid.Computer
import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.Numbers.*
import com.example.flamemathnew.backend.back.Support.CTP
import com.example.flamemathnew.backend.back.Support.createSingleArrayList

class Polynom() : Ring() {
    var key: String = ""
    var cofs: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, 1)

    constructor(_cofs: ArrayList<Ring>, _key: String) : this() {
        key = _key
        var i: Int = _cofs.size - 1
        while (_cofs[i].equals(0.0) && i > 0) {
            i--
        }
        cofs[0] = _cofs[0]
        for (j in 1 until i + 1) cofs.add(_cofs[j])
    }

    override fun plus(right: Ring): Ring {
        if (right is Polynom) {

            if (right.key == key) {
                val left = this

                val tempArr: ArrayList<Ring> = if (left.cofs.size != right.cofs.size) {
                    createSingleArrayList({ createNumb(0.0) }, if (left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
                } else createSingleArrayList({ createNumb(0.0) }, left.cofs.size)

                for (i in 0 until left.cofs.size) tempArr[i] = tempArr[i] + left.cofs[i]
                for (i in 0 until right.cofs.size) tempArr[i] = tempArr[i] + right.cofs[i]
                return Polynom(tempArr, key)

            } else throw Computer.NON_COMPLIANCE_TYPES()

        } else if (right is Numb) {
            val tempArr: ArrayList<Ring> = createSingleArrayList({ createNumb(0) }, cofs.size)
            for (i in 0 until cofs.size) tempArr[i] += cofs[i]
            tempArr[0] -= right
            return Polynom(tempArr, key)
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun minus(right: Ring): Ring {
        if (right is Polynom) {
            if (key == right.key) {
                val left = this

                val tempArr: ArrayList<Ring> = if (left.cofs.size != right.cofs.size) {
                    createSingleArrayList({ createNumb(0.0) }, if (left.cofs.size > right.cofs.size) left.cofs.size else right.cofs.size)
                } else createSingleArrayList({ createNumb(0.0) }, left.cofs.size)

                for (i in 0 until left.cofs.size) tempArr[i] = tempArr[i] + left.cofs[i]
                for (i in 0 until right.cofs.size) tempArr[i] = tempArr[i] - right.cofs[i]
                return Polynom(tempArr, key)
            } else throw Computer.NON_COMPLIANCE_TYPES()

        } else if (right is Numb) {
            val tempArr: ArrayList<Ring> = createSingleArrayList({ createNumb(0) }, cofs.size)
            for (i in 0 until cofs.size) tempArr[i] += cofs[i]
            tempArr[0] -= right
            return Polynom(tempArr, key)
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun times(right: Ring): Ring {
        return if (right is Polynom) {
            if (key == right.key) {
                val left = this
                val tempArr: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, left.maxpower() + right.maxpower() + 1)
                for (i in 0 until left.cofs.size) for (j in 0 until right.cofs.size) tempArr[i + j] = tempArr[i + j] + left.cofs[i] * right.cofs[j]
                Polynom(tempArr, key)
            } else throw Computer.NON_COMPLIANCE_TYPES()

        } else if (right is Numb) {
            val tempArr: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size)
            for (i in 0 until cofs.size) tempArr[i] = cofs[i] * right
            Polynom(tempArr, key)
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun div(right: Ring): Ring {
        if (right is Polynom) {
            if (key == right.key) {
                var left = Polynom(cofs, key)

                if (left.maxpower() == 0) throw Computer.CANNOTDIV()

                val resDiv: ArrayList<Ring> = createSingleArrayList({ createNumb(0L) }, left.maxpower())

                while (left.cofs.size >= right.cofs.size) {
                    val n: Int = left.cofs.size - right.cofs.size
                    var temp = Polynom(right.cofs, key)
                    val cof: Ring = left.cofs[left.cofs.size - 1] / temp.cofs[temp.cofs.size - 1]
                    resDiv[n] = cof
                    temp.incpower(n)
                    temp = (temp * cof) as Polynom
                    left = (left - temp) as Polynom
                }
                if (left.cofs.size == 1 && left.cofs[0].equals(0.0)) {
                    return Polynom(resDiv, key)
                } else throw Computer.CANNOTDIV()
            } else throw Computer.NON_COMPLIANCE_TYPES()

        } else if (right is Numb) {
            val tempArr: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size)

            for (i in 0 until cofs.size) tempArr[i] = cofs[i] / right

            return Polynom(tempArr, key)
        } else throw Computer.NON_COMPLIANCE_TYPES()
    }

    fun maxpower(): Int {
        return cofs.size - 1
    }

    private fun incpower(power: Int) {
        val temparr: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size + power)
        for (i in 0 until cofs.size) temparr[i + power] = cofs[i]
        cofs = temparr
    }

    override fun equals(other: Any?): Boolean {
        if (other is Polynom) {
            if (maxpower() == other.maxpower()) {
                for (i in 0 until maxpower()) if (cofs[i] != other.cofs[i]) return false
                return true
            } else return false
        } else if (other is Numb) return maxpower() == 0 && cofs[0] == other

        else if (other is Number) {
            val temp: Double = other.toDouble()
            return this == createNumb(temp) as Ring
        } else return false
    }

    override fun unaryMinus(): Ring {
        val tempCofs: ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, cofs.size)
        for (i in 0 until cofs.size) tempCofs[i] = -cofs[i]
        return Polynom(tempCofs, key)
    }

    override fun toString(): String {
        if (cofs.size > 1) {
            var answer = ""
            var i: Int = maxpower()
            val zero = createNumb(0)
            val one = createNumb(1)
            while (i > -1) {
                if (cofs[i] != zero) {
                    if (cofs[i] as Numb > zero && i < maxpower()) answer += "+"
                    if (cofs[i] != one && cofs[i] != -one) {
                        answer += cofs[i].toString()
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i == 1) answer += key
                    } else if (cofs[i] == -one && i != 0) {
                        answer += "-"
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i == 1) answer += key
                    } else if (cofs[i] == one && i != 0) {
                        if (i > 1) answer += "$key${CTP(i)}"
                        else if (i == 1) answer += key
                    } else {
                        answer += cofs[0].toString()
                    }
                }
                i--
            }
            return answer
        } else return cofs[0].toString()
    }

    private fun onlyintcofs(): Boolean {
        for (cof in cofs) {
            if (!isInteger(cof as Numb)) return false
        }
        return true
    }

    fun solve(): ArrayList<Ring> {
        if (maxpower() > 0) {
            log_this()
            val roots = arrayListOf<Ring>()
            var mod = Polynom(cofs, key)
            val oneArr: ArrayList<Ring> = arrayListOf(createNumb(0.0), createNumb(1.0))
            val one = Polynom(oneArr, key)
            val havezero: Boolean = mod.cofs[0].equals(0.0)

            if (havezero) commit("?????? ?????? ?????????????????? ???????? ?????????? ????????, ???? ???????????? ???????????????? ????????. ???????????????? ??????????????????, ?????????? ?????????????????? ???? ?????????????? ????????????",
                PROCEEDING)

            while (mod.cofs[0].equals(0.0)) {
                mod = (mod / one) as Polynom
                roots.add(createNumb(0.0))
            }

            if (havezero) mod.log_this()
            if (onlyintcofs()) {
                if (cofs[maxpower()] == createNumb(1L) || cofs[maxpower()] == createNumb(-1L)) {
                    commit("???????????????? $key = -1", PROCEEDING)
                    var tempPol = Polynom()
                    try {
                        val tempCofs: ArrayList<Ring> = createSingleArrayList({ createNumb(1) }, 2)
                        tempPol = Polynom(tempCofs, key)
                        while (true) {
                            mod = (mod / tempPol) as Polynom
                            commit("?????????????? ???? $tempPol ??????????????. -1 ???????????????? ????????????", SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(-1))
                        }
                    } catch (error: Computer.CANNOTDIV) {
                        commit("?????????????? ???? $tempPol ??????????????????. -1 ???? ???????????????? ????????????", SKIPPED)
                    }
                    commit("???????????????? $key = 1", PROCEEDING)

                    try {
                        val tempCofs: ArrayList<Ring> = createSingleArrayList({ createNumb(1) }, 2)
                        tempCofs[0] = createNumb(-1)

                        val tempPol1 = Polynom(tempCofs, key)
                        while (true) {
                            mod = (mod / tempPol1) as Polynom
                            commit("?????????????? ???? $tempPol1 ??????????????. 1 ???????????????? ????????????", SOLUTION)
                            mod.log_this()
                            roots.add(createNumb(1))
                        }
                    } catch (error: Computer.CANNOTDIV) {
                        commit("?????????????? ???? $tempPol ??????????????????. 1 ???? ???????????????? ????????????", SKIPPED)
                    }
                    if (roots.size < maxpower()) {
                        commit("???????????????? ?????????????????? ???????????????????? ??????????.", PROCEEDING)
                        val dividers = findDividers(mod.cofs[0] as Numb)

                        val possibleRoots = allComb(dividers)
                        var temp = ""
                        for (root in possibleRoots) temp += "??$root "

                        commit("?????????????????? ??????????????????: $temp", PROCEEDING)

                        var i = 1
                        val n = possibleRoots.size
                        while (i < n && roots.size < maxpower()) {
                            val temp_cofs: ArrayList<Ring> = createSingleArrayList({ createNumb(1L) }, 2)
                            var cur_root: Ring = possibleRoots[i]
                            temp_cofs[0] = -cur_root
                            tempPol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / tempPol) as Polynom
                                commit("?????????????? ???? $tempPol ??????????????. $cur_root ???????????????? ????????????", SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            } catch (error: Computer.CANNOTDIV) {
                                commit("?????????????? ???? $tempPol ??????????????????. $cur_root ???? ???????????????? ????????????", SOLUTION)
                            }
                            cur_root = -cur_root
                            temp_cofs[0] = cur_root
                            tempPol = Polynom(temp_cofs, key)
                            try {
                                mod = (mod / tempPol) as Polynom
                                commit("?????????????? ???? $tempPol ??????????????. $cur_root ???????????????? ????????????", SOLUTION)
                                mod.log_this()
                                roots.add(cur_root)
                            } catch (error: Computer.CANNOTDIV) {
                                commit("?????????????? ???? ${tempPol} ??????????????????. $cur_root ???? ???????????????? ????????????", SOLUTION)
                            }
                            i++
                        }
                    }
                    return roots
                } else {
                    TODO("?????????????????????? ??????????")
                }
            } else {
                TODO("?????????????????????? ??????????")
            }
        } else throw Computer.HAVE_NOT_SOLUTIONS()
    }
}