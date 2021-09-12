package com.example.flamemathnew.back.matrix

import com.example.flamemathnew.back.affinespace.AffineSpace
import com.example.flamemathnew.back.linearspace.LinearSpace
import com.example.flamemathnew.back.logger.Log.commit
import com.example.flamemathnew.back.logger.Tag
import com.example.flamemathnew.back.mathobject.MathObject
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.matrix.VectorHelper.Companion.createVectorStr
import com.example.flamemathnew.back.numbers.FractionalNumb
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.parameters.SLE
import com.example.flamemathnew.back.point.Point
import com.example.flamemathnew.back.support.Support.Companion.createRectangleArrayList
import com.example.flamemathnew.back.support.Support.Companion.createSingleArrayList
import com.example.flamemathnew.mid.exceptions.*

class AugmentedMatrix : Matrix {
    protected var augmented_n = 0
    lateinit var augmented_arr: MutableList<MutableList<Ring>>
    constructor(arr: MutableList<MutableList<Ring>>, _augmented_arr: MutableList<MutableList<Ring>>) : super(arr) {
        val augmented_m = _augmented_arr.size
        if (augmented_m == m) {
            if (augmented_m > 0) augmented_n = _augmented_arr[0].size else augmented_n = 0
            augmented_arr = createRectangleArrayList<Ring>({createNumb(0.0)}, augmented_m, augmented_n)
            for (i in 0 until augmented_m) for (j in 0 until augmented_n) augmented_arr[i][j] = _augmented_arr[i][j]
        } else throw MatrixDimensionMismatchException()
    }
    constructor(left: Matrix, right: Matrix) : super(left.arr) {
        if (left.m == right.m) {
            val _arr : MutableList<MutableList<Ring>> = left.arr
            val _augmented_arr : MutableList<MutableList<Ring>> = right.arr
            val temp = AugmentedMatrix(_arr, _augmented_arr)
            augmented_arr = temp.augmented_arr
            augmented_n = temp.n
        } else throw MatrixDimensionMismatchException()
    }

    override fun summStrings(a: Int, b: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] + augmented_arr[b][i] * k
        super.summStrings(a, b, k)
    }

    public fun get_augmentation(): Matrix {
        return Matrix(augmented_arr)
    }

    public fun get_main(): Matrix {
        return Matrix(arr)
    }

    public override fun toString(): String {
        var decode = ""
        for (i in 0 until m) {
            for (j in 0 until n) decode += arr.get(i).get(j).toString() + " "
            decode += " | "
            for (j in 0 until augmented_n) decode += augmented_arr[i][j].toString() + " "
            decode += "\n"
        }
        return decode
    }

    override fun mult_string(a: Int, k: Ring, save_det : Boolean) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] * k
        super.mult_string(a, k, save_det)
    }

    override fun div_string(a: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] / k
        super.div_string(a, k)
    }


    override fun rank(): Int {
        val temp_arr = createRectangleArrayList<Ring>({ FractionalNumb(0.0) }, m, n+augmented_n)
        for (i in 0 until m) for (j in 0 until n + augmented_n) {
            if (j < n) temp_arr[i][j] = arr[i][j] else temp_arr[i][j] = augmented_arr[i][j - n]
        }
        val temp = Matrix(temp_arr)
        return temp.rank()
    }

    override fun isNullString(a: Int): Boolean {
        var augmented_check = true
        return if (0 <= a && a < m) {
            for (i in 0 until augmented_n) {
                if (!augmented_arr[a][i].equals( 0.0)) {
                    augmented_check = false
                    break
                }
            }
            if (augmented_check) super.isNullString(a) else false
        } else throw InvalidNumberStringException()
    }

    override fun deleteString(a: Int) {
        val temp = createRectangleArrayList<Ring>({ createNumb(0.0) }, m-1, augmented_n)
        for (i in 0 until a) for (j in 0 until augmented_n) temp[i][j] = augmented_arr[i][j]
        for (i in a + 1 until m) for (j in 0 until augmented_n) temp[i - 1][j] = augmented_arr[i][j]
        augmented_arr = temp
        super.deleteString(a)
    }

    protected fun reset_augmented() {
        augmented_arr = createRectangleArrayList({ createNumb(0.0) }, m, 1)
    }

    protected fun is_homogeneous(): Boolean {
        if (augmented_n > 1) return false
        for (i in 0 until m) if (!augmented_arr[i][0].equals( 0.0)) return false
        return true
    }

    fun substituion(array: MutableList<Ring>): Matrix {
        if (n - m == array.size && augmented_n == 1) {
            if (isSingle()) {
                val cof : MutableList<Ring> = createSingleArrayList<Ring> ({createNumb(0.0)}, n)
                for (i in m until n) cof[i] = array[i - m]
                for (i in 0 until m) {
                    var temp1 = "x" + (i + 1) + " = "
                    var temp2 = temp1
                    for (j in m until n) {
                        //если и коэффициент в матрице ненулевой и подставляется не ноль у соответствующей переменной
                        if (!arr[i][j].equals( 0.0)&& !cof[j].equals( 0.0)) {
                            temp1 += "-a" + (i + 1) + (j + 1) + " * " + "x" + (j + 1) + " + "
                            temp2 += (-arr[i][j]).toString() + " * " + cof[j] + " + "
                            cof[i] += -arr[i][j] * cof[j]
                        }
                    }
                    if (!augmented_arr[i][0].equals( 0.0)) {
                        temp1 += "b" + (i + 1)
                        temp2 += augmented_arr[i][0]
                        cof[i] = cof[i] + augmented_arr[i][0]
                    } else {
                        temp1 = temp1.substring(0, temp1.length - 3)
                        temp2 = temp2.substring(0, temp2.length - 3)
                    }
                    commit(temp1, Tag.SOLUTION)
                    commit(temp2, Tag.SOLUTION)
                    commit("x" + (i + 1) + " = " + cof[i], Tag.SOLUTION)
                }
                val answer = createVectorStr(cof)
                answer.log_this(Tag.ANSWER)
                return answer
            } else throw NonSingleException()
        } else throw MatrixDimensionMismatchException()
    }

    fun solve_system(): MathObject {
        return if (augmented_n == 1) {
            val copy : AugmentedMatrix = AugmentedMatrix(arr, augmented_arr)
            if (copy.m == copy.n && com.example.flamemathnew.mid.settings.matrix.SLE.method === SLE.KRAMER_RULE) {
                TODO("")
            } else {
                commit("Решим систему методом Гаусса", Tag.METHOD_DESCRIPTION)
                copy.gauss_transformation()
                copy.reduce_null_strings()
                if (copy.m == copy.n) {
                    val answer : MutableList<Ring> = createSingleArrayList<Ring>({createNumb(0.0)}, copy.n)
                    for (i in 0 until copy.n) if (copy.arr[i][i].equals(1.0)) answer[i] = copy.augmented_arr[0][i] else throw HaveNotSolutionsException()
                    return Point(answer)
                }
                else if (copy.m < copy.n){
                    val base = createSingleArrayList<Matrix>({Matrix(1)}, copy.n-copy.m)
                    if (is_homogeneous()) {
                        commit(
                            "Так как СЛАУ является однородной и прямоугольной, то она задаёт линейное подпространство(оболочку). Найдём базис.",
                            Tag.PROCEEDING
                        )
                        for (i in 0 until copy.n - copy.m) {
                            val cords_vector  : MutableList<Ring> = createSingleArrayList({createNumb(0.0)},copy.n - copy.m)
                            cords_vector[i] = createNumb(1.0)
                            for (j in copy.m until copy.n) commit("x" + (j + 1) + " = " + cords_vector[j],
                                Tag.SOLUTION)
                            for (j in 0 until copy.m) {
                                base[i] = copy.substituion(cords_vector)
                                base[i].log_this("")
                            }
                        }
                        return LinearSpace(base)
                    }
                    else {
                        commit(
                            "Так как СЛАУ является неоднородной и прямоугольной, то она задаёт линейное многообразие. Найдём базис.",
                            Tag.PROCEEDING
                        )
                        commit("Найдём частное решение", Tag.PROCEEDING)
                        val v = copy.substituion(createSingleArrayList({ createNumb(0.0) }, copy.n - copy.m))
                        v.log_this("Это вектор, на который перенесёно линейное подпространство")
                        copy.reset_augmented()
                        copy.log_this()
                        commit("Найдём базис соответствующего пространства. Для этого обнулим столбец свободных членов.",
                            Tag.PROCEEDING)
                        for (i in 0 until copy.n - copy.m) {
                            val cords_vector = createSingleArrayList<Ring>({ createNumb(0.0) },copy.n - copy.m)
                            cords_vector[i] = createNumb(1.0)
                            for (j in copy.m until copy.n) commit("x" + (j + 1) + " = " + cords_vector[j - copy.m],
                                Tag.SOLUTION)
                            for (j in 0 until copy.m) {
                                base[i] = copy.substituion(cords_vector)
                                base[i].log_this("")
                            }
                        }
                        var temp : AffineSpace? = null
                        try {
                             temp = AffineSpace(v, base)
                        }
                        catch (matrix_error : MatrixErrorException){
                            println("Этого не должно было произойти")
                        }
                        return temp!!
                    }
                }
                else {
                    copy.log_this("Так как строк больше, чем столбцов и сократить ничего нельзя, то система не имеет решений.")
                    throw HaveNotSolutionsException()
                }
            }
        } else throw MatrixDimensionMismatchException()
    }

}