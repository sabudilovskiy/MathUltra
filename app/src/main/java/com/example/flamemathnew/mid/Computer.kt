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


class Computer
{
    companion object {
        fun getLog(): MutableList<String> {
            return Log.getLog()
        }

        fun getLogAsStr(): String {
            val temp: MutableList<String> = getLog()
            var answer: String = ""
            for (i in 0 until temp.size) answer += temp[i] + "\n"
            return answer
        }

        fun countLexemes(
            temp_input: String?,
            temp_keys: MutableList<String?>,
            temp_values: MutableList<String?>
        ): Double {
            Log.clear()
            if (temp_input == null || temp_input == "") throw InputInputLexemesException()
            val input: String = temp_input
            val keys: MutableList<String> = mutableListOf()
            val str_values: MutableList<String> = mutableListOf()
            val values: MutableList<Double> = mutableListOf()
            var i = ComputerHelper.findNullStringInArray(temp_keys)
            if (i != -1) throw InputKeysLexemesException(i)
            for (str in temp_keys) keys.add(str!!)
            i = ComputerHelper.findNullStringInArray(temp_values)
            if (i != -1) throw InputValuesLexemesException(i)
            for (str in temp_values) str_values.add(str!!)
            i = 0
            val n = str_values.size
            while (i < n) {
                if (ComputerHelper.isNumber(str_values[i])) {
                    values.add(str_values[i].toDouble())
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
            return if (getError() === IdErrors.NON_ERROR) {
                answer.getValue()
            } else if (getError() === IdErrors.UNKNOWN_FUNCTION) {
                throw UnknownFunctionException()
            } else if (getError() === IdErrors.ERROR_SIGNS) {
                throw ErrorSignsException()
            } else if (getError() === IdErrors.IMPOSSIBLE_COUNT) {
                throw ImpossibleCountException()
            } else if (getError() === IdErrors.MISS_ARGUMENT_BINARY_OPERATOR) {
                throw MissArgumentBinaryOperatorException()
            } else if (getError() === IdErrors.MISS_ARGUMENT_PRE_OPERATOR) {
                throw MissArgumentPreOperatorException()
            } else if (getError() === IdErrors.MISS_ARGUMENT_POST_OPERATOR) {
                throw MissArgumentPostOperatorException()
            } else if (getError() === IdErrors.HAVE_OPEN_BRACKETS) {
                throw HaveOpenBracketsException()
            } else if (getError() === IdErrors.MORE_RIGHT_BRACKETS) {
                throw MoreRightBracketsException()
            } else if (getError() === IdErrors.BAD_ARGUMENTS) {
                throw BadArgumentsException()
            } else {
                throw UnknownErrorException()
            }
        }

        fun countDeterminant(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?
        ): String {
            Log.clear()
            if (number == null) throw KeyNumberEmptyException()
            else if (number == "DEC") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.DEC
            } else if (number == "PROPER") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.PROPER
            } else throw UnknownNumberException()
            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()
            for (i in 0 until m) if (temp_matrix[i].size != m) throw NonQuadraticMatrixException()
            val arr: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, m, m)
            for (i in 0 until m)
                for (j in 0 until m)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
            val current: Matrix = Matrix(arr)
            val answer: Ring
            if (method == null) throw KeyMethodEmptyException()
            else if (method == "LAPLASS") {
                answer = current.det_with_laplass()
            } else if (method == "TRIANGLE") {
                answer = current.det_with_triangle()
            } else if (method == "SARUSS") {
                answer = current.det_with_saruss_rule()
            } else if (method == "BASIC") {
                answer = current.det_with_basic_rules()
            } else throw UnknownMethodException()
            return answer.toString()
        }

        fun findInverseMatrix(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?
        ): String {
            Log.clear()
            if (number == null) throw KeyNumberEmptyException()
            else if (number == "DEC") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.DEC
            } else if (number == "PROPER") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.PROPER
            } else throw UnknownNumberException()
            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()
            for (i in 0 until m) if (temp_matrix[i].size != m) throw NonQuadraticMatrixException()
            val arr: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, m, m)
            for (i in 0 until m)
                for (j in 0 until m)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
            val current: Matrix = Matrix(arr)
            val answer: Ring
            if (method == null) throw KeyMethodEmptyException()
            else if (method == "GAUSS") {
                try {
                    answer = current.getInverseGauss()
                } catch (degenerate_matrix: DegenerateMatrixException) {
                    return "Матрица вырождена. Обратная не существует"
                }
            } else if (method == "ALGEBRAIC_COMPLEMENT") {
                try {
                    answer = current.getInverseAlgebraicComplement()
                } catch (degenerate_matrix: DegenerateMatrixException) {
                    return "Матрица вырождена. Обратная не существует"
                }
            } else throw UnknownMethodException()
            return answer.toString()
        }

        fun countRank(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?
        ): Int {
            Log.clear()
            if (number == null) throw KeyNumberEmptyException()
            else if (number == "DEC") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.DEC
            } else if (number == "PROPER") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.PROPER
            } else throw UnknownNumberException()
            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()
            val n: Int = temp_matrix[0].size
            for (i in 0 until m) if (temp_matrix[i].size != n) throw MatrixErrorException()
            val arr: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, m, n)
            for (i in 0 until m)
                for (j in 0 until n)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
            val current: Matrix = Matrix(arr)
            val answer: Int
            if (method == null) throw KeyMethodEmptyException()
            else if (method == "TRIANGLE") {
                answer = current.rankWithTriangle()
            } else if (method == "MINORS") {
                answer = current.rankWithMinors()
            } else throw UnknownMethodException()
            return answer
        }

        fun SLE(
            temp_matrix: MutableList<MutableList<String?>>,
            method: String?,
            number: String?
        ): String {
            Log.clear()
            if (number == null) throw KeyNumberEmptyException()
            else if (number == "DEC") {
                Numbers.type = Number.DEC
            } else if (number == "PROPER") {
                Numbers.type = com.example.flamemathnew.back.parameters.Number.PROPER
            } else throw UnknownNumberException()
            val m: Int = temp_matrix.size
            if (m < 1) throw MatrixFailException()
            val n: Int = temp_matrix[0].size
            for (i in 0 until m) if (temp_matrix[i].size != n) throw MatrixFailException()
            val arr: MutableList<MutableList<Ring>>
            val free: MutableList<MutableList<Ring>>
            arr = createRectangleArrayList({ createNumb(0.0) }, m, n - 1)
            free = createRectangleArrayList({ createNumb(0.0) }, m, 1)
            for (i in 0 until m) {
                for (j in 0 until n - 1)
                    if (ComputerHelper.isNumber(temp_matrix[i][j]!!)) arr[i][j] =
                        createNumb(temp_matrix[i][j]!!.toDouble())
                    else throw FieldErrorException(i, j)
                if (ComputerHelper.isNumber(temp_matrix[i][n - 1]!!)) free[i][0] =
                    createNumb(temp_matrix[i][n - 1]!!.toDouble())
                else throw FieldErrorException(i, n - 1)
            }
            val current: AugmentedMatrix = AugmentedMatrix(arr, free)
            try {
                val answer: MathObject = current.solve_system()
                return answer.toString()
            } catch (have_not_solution: HaveNotSolutionsException) {
                return "Нет решений."
            }
        }

        fun times(
            temp_matrix_left: MutableList<MutableList<String?>>,
            temp_matrix_right: MutableList<MutableList<String?>>,
            number : Number
        ): String {
            Log.clear()
            val m: Int = temp_matrix_left.size
            if (m < 1) throw MatrixFailException()
            val n: Int = temp_matrix_left[0].size
            for (i in 0 until m) if (temp_matrix_left[i].size != n) throw MatrixErrorException()
            val left_arr: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, m, n)
            for (i in 0 until m)
                for (j in 0 until n)
                    if (ComputerHelper.isNumber(temp_matrix_left[i][j]!!)) left_arr[i][j] =
                        createNumb(temp_matrix_left[i][j]!!.toDouble())
                    else throw FieldErrorSleException(i, j, false)
            if (temp_matrix_right.size != n) throw MatrixDimensionMismatchException()
            val p: Int = temp_matrix_right[0].size
            for (i in 0 until n) if (temp_matrix_right[i].size != p) throw MatrixFailException()
            val right_arr: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, n, p)
            for (i in 0 until n)
                for (j in 0 until p)
                    if (ComputerHelper.isNumber(temp_matrix_right[i][j]!!)) right_arr[i][j] =
                        createNumb(temp_matrix_right[i][j]!!.toDouble())
                    else throw FieldErrorSleException(i, j, true)
            if (number == null) throw KeyNumberEmptyException()
             else Numbers.type = number
            val left: Matrix = Matrix(left_arr)
            val right: Matrix = Matrix(right_arr)
            return (left * right).toString()
        }


    }
}