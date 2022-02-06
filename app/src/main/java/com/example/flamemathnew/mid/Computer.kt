package com.example.flamemathnew.mid


import com.example.flamemathnew.back.lexemes.*
import com.example.flamemathnew.back.lexemes.ErrorHandler.Companion.getError
import com.example.flamemathnew.back.logger.Log
import com.example.flamemathnew.back.mathobject.MathObject
import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.matrix.AugmentedMatrix
import com.example.flamemathnew.back.matrix.Matrix
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.parameters.Number
import com.example.flamemathnew.back.support.Support.Companion.createRectangleArrayList
import com.example.flamemathnew.mid.exceptions.*
import com.example.flamemathnew.mid.settings.Numbers

/*
 * @author Sabudilovskiy
 */
class Computer {

    companion object {
        private fun getLog(): MutableList<String> {
            return Log.getLog()
        }

        fun getLogAsStr(): String {
            val temp: MutableList<String> = getLog()
            var answer = ""
            for (i in 0 until temp.size) answer += temp[i] + "\n"
            return answer
        }

        fun countLexemes(
            temp_input: String?,
            temp_keys: MutableList<String?>,
            temp_values: MutableList<String?>,
        ): Double {
            Log.clear()
            if (temp_input == null || temp_input == "") throw InputInputLexemesException()

            val input: String = temp_input

            val keys: MutableList<String> = mutableListOf()

            val strValues: MutableList<String> = mutableListOf()

            val values: MutableList<Double> = mutableListOf()

            var i = ComputerHelper.findNullStringInArray(temp_keys)
            if (i != -1) throw InputKeysLexemesException(i)

            for (str in temp_keys) keys.add(str!!)

            i = ComputerHelper.findNullStringInArray(temp_values)
            if (i != -1) throw InputValuesLexemesException(i)

            for (str in temp_values) strValues.add(str!!)
            i = 0
            val n = strValues.size

            while (i < n) {
                if (ComputerHelper.isNumber(strValues[i])) {
                    values.add(strValues[i].toDouble())
                    i++
                } else throw InputValuesLexemesException(i)
            }
            if (keys.size != values.size) {
                throw ArgumentListMismatchException()
            }

            Archieve(keys)
            ErrorHandler.setDefault()
            val answer: Lexeme
            val sentence = Sentence(input!!)
            if (getError() === IdErrors.ERROR_SIGNS) throw ErrorSignsException() else if (getError() === IdErrors.UNKNOWN_FUNCTION) throw UnknownFunctionException()

            sentence.substitute(keys, values)

            answer = sentence.count()
            return when (getError()) {
                IdErrors.NON_ERROR -> {
                    answer.getValue()
                }
                IdErrors.UNKNOWN_FUNCTION -> {
                    throw UnknownFunctionException()
                }
                IdErrors.ERROR_SIGNS -> {
                    throw ErrorSignsException()
                }
                IdErrors.IMPOSSIBLE_COUNT -> {
                    throw ImpossibleCountException()
                }
                IdErrors.MISS_ARGUMENT_BINARY_OPERATOR -> {
                    throw MissArgumentBinaryOperatorException()
                }
                IdErrors.MISS_ARGUMENT_PRE_OPERATOR -> {
                    throw MissArgumentPreOperatorException()
                }
                IdErrors.MISS_ARGUMENT_POST_OPERATOR -> {
                    throw MissArgumentPostOperatorException()
                }
                IdErrors.HAVE_OPEN_BRACKETS -> {
                    throw HaveOpenBracketsException()
                }
                IdErrors.MORE_RIGHT_BRACKETS -> {
                    throw MoreRightBracketsException()
                }
                IdErrors.BAD_ARGUMENTS -> {
                    throw BadArgumentsException()
                }
                else -> {
                    throw UnknownErrorException()
                }
            }
        }

        fun countDeterminant(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?,
        ): String {
            Log.clear()
            when (number) {
                null -> throw KeyNumberEmptyException()
                "DEC" -> {
                    Numbers.type = Number.DEC
                }
                "PROPER" -> {
                    Numbers.type = Number.PROPER
                }
                else -> throw UnknownNumberException()
            }

            val m: Int = temp_matrix.size

            if (m < 1) throw MatrixFailException()

            for (i in 0 until m) if (temp_matrix[i].size != m) throw NonQuadraticMatrixException()

            val arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, m)

            for (i in 0 until m)
                for (j in 0 until m)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)

            val current = Matrix(arr)

            val answer: Ring = when (method) {
                null -> throw KeyMethodEmptyException()
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
                else -> throw UnknownMethodException()
            }
            return answer.toString()
        }

        fun findInverseMatrix(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?,
        ): String {
            Log.clear()

            when (number) {
                null -> throw KeyNumberEmptyException()
                "DEC" -> {
                    Numbers.type = Number.DEC
                }
                "PROPER" -> {
                    Numbers.type = Number.PROPER
                }
                else -> throw UnknownNumberException()
            }

            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()

            for (i in 0 until m) if (temp_matrix[i].size != m) throw NonQuadraticMatrixException()

            val arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, m)

            for (i in 0 until m)
                for (j in 0 until m)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)

            val current = Matrix(arr)
            val answer: Ring = when (method) {
                null -> throw KeyMethodEmptyException()
                "GAUSS" -> {
                    try {
                        current.getInverseGauss()
                    } catch (degenerate_matrix: DegenerateMatrixException) {
                        return "Матрица вырождена. Обратная не существует"
                    }
                }
                "ALGEBRAIC_COMPLEMENT" -> {
                    try {
                        current.getInverseAlgebraicComplement()
                    } catch (degenerate_matrix: DegenerateMatrixException) {
                        return "Матрица вырождена. Обратная не существует"
                    }
                }
                else -> throw UnknownMethodException()
            }
            return answer.toString()
        }

        fun countRank(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?,
        ): Int {
            Log.clear()

            when (number) {
                null -> throw KeyNumberEmptyException()
                "DEC" -> {
                    Numbers.type = Number.DEC
                }
                "PROPER" -> {
                    Numbers.type = Number.PROPER
                }
                else -> throw UnknownNumberException()
            }

            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()

            val n: Int = temp_matrix[0].size

            for (i in 0 until m) if (temp_matrix[i].size != n) throw MatrixErrorException()

            val arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n)

            for (i in 0 until m)
                for (j in 0 until n)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
            val current = Matrix(arr)

            val answer: Int = when (method) {
                null -> throw KeyMethodEmptyException()
                "TRIANGLE" -> {
                    current.rankWithTriangle()
                }
                "MINORS" -> {
                    current.rankWithMinors()
                }
                else -> throw UnknownMethodException()
            }
            return answer
        }

        fun SLE(
            temp_matrix: MutableList<MutableList<String?>>,
            number: String?,
        ): String {
            Log.clear()
            when (number) {
                null -> throw KeyNumberEmptyException()
                "DEC" -> {
                    Numbers.type = Number.DEC
                }
                "PROPER" -> {
                    Numbers.type = Number.PROPER
                }
                else -> throw UnknownNumberException()
            }

            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()

            val n: Int = temp_matrix[0].size
            for (i in 0 until m) if (temp_matrix[i].size != n) throw MatrixFailException()

            val arr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n - 1)
            val free: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, 1)

            for (i in 0 until m) {
                for (j in 0 until n - 1)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
                if (ComputerHelper.isNumber(temp_matrix[i][n - 1]!!)) free[i][0] =
                    createNumb(temp_matrix[i][n - 1]!!.toDouble())
                else throw FieldErrorException(i, n - 1)
            }
            val current = AugmentedMatrix(arr, free)
            return try {
                val answer: MathObject = current.solve_system()
                answer.toString()
            } catch (have_not_solution: HaveNotSolutionsException) {
                "Нет решений."
            }
        }

        fun times(
            temp_matrix_left: MutableList<MutableList<String?>>,
            temp_matrix_right: MutableList<MutableList<String?>>,
            number: Number,
        ): String {
            Log.clear()

            val m: Int = temp_matrix_left.size
            if (m < 1) throw MatrixFailException()

            val n: Int = temp_matrix_left[0].size
            for (i in 0 until m) if (temp_matrix_left[i].size != n) throw MatrixErrorException()

            val leftArr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m, n)

            for (i in 0 until m)
                for (j in 0 until n)
                    if (ComputerHelper.isNumber(temp_matrix_left[i][j]!!)) leftArr[i][j] =
                        createNumb(temp_matrix_left[i][j]!!.toDouble())
                    else throw FieldErrorSleException(i, j, false)


            if (temp_matrix_right.size != n) throw MatrixDimensionMismatchException()

            val p: Int = temp_matrix_right[0].size
            for (i in 0 until n) if (temp_matrix_right[i].size != p) throw MatrixFailException()

            val rightArr: MutableList<MutableList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, n, p)

            for (i in 0 until n)
                for (j in 0 until p)
                    if (ComputerHelper.isNumber(temp_matrix_right[i][j]!!)) rightArr[i][j] =
                        createNumb(temp_matrix_right[i][j]!!.toDouble())
                    else throw FieldErrorSleException(i, j, true)
            Numbers.type = number

            val left = Matrix(leftArr)
            val right = Matrix(rightArr)

            return (left * right).toString()
        }
    }
}
