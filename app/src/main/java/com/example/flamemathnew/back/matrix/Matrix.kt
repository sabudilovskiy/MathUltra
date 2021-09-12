package com.example.flamemathnew.back.matrix


import com.example.flamemathnew.back.logger.Log.commit
import com.example.flamemathnew.back.logger.Tag
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.numbers.DecNumb
import com.example.flamemathnew.back.numbers.FractionalNumb
import com.example.flamemathnew.back.numbers.Numb
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.parameters.Det
import com.example.flamemathnew.back.parameters.Inverse
import com.example.flamemathnew.back.parameters.Rank
import com.example.flamemathnew.back.polynom.Polynom
import com.example.flamemathnew.back.support.Support.Companion.CTI
import com.example.flamemathnew.back.support.Support.Companion.createRectangleArrayList
import com.example.flamemathnew.back.support.Support.Companion.createSingleArrayList
import com.example.flamemathnew.mid.exceptions.*
import com.example.flamemathnew.mid.settings.matrix.Inverse.method
import com.example.flamemathnew.mid.settings.matrix.Rank.method

//import java.util.*


open class Matrix : Ring {
    protected var cof_det : Ring = createNumb(1.0)
    var m = 0
    var n = 0
    var arr: MutableList<MutableList<Ring>>;

    constructor(_arr: MutableList<MutableList<Ring>>) {
        m = _arr.size
        if (m > 0) n = _arr[0].size else n = 0
        arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, m, n)
        for (i in 0 until m) for (j in 0 until n) arr[i][j] = _arr[i][j]
    }

    //создаёт единичную матрицу n на n
    constructor(n: Int) {
        arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, n, n)
        m = n
        this.n = n
        for (i in 0 until n) for (j in 0 until n) if (i == j) arr[i][j] = createNumb(1.0)
    }


    //прибавляем к a b
    protected open fun summStrings(a: Int, b: Int, k: Ring) {
        if (0 <= a && a < m && 0 <= b && b < m) {
            for (i in 0 until n) arr[a][i] = arr[a][i] + arr[b][i] * k
            log_this("Прибавляем к " + (a + 1) + " строке " + (b + 1) + " строку, умноженную на " + k)
        } else throw InvalidNumberStringException()
    }

    protected open fun isNullString(a: Int): Boolean {
        if (0 <= a && a < m) {
            for (i in 0 until n) if (!arr[a][i].equals(0.0)) return false
            return true
        } else throw InvalidNumberStringException()
    }
    protected fun swapStrings(a: Int, b: Int) {
        if (0 <= a && a < m && 0 <= b && b < m) {
            cof_det = cof_det * createNumb(-1.0)
            for (i in 0 until n) {
                val first = arr[a][i]
                val second = arr[b][i]
                arr[a][i] = second
                arr[b][i] = first
            }
        } else throw InvalidNumberStringException()
    }

    protected open fun deleteString(a: Int) {
        if (0 <= a && a < m) {
            val temp = createRectangleArrayList<Ring>({ createNumb(0.0) }, m - 1, n)
            for (i in 0 until a) for (j in 0 until n) temp[i][j] = arr[i][j]
            for (i in a + 1 until m) for (j in 0 until n) temp[i - 1][j] = arr[i][j]
            arr = temp
            m--
        }
    }

    protected fun deleteColumn(a: Int) {
        if (0 <= a && a < n) {
            val zero = createNumb(0.0)
            val temp = createRectangleArrayList<Ring>({ createNumb(0.0) }, m, n - 1)
            for (i in 0 until m) for (j in 0 until a) temp[i][j] = arr[i][j]
            for (i in 0 until m) for (j in a + 1 until n) temp[i][j - 1] = arr[i][j]
            arr = temp
            n--
        } else throw InvalidNumberStringException()
    }

    protected fun moveStringToEnd(a: Int) {
        if (0 <= a && a < m) {
            for (i in a until m - 1) {
                swapStrings(i, i + 1)
            }
        } else throw InvalidNumberStringException()
    }

    protected fun find_non_zero_in_column(column: Int, start: Int): Int {
        return if (0 <= column && column < n) {
            for (i in start until m) {
                if (!arr[i][column].equals(0.0)) return i
            }
            -1
        } else throw InvalidNumberStringException()
    }

    protected open fun mult_string(a: Int, k: Ring, save_det : Boolean = true) {
        if (0 <= a && a < m) {
            for (i in 0 until n) arr[a][i] = arr[a][i] * k
            if (save_det) cof_det = cof_det / k
        } else throw InvalidNumberStringException()
    }

    protected open fun div_string(a: Int, k: Ring) {
        if (0 <= a && a < m) {
            for (i in 0 until n) arr[a][i] = arr[a][i] / k
            cof_det = cof_det * k
        } else throw InvalidNumberStringException()
    }

    fun gauss_transformation() {
        log_this("Для того, чтобы преобразовать матрицу методом Гаусса необходимо сначала сделать матрицу верхнетреугольной.")
        triangular_transformation()
        log_this("Мы добились того, что под главной диагональю одни нули. Осталось добиться того же над главной диагональю, где это возможно")
        var i : Int
        if (m > n) i = n - 1
        else i = m - 1
        while (i>0) {
            val f = find_non_zero_in_column(i, 0)
            if (!arr[i][i].equals(0.0) && f != i && f != -1) {
                for (j in i - 1 downTo 0) {
                    commit(
                        "k" + j + "= -a" + CTI(j) + CTI(i)+ " / a" + CTI(i) + CTI(i) + "=" + -arr[j][i] + " / " + arr[i][i] + " = " + (-arr[j][i] / arr[i][i]),
                        Tag.SOLUTION
                    )
                    val k = -arr[j][i] / arr[i][i]
                    summStrings(j, i, k)
                }
            } else if (f == i || f == -1) {
                commit("Так как " + (i + 1) + " столбец выше главной диагонали уже обнулён, то ничего не делаем",
                    Tag.SKIPPED)
            }
            i--
        }
        log_this("Теперь и над, и под главной диагональю нули. Осталось добиться того, чтобы на главной диагонали были только единицы.")
        for (i in 0 until m) if (!arr[i][i].equals(0.0) && !arr[i][i].equals(1.0)) {
            log_this("Делим " + (i + 1) + " строку на " + arr[i][i].toString())
            div_string(i, arr[i][i])
        }
    }

    //проверяет, является ли матрица единичной в главной части(квадратной подматрице)
    protected fun isSingle(): Boolean {
        val one = createNumb(1.0)
        val zero = createNumb(0.0)
        for (i in 0 until m) for (j in 0 until m) if (arr[i][j] != (if (i == j) one else zero)) return false
        return true
    }

    fun triangular_transformation() {
        var local_m = m
        var local_n = n
        log_this("Для того, чтобы привести матрицу к верхнетреугольному(трапециевидному) виду необходимо постепенно, столбец за столбцом путём вычитания верхних строк из нижних добиваться появления нулей под главной диагональю.")
        var i = 0
        while (i < local_m - 1 && i < local_n - 1) {
            val f = find_non_zero_in_column(i, i + 1)
            if (!arr[i][i].equals(0.0) && f != -1) {
                for (j in i + 1 until m) {
                    commit(
                        "k" + (j + 1) + "= -a" + CTI(j + 1) + CTI(i + 1) + " / a" + CTI(i + 1) + CTI(i + 1) + "=" + -arr[j][i] + " / " + arr[i][i] + " = " + -arr[j][i] / arr[i][i],
                        Tag.SOLUTION
                    )
                    val k = -arr[j][i] / arr[i][i]
                    summStrings(j, i, k)
                }
            } else if (f == -1) {
                commit("Так как " + (i + 1) + " столбец ниже главной диагонали уже обнулён, то ничего не делаем",
                    Tag.SKIPPED)
            } else {
                if (isNullString(i)) {
                    moveStringToEnd(i)
                    log_this("Получилось, что " + (i + 1) + " строка является нулевой. Смещаем её вниз.")
                    i--
                    local_m--
                    local_n--
                } else {
                    swapStrings(i, f)
                    log_this("Так как " + (i + 1) + " строчка имеет ноль на главной диагонали, то мы её поменяли с нижней, у которой нет нуля на главной диагонали")
                }
            }
            i++
        }
    }
    protected fun complement_minor(str: Int, col: Int): Matrix {
        return if (0 <= str && str < m && 0 <= col && col < n) {
            val copy = Matrix(arr)
            copy.deleteString(str)
            copy.deleteColumn(col)
            copy
        } else throw InvalidNumberStringException()
    }

    protected fun complement_minor(str: IntArray, col: IntArray): Matrix {
        return if (m == n && str.size == col.size) {
            var k = 0
            var a = 0
            var b = 0
            val m_new = m - str.size
            val temp_arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m_new, m_new)
            while (k < m_new * m_new) {
                var crossed_out = false
                for (j in str) if (a == j) {
                    crossed_out = true
                    break
                }
                if (!crossed_out) {
                    for (j in col) if (a == j) {
                        crossed_out = true
                        break
                    }
                    if (!crossed_out) {
                        temp_arr[k / m_new][k % m_new] = arr[a++][b++]
                        k++
                    }
                }
            }
            Matrix(temp_arr)
        } else throw MatrixDimensionMismatchException()
    }

    protected fun minor(str: MutableList<Int>, col: MutableList<Int>): Matrix {
        return if (str.size == col.size) {
            var k = 0
            val m_new = str.size
            val temp_arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m_new, m_new)
            var i = 0
            while (i < m && k < m_new * m_new) {
                var j = 0
                while (j < n && k < m_new * m_new) {
                    if (str.contains(i) && col.contains(j)) {
                        temp_arr[k / m_new][k % m_new] = arr[i][j]
                        k++
                    }
                    j++
                }
                i++
            }
            Matrix(temp_arr)
        } else throw MatrixDimensionMismatchException()
    }

    protected fun reduce_null_strings() {
        for (i in m - 1 downTo 0) if (isNullString(i)) {
            deleteString(i)
            log_this("Вычёркиваем " + (i + 1) + " строку")
        }
    }

    protected fun count_null_in_string(a: Int): Int {
        return if (0 <= a && a < m) {
            var count = 0
            val zero = createNumb(0.0)
            for (i in 0 until n) if (arr[a][i] == zero) count++
            count
        } else throw InvalidNumberStringException()
    }

    protected fun count_null_in_column(a: Int): Int {
        return if (0 <= a && a < m) {
            var count = 0
            val zero = createNumb(0.0)
            for (i in 0 until m) if (arr[i][a] == zero) count++
            count
        } else throw InvalidNumberStringException()
    }

    //возвращает номер строки, в которой наибольшее количество нулей и их количество
    protected fun find_most_null_string(): IntArray {
        val max = IntArray(2)
        for (i in 0 until m) if (count_null_in_string(i) > max[1]) {
            max[0] = i
            max[1] = count_null_in_string(i)
        }
        return max
    }

    //возвращает номер столбца, в котором наибольшее количество нулей и их количество
    protected open fun find_most_null_column(): IntArray {
        val max = IntArray(2)
        for (i in 0 until n) if (count_null_in_column(i) > max[1]) {
            max[0] = i
            max[1] = count_null_in_column(i)
        }
        return max
    }

    protected fun algebraic_complement(a: Int, b: Int): Ring {
        if (0 <= a && a < m && 0 <= b && b < n) {
            commit(
                "Для вычисления алгебраического дополнения необходимо умножить -1 в степени суммы индексов элемента на его минор.",
                Tag.BASE_RULES
            )
            commit("Найдём A" + CTI(a + 1) + CTI(b + 1), Tag.PROCEEDING)
            val minor = complement_minor(a, b)
            commit(
                "Получаем минор вычеркнув " + (a + 1) + " строку и " + (b + 1) + " столбец. Вычислим его определитель.",
                Tag.PROCEEDING
            )
            val minor_determinant = minor.determinant()
            val value = minor_determinant * createNumb(Math.pow(-1.0, (a + b).toDouble()))
            commit(
                "A" + (a + 1) + (b + 1) + " = " + minor_determinant + "*" + "-1^(" + (a + 1) + "+" + (b + 1) + ") = " + value,
                Tag.SOLUTION
            )
            return value
        } else throw InvalidNumberStringException()
    }

    public fun determinant(): Ring {
        if (m == n) {
            try {
                val cur_method: Det = com.example.flamemathnew.mid.settings.matrix.Det.getCurMethod(m);
                return when (cur_method) {
                    Det.BASIC -> det_with_basic_rules();
                    Det.LAPLASS -> det_with_laplass();
                    Det.SARUSS -> det_with_saruss_rule();
                    Det.TRIANGLE -> det_with_triangle();
                };
            } catch (ignored: MatrixDimensionMismatchException) {
                return createNumb(0.0)
            }
        } else throw NonQuadraticMatrixException();
    }

    fun det_with_basic_rules(): Ring {
        val det: Ring
        return if (m == n) {
            if (m == 1) {
                log_this()
                commit("Определитель матрицы из одного элемента равен этому элеменету.",
                    Tag.METHOD_DESCRIPTION)
                commit("det = " + "a${CTI(1)}+${CTI(1)}" + " = " + arr[0][0], Tag.SOLUTION)
                det = arr[0][0]
            } else if (m == 2) {
                det = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0]
                log_this()
                commit("Определитель матрицы 2 на 2 равен произведению элементов на главной минус произведение элементов на побочной диагонали.",
                    Tag.METHOD_DESCRIPTION)
                commit(
                    "det = a${CTI(11)}*${CTI(22)} - a${CTI(12)}*a${CTI(21)} = " + arr[0][0] + "*" + arr[1][1] + "-" + arr[0][1] + "*" + arr[1][0] + " =  " + det,
                    Tag.SOLUTION
                )
            } else throw MatrixDimensionMismatchException()
            det
        } else throw NonQuadraticMatrixException()
    }

    fun det_with_saruss_rule(): Ring {
        return if (m == n && m == 3) {
            var det: Ring =
                arr[0][0] * arr[1][1] * arr[2][2] + arr[0][1] * arr[1][2] * arr[2][0] + arr[0][2] * arr[1][0] * arr[2][1]
            det =
                det - arr[2][0] * arr[1][1] * arr[0][2] - arr[1][0] * arr[0][1] * arr[2][2] - arr[0][0] * arr[2][1] * arr[1][2]
            log_this()
            commit("Определитель матрицы 3 на 3 можно вычислить используя правило треугольника или способ Саррюса. ",
                Tag.METHOD_DESCRIPTION)
            commit("det = a${CTI(11)}*a${CTI(22)}*a${CTI(33)} + a${CTI(12)}*a${CTI(23)}*a${CTI(31)} + a${CTI(13)}*a${CTI(21)}*a${CTI(32)} - a${CTI(31)}*a${CTI(22)}*a${CTI(13)} - a${CTI(21)}*a${CTI(12)}*a${CTI(33)} - a${CTI(11)}*a${CTI(32)}*a${CTI(23)} =",
                Tag.METHOD_RULES)
            commit(
                "= " + arr[0][0] + "*" + arr[1][1] + "*" + arr[2][2] + " " + arr[0][1] + "*" + arr[1][2] + "*" + arr[2][0] + " " + arr[0][2] + "*" + arr[1][0] + "*" + arr[2][1] + " -(" + arr[2][0] + "*" + arr[1][1] + "*" + arr[0][2] + " " + arr[1][0] + "*" + arr[0][1] + "*" + arr[2][2] + " " + arr[0][0] + "*" + arr[2][1] + "*" + arr[1][2] + ") =",
                Tag.SOLUTION
            )
            commit(
                "= " + arr[0][0] * arr[1][1] * arr[2][2] + " + " + arr[0][1] * arr[1][2] * arr[2][0] + " + " + arr[0][2] * arr[1][0] * arr[2][1] + " + " + "-(" + arr[2][0] * arr[1][1] * arr[0][2] + " + " + arr[1][0] * arr[0][1] * arr[2][2] + " + " + arr[0][0] * arr[2][1] * arr[1][2] + ")= " + det,
                Tag.SOLUTION
            )
            commit("= " + det, Tag.SOLUTION)
            det
        } else throw MatrixDimensionMismatchException()
    }

    fun det_with_triangle(): Ring {
        val copy = Matrix(arr)
        copy.triangular_transformation()
        var det : Ring = createNumb(1.0)
        var temp = "det = "
        var temp2 = "det = "
        for (i in 0 until m) {
            det = det * copy.arr[i][i]
            temp += "a" + (i + 1) + (i + 1) + " * "
            temp2 += copy.arr[i][i].toString() + " * "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        commit(temp, Tag.SOLUTION)
        commit(
            "Так как матрица треугольного вида, то определитель равен произведению элементов на главной диагонали.",
            Tag.METHOD_DESCRIPTION
        )
        commit(temp2, Tag.SOLUTION)
        commit("det = $det", Tag.SOLUTION)
        return det
    }

    fun det_with_laplass(): Ring {
        if (m>1){
            val str = find_most_null_string()
            val col = find_most_null_column()
            if (str[1] >= col[1]) {
                return decompositonWithStr(str[0])
            } else {
                return decompositonWithCol(col[0])
            }
        }
        else return arr[0][0]

    }

    fun decompositonWithStr(str: Int): Ring {
        var det : Ring = createNumb(0.0)
        val A = createSingleArrayList<Ring>({ createNumb(0.0) }, m) //массив алгебраических дополнений
        log_this()
        commit("Для подсчёта определителя будем использовать разложение в строку.",
            Tag.METHOD_DESCRIPTION)
        commit(" Раскладываем по " + (str + 1) + " строке.", Tag.PROCEEDING)
        for (i in 0 until n) {
            if (arr[str][i].equals(0.0)) {
                commit(
                    "Так как a" + CTI(str + 1) + CTI(i + 1) + " равно нулю, то считать A" + CTI(str + 1) + CTI(i + 1) + " нет необходимости.",
                    Tag.SKIPPED
                )
                A[i] = createNumb(0.0)
            } else A[i] = algebraic_complement(str, i)
        }
        var temp = "det = "
        var temp2 = "det = "
        var temp3 = "det ="
        for (i in 0 until n) {
            det += A[i] * arr[str][i]
            temp += "A" + CTI(str + 1) + CTI(i + 1) + "*" + "a" + CTI(str + 1) + CTI(i + 1) + " + "
            temp2 += " " + A[i] + "*" + arr[str][i] + " + "
            temp3 += (A[i] * arr[str][i]).toString() + " + "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        temp3 = temp3.substring(0, temp3.length - 3)
        commit("Для того, чтобы посчитать определитель при помощи строчки, надо вычислить сумму произведений элементов на их алгебраические дополнения.",
            Tag.METHOD_DESCRIPTION)
        commit(temp, Tag.METHOD_RULES)
        commit(temp2, Tag.SOLUTION)
        commit(temp3, Tag.SOLUTION)
        commit("det = $det", Tag.ANSWER)
        return det
    }

    fun decompositonWithCol(col: Int): Ring {
        var det : Ring = createNumb(0.0)
        val A = createSingleArrayList<Ring>({ createNumb(0.0) }, m) //массив алгебраических дополнений
        log_this()
        commit("Для подсчёта определителя будем использовать разложение в столбец.",
            Tag.METHOD_DESCRIPTION)
        commit("Раскладываем по " + (col + 1) + " столбцу.", Tag.PROCEEDING)
        for (i in 0 until n) {
            if (arr[i][col].equals(0.0)) {
                A[i] = createNumb(0.0)
                commit(
                    "Так как a" + CTI(i + 1) + CTI(col + 1) + " равно нулю, то считать A" + CTI(i + 1) + CTI(col + 1) + " нет необходимости.",
                    Tag.SKIPPED
                )
            } else A[i] = algebraic_complement(i, col)
        }
        var temp = "det = "
        var temp2 = "det = "
        var temp3 = "det ="
        for (i in 0 until n) {
            det += A[i] * arr[i][col]
            temp += "A" + CTI(i + 1) + CTI(col + 1) + "*" + "a" + CTI(i + 1) + CTI(col + 1) + " + "
            temp2 += " " + A[i] + "*" + arr[i][col] + " + "
            temp3 += (A[i] * arr[i][col]).toString() + " + "
        }
        temp = temp.substring(0, temp.length - 3)
        temp2 = temp2.substring(0, temp2.length - 3)
        temp3 = temp3.substring(0, temp3.length - 3)
        commit("Для того, чтобы посчитать определитель при помощи столбца, надо вычислить сумму произведений элементов на их алгебраические дополнения.",
            Tag.METHOD_DESCRIPTION)
        commit(temp, Tag.METHOD_RULES)
        commit(temp2, Tag.SOLUTION)
        commit(temp3, Tag.SOLUTION)
        commit("det = $det", Tag.ANSWER)
        return det
    }

    fun print() {
        for (i in 0 until m) {
            var temp = ""
            for (j in 0 until n) temp = temp + arr[i][j] + " "
            println(temp)
        }
    }

    fun rankWithTriangle(): Int {
        val copy = Matrix(arr)
        commit("Ранг равен количеству ненулевых элементов на главной диагонали после приведения к трапецевидному виду",
            Tag.METHOD_DESCRIPTION)
        copy.triangular_transformation()
        var count = 0
        for (i in 0 until m) {
            if (!copy.arr[i][i].equals(0.0)) count++
        }
        commit("Rank = $count", Tag.ANSWER)
        return count
    }

    fun rankWithMinors(): Int {
        log_this()
        commit("Для нахождения ранга методом окламляющих миноров необходимо найти ненулевой элемент. Если его нет, то ранк равен нулю",
            Tag.METHOD_DESCRIPTION)
        commit("Затем необходимо проверить все миноры, которые включают его. Если найдётся ненулевой минор, то ранк равен двум. Проверяем все миноры, включающие его.",
            Tag.METHOD_DESCRIPTION)
        commit("Если находим ненулевой минор, то ранк равен его размеру. Продолжаем, пока либо не закончится матрица, либо не сможем найти ненулевой минор.",
            Tag.METHOD_DESCRIPTION)
        commit("Найдём ненулевой элемент", Tag.PROCEEDING)
        var a = 0
        var b = 0
        var found = false
        var i = 0
        while (i < m && !found) {
            var j = 0
            while (j < n && !found) {
                if (!arr[i][j].equals(0.0)) {
                    a = i
                    b = i
                    found = true
                }
                j++
            }
            i++
        }
        if (found){
            commit("a" + CTI(a + 1) + CTI(b + 1) + '\u2260' + " 0 ", Tag.SOLUTION)
            commit("Теперь рассмотрим все миноры, в которые входит данный элемент.", Tag.PROCEEDING)
            val temp_arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, 1, 1)
            var cur_minor = Matrix(temp_arr)
            var cur_str = MutableList<Int>(1) {i -> 0}
            var cur_col = MutableList<Int>(1) {i -> 0}
            cur_str[0] = a
            cur_col[0] = b
            found = true
            while (found && cur_minor.m < m && cur_minor.m < n) {
                found = false
                val temp_str = MutableList<Int>(cur_minor.m + 1) {i -> 0}
                val temp_col = MutableList<Int>(cur_minor.m + 1) {i -> 0}
                for (k in 0 until cur_minor.m) {
                    temp_str[k] = cur_str[k]
                    temp_col[k] = cur_col[k]
                }
                var i = 0
                while (i < m && !found) {
                    var j = 0
                    while (j < n && !found) {
                        if (!cur_col.contains(i) && !cur_str.contains(j)) {
                            temp_str[cur_minor.m] = i
                            temp_col[cur_minor.m] = j
                            val minor = minor(temp_str, temp_col)
                            minor.log_this("Проверим минор.")
                            var det : Ring = createNumb(0.0)
                            try {
                                det = minor.determinant()
                            } catch (ignored: NonQuadraticMatrixException) {
                            } catch (ignored: InvalidNumberStringException) {
                            }
                            if (det != createNumb(0.0)) {
                                cur_minor = minor
                                cur_minor.log_this()
                                commit("Так как этот минор не равен нулю, то теперь мы будем рассматривать миноры, которые включают его",
                                    Tag.PROCEEDING)
                                cur_str = temp_str
                                cur_col = temp_col
                                found = true
                            }
                        }
                        j++
                    }
                    i++
                }
            }
            cur_minor.log_this()
            commit("Этот минор является базисным", Tag.PROCEEDING)
            commit("Rank = ${cur_minor.m}", Tag.ANSWER)
            return cur_minor.m
        }
        else{
            commit("Так как в матрице нет ненулевых элементов, то ранк равен 0.", Tag.ANSWER)
            return 0
        }
    }

    fun transposition() {
        val new_arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, n, m)
        for (i in 0 until m) for (j in 0 until n) new_arr[j][i] = arr[i][j]
        arr = new_arr
        val temp = n
        n = m
        m = temp
    }

    open fun rank(): Int {
        return when(com.example.flamemathnew.mid.settings.matrix.Rank.method){
            Rank.BORDERING -> rankWithMinors()
            Rank.ELEMENTAL_ROW -> rankWithTriangle()
        }
    }

    fun getInverse() : Matrix {
        return when (com.example.flamemathnew.mid.settings.matrix.Inverse.method){
            Inverse.GAUSS -> getInverseGauss()
            Inverse.ALGEBRAIC_COMPLEMENT -> getInverseAlgebraicComplement()
        }
    }
    fun getInverseGauss(): Matrix {
        return if (m == n) {
            val single = Matrix(m)
            val copy = Matrix(arr)
            val temp = AugmentedMatrix(copy, single)
            temp.log_this()
            commit("Чтобы найти обратную матрицу методом Гаусса необходимо дописать справа от неё единичную и методом Гаусса добиться того, чтобы слева была единичная.",
                Tag.METHOD_DESCRIPTION)
            temp.gauss_transformation()
            if (temp.get_main().isSingle()) temp.get_augmentation() else throw DegenerateMatrixException()
        } else throw NonQuadraticMatrixException()
    }
    fun getInverseAlgebraicComplement() : Matrix {
        if (m == n) {
            var copy = Matrix(arr)
            copy.log_this()
            commit("Чтобы найти обратную матрицу необходимо найти её определитель, затем составить транспонированную матрицу из алгербраических дополнений элементов матрицы и после разделить её на определитель.",
                Tag.METHOD_DESCRIPTION)
            val det = copy.determinant()
            if (!det.equals(0.0)){
                //матрица из дополнений
                val _arr = createRectangleArrayList<Ring>({createNumb(0.0)}, m, n)
                for (i in 0 until n) for (j in 0 until n){
                    _arr[i][j] = copy.algebraic_complement(i, j)
                }
                copy = Matrix(_arr)
                commit("Составим матрицу из алгебраических дополнений", Tag.PROCEEDING)
                copy.log_this()
                commit("Транспонируем её", Tag.PROCEEDING)
                copy.transposition()
                copy.log_this()
                commit("Транспонировали. Разделим на определитель изначальной матрицы",
                    Tag.PROCEEDING)
                copy = (copy / det) as Matrix
                copy.log_this(Tag.ANSWER)
                return copy
            }
            else throw DegenerateMatrixException()
        } else throw NonQuadraticMatrixException()
    }
    fun findEigenvalues() : MutableList<Ring> {
        if (m == n){
            log_this()
            commit("Для нахождеия жордановой формы необходимо вычесть λ по главной диагонали, посчитать определитель и найти корни характеристического многочлена.",
                Tag.METHOD_DESCRIPTION)
            val temp_arr : MutableList<Ring> = mutableListOf(createNumb(0.0), createNumb(1.0))
            val lambda : Polynom = Polynom(temp_arr, "λ")
            val char_matrix : Matrix = (this - Matrix(m)*lambda) as Matrix
            char_matrix.log_this()
            commit("Вычли λ, необходимо найти характеристический многочлен", Tag.PROCEEDING)
            val char_polynom : Polynom = char_matrix.det_with_laplass() as Polynom
            commit("A(λ) = ${char_polynom.toString()}", Tag.SOLUTION)
            commit("Найдём его корни", Tag.PROCEEDING)
            val roots : MutableList<Ring> = char_polynom.solve()
            return roots
        }
        else throw NonQuadraticMatrixException()
    }
    fun findJordanForm() : Matrix{
        val roots = findEigenvalues()
        val eigenvalues : MutableList<Ring> = mutableListOf()
        val algebraicMultiplicity : MutableList<Int> = mutableListOf()
        val geometricMultiplicity : MutableList<Int> = mutableListOf()
        var i : Int = 1
        var j : Int = 1
        val char_matrixs : MutableList<Matrix> = mutableListOf()
        var temp_matrix  = Matrix(n)
        temp_matrix = (temp_matrix *eigenvalues[0]) as Matrix
        char_matrixs[0] = (this - temp_matrix) as Matrix
        eigenvalues.add(roots[0])
        var temp : String = "λ$i = ${roots[0]}"
        while (i < roots.size){
            if (roots[i] == roots[i-1]) j++
            else {
                temp+=", a${CTI(i)} = $j"
                algebraicMultiplicity.add(j)
                j = 1
                eigenvalues.add(roots[i])
                temp_matrix = (temp_matrix *eigenvalues[i]) as Matrix
                char_matrixs[i] = (this - temp_matrix) as Matrix
                temp = "λ${CTI(i)} = ${roots[i]}"
            }
            i++
        }
        i = 0
        temp+=", a${CTI(i)} = $j"
        algebraicMultiplicity.add(j)
        commit("Для вычисления геометрической кратности необходимо из изначальной матрицы вычесть собственное значение. Посчитать ранк получившейся матрицы и из размерности матрицы вычесть его.",
            Tag.METHOD_DESCRIPTION)
        while (i < eigenvalues.size){
            val rank_matrix = char_matrixs[i].rank()
            commit("r(A-E*λ${CTI(i)}) = $rank_matrix", Tag.SOLUTION)
            val cur_geometricMultiplicity : Int = n - rank_matrix
            commit("s${CTI(i)} = $n - $rank_matrix = $cur_geometricMultiplicity", Tag.SOLUTION)
            geometricMultiplicity.add(cur_geometricMultiplicity)
            i++
        }
        i = 0
        j = 0
        commit("Составим матрицу по имеющимся данным. ", Tag.PROCEEDING)
        val begin_blocks : MutableList<Int> = mutableListOf()
        val end_blocks : MutableList<Int> = mutableListOf()
        while (i < eigenvalues.size){
            begin_blocks.add(j)
            j+=algebraicMultiplicity[i]
            end_blocks.add(j)
            var k = 1
            while (k < geometricMultiplicity[i]){
                begin_blocks.add(begin_blocks[begin_blocks.size-1] + 1)
                end_blocks.add(end_blocks[end_blocks.size - 1])
                end_blocks[end_blocks.size - 1] = begin_blocks[begin_blocks.size-1] + 1
                k++
            }
            i++
        }
        val JordanForm = Matrix(n)
        i = 0
        j = 0
        var k = 0
        while (k < n){
            if (j < algebraicMultiplicity[i]) {
                j++
            }
            else{
                j = 1
                i++
            }
            JordanForm.arr[k][k] = eigenvalues[i]
            k++
        }
        k = 0
        while (k<geometricMultiplicity.size){
            if (end_blocks[k] - begin_blocks[k] > 1){
                for (f in begin_blocks[k] until end_blocks[k]) JordanForm.arr[k][k+1] = createNumb(1)
            }
            k++
        }
        JordanForm.log_this(Tag.ANSWER)

        return JordanForm
    }
    override fun plus(right: Ring): Ring {
        if (right is Matrix){
            val left: Matrix = this
            if (left.m == right.m && left.n == right.n) {
                val m = left.m
                val n = left.n
                val zero = createNumb(0.0)
                val arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, m, n)
                for (i in 0 until m) for (j in 0 until n) arr[i][j] = left.arr[i][j] + right.arr[i][j]
                return Matrix(arr)
            } else throw MatrixDimensionMismatchException()
        }
        else throw NonComplianceTypesException()
    }

    override fun minus(right: Ring): Ring {
        if (right is Matrix){
            val left: Matrix = this
            if (left.m == right.m && left.n == right.n) {
                val m = left.m
                val n = left.n
                val arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, m, n)
                for (i in 0 until m) for (j in 0 until n) arr[i][j] = left.arr[i][j] - right.arr[i][j]
                return Matrix(arr)
            } else throw MatrixDimensionMismatchException()
        }
        else throw NonComplianceTypesException()
    }

    override fun times(right: Ring): Ring {
        if (right is Numb || right is Polynom){
            val copy : Matrix = Matrix(arr)
            for (i in 0 until m) copy.mult_string(i, right, false)
            return copy
        }
        else if (right is Matrix){
            val left: Matrix = this
            if (left.n == right.m) {
                val m = left.m
                val n = left.n
                val p = right.n
                val zero = createNumb(0.0)
                val arr = createRectangleArrayList<Ring>({ createNumb(0.0) }, m, p)
                for (i in 0 until m) for (j in 0 until p) for (k in 0 until n) arr[i][j] += left.arr[i][k] * right.arr[k][j]
                return Matrix(arr)
            } else throw MatrixDimensionMismatchException()
        }
        else throw NonComplianceTypesException()
    }

    override fun div(right: Ring): Ring {
        if (right is DecNumb || right is FractionalNumb){
            val copy : Matrix = Matrix(arr)
            for (i in 0 until m) copy.div_string(i, right)
            return copy
        }
        else if (right is Matrix){
            val left: Matrix = this
            if (left.n == right.m && right.m == right.n) {
                commit( "Чтобы разделить матрицу на матрицу, надо умножить левую матрицу на противоположную правой",
                    Tag.BASE_RULES)
                val real_right = right.getInverse()
                return left*real_right
            } else throw MatrixDimensionMismatchException()
        }
        else throw NonComplianceTypesException()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Matrix) {
            if (m == other.m && n == other.n){
                for (i in 0 until m) for (j in 0 until n) if (arr[i][j]!=other.arr[i][j]) return false
                return true
            }
            else return false
        }
        else return false
    }

    override fun unaryMinus() : Ring{
        val copy = Matrix(arr)
        for (i in 0 until m) copy.mult_string(i, createNumb(-1.0))
        return copy
    }

    override fun toString(): String {
        var decode = ""
        for (i in 0 until m) {
            for (j in 0 until n) decode += arr[i][j].toString() + " "
            decode += "\n"
        }
        return decode
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + cof_det.hashCode()
        result = 31 * result + m
        result = 31 * result + n
        result = 31 * result + arr.hashCode()
        return result
    }
}