package com.example.flamemathnew.backend.mid

import com.example.flamemathnew.backend.back.Lexemes.Archieve
import com.example.flamemathnew.backend.back.Lexemes.ErrorHandler.getError
import com.example.flamemathnew.backend.back.Lexemes.ErrorHandler.getBeginError
import com.example.flamemathnew.backend.back.Lexemes.ErrorHandler.getEndError
import com.example.flamemathnew.backend.back.Lexemes.ErrorHandler.setDefault
import com.example.flamemathnew.backend.back.Lexemes.Id_errors
import com.example.flamemathnew.backend.back.Lexemes.Lexeme
import com.example.flamemathnew.backend.back.Lexemes.Sentence
import com.example.flamemathnew.backend.back.Logger.Log
import com.example.flamemathnew.backend.back.MathObject.MathObject
import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.Matrix.AugmentedMatrix
import com.example.flamemathnew.backend.back.Matrix.Matrix
import com.example.flamemathnew.backend.back.Numbers.createNumb
import com.example.flamemathnew.backend.back.Parameters.Number
import com.example.flamemathnew.backend.back.Support.createRectangleArrayList

fun findNullStringInArray(arr: ArrayList<String?>): Int {
    var i = 0
    val n: Int = arr.size
    while (i < n) {
        if (arr[i] == null || arr[i] == "") return i
        i++
    }
    return -1
}

fun isNumber(str: String): Boolean {
    if (str == "") return false
    var commas = 0
    var a = 0
    a = if ((str[0] == '+' || str[0] == '-') && str.length > 1 && isDigit(str[1])) 2
    else if (isDigit(str[0])) 1
    else return false

    for (i in a until str.length) {
        val ch: Char = str[i]
        if (isDigit(ch)) {
        } else if (ch == '.' && commas == 0) commas++
        else return false
    }
    return true
}

fun isDigit(ch: Char): Boolean {
    return ch in '0'..'9'
}

object Computer {
    private fun getLog(): ArrayList<String> {
        return Log.get_log()
    }

    fun getLogAsStr(): String {
        val temp: ArrayList<String> = getLog()
        var answer = ""
        for (i in 0 until temp.size) answer += temp[i] + "\n"
        return answer
    }

    fun countLexemes(temp_input: String?, temp_keys: ArrayList<String?>, temp_values: ArrayList<String?>): Double {
        Log.clear()
        if (temp_input == null || temp_input == "") throw Input_Input_Lexemes_Exception()
        val input: String = temp_input

        val keys: ArrayList<String> = arrayListOf()
        val strValues: ArrayList<String> = arrayListOf()
        val values: ArrayList<Double> = arrayListOf()

        var i = findNullStringInArray(temp_keys)

        if (i != -1) throw Input_Keys_Lexemes_Exception(i)

        for (str in temp_keys) keys.add(str!!)
        i = findNullStringInArray(temp_values)

        if (i != -1) throw Input_Values_Lexemes_Exception(i)
        for (str in temp_values) strValues.add(str!!)

        i = 0
        val n = strValues.size

        while (i < n) {
            if (isNumber(strValues[i])) {
                values.add(strValues[i].toDouble())
                i++
            } else throw Input_Values_Lexemes_Exception(i)
        }

        if (keys.size != values.size) {
            throw ARGUMENT_LIST_MISMATCH()
        }

        Archieve(keys)
        setDefault()

        val answer: Lexeme
        val sentence = Sentence(input!!)

        if (getError() === Id_errors.ERROR_SIGNS)
            throw ERROR_SIGNS()
        else if (getError() === Id_errors.UNKNOWN_FUNCTION) throw UNKNOWN_FUNCTION()

        sentence.substitute(keys, values)
        answer = sentence.count()

        return when (getError()) {
            Id_errors.NON_ERROR -> {
                answer.get_value()
            }
            Id_errors.UNKNOWN_FUNCTION -> {
                throw UNKNOWN_FUNCTION()
            }
            Id_errors.ERROR_SIGNS -> {
                throw ERROR_SIGNS()
            }
            Id_errors.IMPOSSIBLE_COUNT -> {
                throw IMPOSSIBLE_COUNT()
            }
            Id_errors.MISS_ARGUMENT_BINARY_OPERATOR -> {
                throw MISS_ARGUMENT_BINARY_OPERATOR()
            }
            Id_errors.MISS_ARGUMENT_PRE_OPERATOR -> {
                throw MISS_ARGUMENT_PRE_OPERATOR()
            }
            Id_errors.MISS_ARGUMENT_POST_OPERATOR -> {
                throw MISS_ARGUMENT_POST_OPERATOR()
            }
            Id_errors.HAVE_OPEN_BRACKETS -> {
                throw HAVE_OPEN_BRACKETS()
            }
            Id_errors.MORE_RIGHT_BRACKETS -> {
                throw MORE_RIGHT_BRACKETS()
            }
            Id_errors.BAD_ARGUMENTS -> {
                throw BAD_ARGUMENTS()
            }
            else -> {
                throw UNKNOWN_ERROR()
            }
        }
    }

    fun countDeterminant(temp_matrix: ArrayList<ArrayList<String?>>, method: String?, number: String?): String {
        Log.clear()
        when (number) {
            null -> throw KEY_NUMBER_EMPTY()
            "DEC" -> {
                Settings.numbers.type = Number.DEC
            }
            "PROPER" -> {
                Settings.numbers.type = Number.PROPER
            }
            else -> throw UNKNOWN_NUMBER()
        }

        val m: Int = temp_matrix.size
        if (m < 1) throw MATRIX_FAIL()

        for (i in 0 until m) if (temp_matrix[i].size != m) throw NON_QUADRATIC_MATRIX()

        val arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, m)

        for (i in 0 until m)
            for (j in 0 until m)
                if (isNumber(temp_matrix[i][j]!!)) arr[i][j] = createNumb(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)
        val current = Matrix(arr)

        val answer: Ring = when (method) {
            null -> throw KEY_METHOD_EMPTY()
            "LAPLASS" -> {
                current.det_with_laplass()
            }
            "TRIANGLE" -> {
                current.det_with_triangle()
            }
            "SARUSS" -> {
                current.det_with_saruss_rule()
            }
            "BASIC" -> {
                current.det_with_basic_rules()
            }
            else -> throw UNKNOWN_METHOD()
        }
        return answer.toString()
    }

    fun findInverseMatrix(temp_matrix: ArrayList<ArrayList<String?>>, method: String?, number: String?): String {
        Log.clear()
        when (number) {
            null -> throw KEY_NUMBER_EMPTY()
            "DEC" -> {
                Settings.numbers.type = Number.DEC
            }
            "PROPER" -> {
                Settings.numbers.type = Number.PROPER
            }
            else -> throw UNKNOWN_NUMBER()
        }

        val m: Int = temp_matrix.size
        if (m < 1) throw MATRIX_FAIL()

        for (i in 0 until m) if (temp_matrix[i].size != m) throw NON_QUADRATIC_MATRIX()

        val arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, m)

        for (i in 0 until m)
            for (j in 0 until m)
                if (isNumber(temp_matrix[i][j]!!)) arr[i][j] = createNumb(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)

        val current = Matrix(arr)

        val answer: Ring = when (method) {
            null -> throw KEY_METHOD_EMPTY()
            "GAUSS" -> {
                try {
                    current.getInverseGauss()
                } catch (degenerate_matrix: DEGENERATE_MATRIX) {
                    return "Матрица вырождена. Обратная не существует"
                }
            }
            "ALGEBRAIC_COMPLEMENT" -> {
                try {
                    current.getInverseAlgebraicComplement()
                } catch (degenerate_matrix: DEGENERATE_MATRIX) {
                    return "Матрица вырождена. Обратная не существует"
                }
            }
            else -> throw UNKNOWN_METHOD()
        }
        return answer.toString()
    }

    fun countRank(temp_matrix: ArrayList<ArrayList<String?>>, method: String?, number: String?): Int {
        Log.clear()
        when (number) {
            null -> throw KEY_NUMBER_EMPTY()
            "DEC" -> {
                Settings.numbers.type = Number.DEC
            }
            "PROPER" -> {
                Settings.numbers.type = Number.PROPER
            }
            else -> throw UNKNOWN_NUMBER()
        }

        val m: Int = temp_matrix.size
        if (m < 1) throw MATRIX_FAIL()

        val n: Int = temp_matrix[0].size

        for (i in 0 until m) if (temp_matrix[i].size != n) throw MATRIX_ERROR()

        val arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n)

        for (i in 0 until m)
            for (j in 0 until n)
                if (isNumber(temp_matrix[i][j]!!)) arr[i][j] = createNumb(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)

        val current = Matrix(arr)

        val answer: Int = when (method) {
            null -> throw KEY_METHOD_EMPTY()
            "TRIANGLE" -> {
                current.rankWithTriangle()
            }
            "MINORS" -> {
                current.rankWithMinors()
            }
            else -> throw UNKNOWN_METHOD()
        }
        return answer
    }

    fun SLE(temp_matrix: ArrayList<ArrayList<String?>>, method: String?, number: String?): String {
        Log.clear()
        when (number) {
            null -> throw KEY_NUMBER_EMPTY()
            "DEC" -> {
                Settings.numbers.type = Number.DEC
            }
            "PROPER" -> Settings.numbers.type = Number.PROPER
            else -> throw UNKNOWN_NUMBER()
        }

        val m: Int = temp_matrix.size
        if (m < 1) throw MATRIX_FAIL()
        val n: Int = temp_matrix[0].size
        for (i in 0 until m) if (temp_matrix[i].size != n) throw MATRIX_FAIL()

        val arr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n - 1)
        val free: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, 1)

        for (i in 0 until m) {
            for (j in 0 until n - 1)
                if (isNumber(temp_matrix[i][j]!!)) arr[i][j] = createNumb(temp_matrix[i][j]!!.toDouble())
                else throw FIELD_ERROR(i, j)
            if (isNumber(temp_matrix[i][n - 1]!!)) free[i][0] = createNumb(temp_matrix[i][n - 1]!!.toDouble())
            else throw FIELD_ERROR(i, n - 1)
        }

        val current = AugmentedMatrix(arr, free)

        return try {
            val answer: MathObject = current.solve_system()
            answer.toString()
        } catch (have_not_solution: HAVE_NOT_SOLUTIONS) {
            "Нет решений."
        }
    }

    fun times(temp_matrix_left: ArrayList<ArrayList<String?>>, temp_matrix_right: ArrayList<ArrayList<String?>>, number: String?): String {
        Log.clear()
        val m: Int = temp_matrix_left.size
        if (m < 1) throw MATRIX_FAIL()

        val n: Int = temp_matrix_left[0].size

        for (i in 0 until m) if (temp_matrix_left[i].size != n) throw MATRIX_ERROR()

        val leftArr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n)
        for (i in 0 until m)
            for (j in 0 until n)
                if (isNumber(temp_matrix_left[i][j]!!)) leftArr[i][j] = createNumb(temp_matrix_left[i][j]!!.toDouble())
                else throw FIELD_ERROR_SLE(i, j, false)

        if (temp_matrix_right.size != n) throw MATRIX_DIMENSION_MISSMATCH()

        val p: Int = temp_matrix_right[0].size

        for (i in 0 until n) if (temp_matrix_right[i].size != p) throw MATRIX_FAIL()

        val rightArr: ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, n, p)

        for (i in 0 until n)
            for (j in 0 until p)
                if (isNumber(temp_matrix_right[i][j]!!)) rightArr[i][j] = createNumb(temp_matrix_right[i][j]!!.toDouble())
                else throw FIELD_ERROR_SLE(i, j, true)

        when (number) {
            null -> throw KEY_NUMBER_EMPTY()
            "DEC" -> {
                Settings.numbers.type = Number.DEC
            }
            "PROPER" -> {
                Settings.numbers.type = Number.PROPER
            }
            else -> throw UNKNOWN_NUMBER()
        }

        val left = Matrix(leftArr)
        val right = Matrix(rightArr)
        return (left * right).toString()
    }

    open class Lexemes_Exception : Exception {
        var error_begin = -1
            private set
        var error_end = -1
            private set

        constructor() {}
        constructor(begin: Int, end: Int) {
            error_begin = begin
            error_end = end
        }
    }

    class Input_Input_Lexemes_Exception() : Exception() {
    }

    class Input_Keys_Lexemes_Exception() : Exception() {
        var number_key: Int = -1

        constructor(_error: Int) : this() {
            number_key = _error
        }
    }

    class Input_Values_Lexemes_Exception() : Exception() {
        var number_value: Int = -1

        constructor(_error: Int) : this() {
            number_value = _error
        }
    }

    class ARGUMENT_LIST_MISMATCH : Lexemes_Exception()
    class BAD_ARGUMENTS : Lexemes_Exception(getBeginError(), getEndError())
    class ERROR_SIGNS : Lexemes_Exception(getBeginError(), getEndError())
    class HAVE_OPEN_BRACKETS internal constructor() : Lexemes_Exception(getBeginError(), getEndError())
    class IMPOSSIBLE_COUNT : Lexemes_Exception(getBeginError(), getEndError())
    class MISS_ARGUMENT_BINARY_OPERATOR : Lexemes_Exception(getBeginError(), getEndError())
    class MISS_ARGUMENT_POST_OPERATOR : Lexemes_Exception(getBeginError(), getEndError())
    class MISS_ARGUMENT_PRE_OPERATOR : Lexemes_Exception(getBeginError(), getEndError())
    class MORE_RIGHT_BRACKETS : Lexemes_Exception(getBeginError(), getEndError())
    class UNKNOWN_ERROR : Lexemes_Exception(getBeginError(), getEndError())
    class UNKNOWN_FUNCTION : Lexemes_Exception(getBeginError(), getEndError())
    open class MATRIX_ERROR : Exception()
    class NON_QUADRATIC_MATRIX : MATRIX_ERROR()
    class DEGENERATE_MATRIX : MATRIX_ERROR()
    class MATRIX_FAIL : MATRIX_ERROR()
    class MATRIX_DIMENSION_MISSMATCH : MATRIX_ERROR()
    class INVALID_NUMBER_STRING : MATRIX_ERROR()
    class HAVE_NOT_SOLUTIONS : MATRIX_ERROR()
    class NON_SINGLE : MATRIX_ERROR()
    class NON_COMPLIANCE_TYPES : Exception()
    class NON_INTEGER : Exception()
    class KEY_METHOD_EMPTY : Exception()
    class UNKNOWN_METHOD : Exception()
    class KEY_NUMBER_EMPTY : Exception()
    class UNKNOWN_NUMBER : Exception()
    class CANNOTDIV : Exception()
    open class FIELD_ERROR : Exception {
        public var i: Int = -1
        public var j: Int = -1

        constructor(_i: Int, _j: Int) {
            i = _i
            j = _j
        }
    }

    open class FIELD_ERROR_SLE(_i: Int, _j: Int, _is_right: Boolean) : FIELD_ERROR(_i, _j) {
        public var is_right: Boolean = _is_right
    }
}