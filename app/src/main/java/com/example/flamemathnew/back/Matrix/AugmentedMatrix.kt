package Matrix

import AffineSpace.AffineSpace
import MathObject.Ring
import Number.FractionalNumb
import LinearSpace.LinearSpace
import Logger.Log.commit
import Logger.Tag.*
import MRV.Computer
import MRV.Computer.HAVE_NOT_SOLUTIONS
import MRV.Computer.INVALID_NUMBER_STRING
import MRV.Computer.MATRIX_DIMENSION_MISSMATCH
import MRV.Computer.NON_SINGLE
import MathObject.MathObject.MathObject
import Number.createNumb
import Parameters.SLE
import Point.Point
import Support.createRectangleArrayList
import Support.createSingleArrayList
import com.example.flamemathnew.mid.Settings

class AugmentedMatrix : Matrix {
    protected var augmented_n = 0
    lateinit var augmented_arr: ArrayList<ArrayList<Ring>>
    constructor(arr: ArrayList<ArrayList<Ring>>, _augmented_arr: ArrayList<ArrayList<Ring>>) : super(arr) {
        val augmented_m = _augmented_arr.size
        if (augmented_m == m) {
            if (augmented_m > 0) augmented_n = _augmented_arr[0].size else augmented_n = 0
            augmented_arr = createRectangleArrayList<Ring>({createNumb(0.0)}, augmented_m, augmented_n)
            for (i in 0 until augmented_m) for (j in 0 until augmented_n) augmented_arr[i][j] = _augmented_arr[i][j]
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }
    constructor(left: Matrix, right: Matrix) : super(left.arr) {
        if (left.m == right.m) {
            val _arr : ArrayList<ArrayList<Ring>> = left.arr
            val _augmented_arr : ArrayList<ArrayList<Ring>> = right.arr
            val temp = AugmentedMatrix(_arr, _augmented_arr)
            augmented_arr = temp.augmented_arr
            augmented_n = temp.n
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun summ_strings(a: Int, b: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] + augmented_arr[b][i] * k
        super.summ_strings(a, b, k)
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

    @Throws(INVALID_NUMBER_STRING::class)
    override fun mult_string(a: Int, k: Ring, save_det : Boolean) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] * k
        super.mult_string(a, k, save_det)
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun div_string(a: Int, k: Ring) {
        for (i in 0 until augmented_n) augmented_arr[a][i] = augmented_arr[a][i] / k
        super.div_string(a, k)
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun rank(): Int {
        val temp_arr = createRectangleArrayList<Ring>({FractionalNumb(0.0)}, m, n+augmented_n)
        for (i in 0 until m) for (j in 0 until n + augmented_n) {
            if (j < n) temp_arr[i][j] = arr[i][j] else temp_arr[i][j] = augmented_arr[i][j - n]
        }
        val temp = Matrix(temp_arr)
        return temp.rank()
    }

    @Throws(INVALID_NUMBER_STRING::class)
    override fun is_null_string(a: Int): Boolean {
        var augmented_check = true
        return if (0 <= a && a < m) {
            for (i in 0 until augmented_n) {
                if (!augmented_arr[a][i].equals( 0.0)) {
                    augmented_check = false
                    break
                }
            }
            if (augmented_check) super.is_null_string(a) else false
        } else throw INVALID_NUMBER_STRING()
    }

    override fun delete_string(a: Int) {
        val temp = createRectangleArrayList<Ring>({ createNumb(0.0) }, m-1, augmented_n)
        for (i in 0 until a) for (j in 0 until augmented_n) temp[i][j] = augmented_arr[i][j]
        for (i in a + 1 until m) for (j in 0 until augmented_n) temp[i - 1][j] = augmented_arr[i][j]
        augmented_arr = temp
        super.delete_string(a)
    }

    protected fun reset_augmented() {
        augmented_arr = createRectangleArrayList({ createNumb(0.0) }, m, 1)
    }

    protected fun is_homogeneous(): Boolean {
        if (augmented_n > 1) return false
        for (i in 0 until m) if (!augmented_arr[i][0].equals( 0.0)) return false
        return true
    }

    @Throws(MATRIX_DIMENSION_MISSMATCH::class, NON_SINGLE::class)
    fun substituion(array: ArrayList<Ring>): Matrix {
        if (n - m == array.size && augmented_n == 1) {
            if (is_single()) {
                val cof : ArrayList<Ring> = createSingleArrayList<Ring> ({createNumb(0.0)}, n)
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
                    commit(temp1, SOLUTION)
                    commit(temp2,SOLUTION)
                    commit("x" + (i + 1) + " = " + cof[i], SOLUTION)
                }
                val answer = create_vector_str(cof)
                answer.log_this(ANSWER)
                return answer
            } else throw NON_SINGLE()
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

    @Throws(
        MATRIX_DIMENSION_MISSMATCH::class,
        INVALID_NUMBER_STRING::class,
        HAVE_NOT_SOLUTIONS::class,
        NON_SINGLE::class,
    )
    fun solve_system():MathObject {
        return if (augmented_n == 1) {
            val copy : AugmentedMatrix = AugmentedMatrix(arr, augmented_arr)
            if (copy.m == copy.n && Settings.matrix.SLE.method === SLE.KRAMER_RULE) {
                TODO("")
            } else {
                commit("Решим систему методом Гаусса", METHOD_DESCRIPTION)
                copy.gauss_transformation()
                copy.reduce_null_strings()
                if (copy.m == copy.n) {
                    val answer : ArrayList<Ring> = createSingleArrayList<Ring>({createNumb(0.0)}, copy.n)
                    for (i in 0 until copy.n) if (copy.arr[i][i].equals(1.0)) answer[i] = copy.augmented_arr[0][i] else throw HAVE_NOT_SOLUTIONS()
                    return Point(answer)
                }
                else if (copy.m < copy.n){
                    val base = createSingleArrayList<Matrix>({Matrix(1)}, copy.n-copy.m)
                    if (is_homogeneous()) {
                        commit(
                            "Так как СЛАУ является однородной и прямоугольной, то она задаёт линейное подпространство(оболочку). Найдём базис.",
                            PROCEEDING
                        )
                        for (i in 0 until copy.n - copy.m) {
                            val cords_vector  : ArrayList<Ring> = createSingleArrayList({createNumb(0.0)},copy.n - copy.m)
                            cords_vector[i] = createNumb(1.0)
                            for (j in copy.m until copy.n) commit("x" + (j + 1) + " = " + cords_vector[j], SOLUTION)
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
                            PROCEEDING
                        )
                        commit("Найдём частное решение", PROCEEDING)
                        val v = copy.substituion(createSingleArrayList({ createNumb(0.0) }, copy.n - copy.m))
                        v.log_this("Это вектор, на который перенесёно линейное подпространство")
                        copy.reset_augmented()
                        copy.log_this()
                        commit("Найдём базис соответствующего пространства. Для этого обнулим столбец свободных членов.", PROCEEDING)
                        for (i in 0 until copy.n - copy.m) {
                            val cords_vector = createSingleArrayList<Ring>({ createNumb(0.0) },copy.n - copy.m)
                            cords_vector[i] = createNumb(1.0)
                            for (j in copy.m until copy.n) commit("x" + (j + 1) + " = " + cords_vector[j - copy.m], SOLUTION)
                            for (j in 0 until copy.m) {
                                base[i] = copy.substituion(cords_vector)
                                base[i].log_this("")
                            }
                        }
                        var temp : AffineSpace? = null
                        try {
                             temp = AffineSpace(v, base)
                        }
                        catch (matrix_error : Computer.MATRIX_ERROR){
                            println("Этого не должно было произойти")
                        }
                        return temp!!
                    }
                }
                else {
                    copy.log_this("Так как строк больше, чем столбцов и сократить ничего нельзя, то система не имеет решений.")
                    throw HAVE_NOT_SOLUTIONS()
                }
            }
        } else throw MATRIX_DIMENSION_MISSMATCH()
    }

}