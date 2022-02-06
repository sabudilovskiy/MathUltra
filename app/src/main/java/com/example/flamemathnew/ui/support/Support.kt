package com.example.flamemathnew.ui.support

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import com.example.flamemathnew.mid.Computer
import com.example.flamemathnew.mid.exceptions.*

//import java.util.ArrayList

class Support {

    companion object {
        private val RED_TEXT_TAG = "RED_TEXT_TAG"



        fun computeSLE(listMatr: MutableList<String>,
                       TYPE_COMPUTE: String,
                       numbersType: String,
                       N: Int,
                       M: Int) : String {
            val list: MutableList<MutableList<String?>> = readMatrix(listMatr, N, M)
            val free_arr : MutableList<MutableList<String?>> = readMatrixend(listMatr, 1, M)
            for (i in 0 until M) list[i].add(free_arr[0][i])
            var answer: String
            var log = ""
            try {

                for (i in list.indices){
                    for (j in list[i].indices){
                        Log.d("TAG_MATRIX_SLE", "... ${list[i][j]}")
                    }
                }

                answer = "Ответ: \n" + Computer.SLE(list, numbersType)
                log = Computer.getLogAsStr()
            } catch (matrix_fail: MatrixFailException) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: NonQuadraticMatrixException) {
                answer = "Матрица не является квадратной. Вычислить обратную невозможно."
            } catch (field_error: FieldErrorException) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            } catch (degenerate_matrix: DegenerateMatrixException) {
                answer = "Матрица является вырожденной. Нахождение обратной невозможно."
            }
            return "$answer $log"
        }


        fun computeLexemes(
            editText: EditText,
            keys: MutableList<String?>,
            values: MutableList<String?>
        ): String {
            var temp = ""
            try {
                temp = """Result """.trimIndent() + java.lang.String.valueOf(
                    Computer.countLexemes(
                        editText.text.toString(), keys, values
                    )
                )
            } catch (error: ArgumentListMismatchException) {
                temp = "Списки аргументов не соответствуют."
            } catch (error: UnknownFunctionException) {
                temp = "Неизвестная функция "
                redText(error.error_begin, error.error_end, editText)
            } catch (error: ErrorSignsException) {
                temp =
                    "Какое-то из чисел записано с ошибкой: слишком много точек." + "Место ошибки: " + Integer.toString(
                        error.error_begin
                    )
                redText(error.error_begin, error.error_end, editText)
            } catch (error: ImpossibleCountException) {
                temp = "Функцию в заданной точке невозможно вычислить."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: MissArgumentBinaryOperatorException) {
                temp = "У какого-то из бинарных операторов отсутствует аргумент."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: MissArgumentPreOperatorException) {
                temp = "У какого-то из преоператоров отсутствует аргумент."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: MissArgumentPostOperatorException) {
                temp =
                    "У какого-то из постоператоров отсутствует аргумент." + "Ошибка от: " + error.error_begin + " до: " + error.error_end
            } catch (error: HaveOpenBracketsException) {
                temp = "Есть незакрытая скобка."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: MoreRightBracketsException) {
                temp = "Закрыто больше скобок, чем открыто."
            } catch (error: BadArgumentsException) {
                temp = "У какого-то операторов недостаточно или слишком много аргументов."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: UnknownErrorException) {
                temp = "Неизвестная ошибка."
            } catch (error: InputInputLexemesException) {
                temp = "Функция не введена"
            } catch (error: InputKeysLexemesException) {
                temp = "Ключ переменной №" + (error.number_key + 1) + " не введён"
            } catch (error: InputValuesLexemesException) {
                temp =
                    "В вводе значения переменной №" + (error.number_value + 1) + " допущена ошибка"
            }
            return temp
        }


        fun readMatrix(listMatr: MutableList<String>, N : Int, M : Int) : MutableList<MutableList<String?>> {
            val list: MutableList<MutableList<String?>> = MutableList( N) {i -> mutableListOf()}
            var m = 0
            for (i in 0 until  N) {
                val row: MutableList<String?> = MutableList<String?>(M) {i -> ""}
                for (j in 0 until  M) {
                    row.add(listMatr[m])
                    m++
                }
                list.add(row)
            }
            return list
        }
        fun readMatrixend(listMatr: MutableList<String>, N : Int, M : Int) : MutableList<MutableList<String?>> {
            val list: MutableList<MutableList<String?>> = MutableList( N) {i-> mutableListOf()}
            var m = listMatr.size - M*N
            for (i in 0 until  N) {
                val row: MutableList<String?> = MutableList<String?>(M){i-> ""}
                for (j in 0 until  M) {
                    row.add(listMatr[m])
                    m++
                }
                list.add(row)
            }
            return list
        }
        fun computeInversion(
            listMatr: MutableList<String>,
            TYPE_COMPUTE: String,
            numbersType: String,
            N: Int,
            M: Int
        ): String {
            val list: MutableList<MutableList<String?>> = readMatrix(listMatr, N, M)
            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + Computer.findInverseMatrix(
                    list,
                    TYPE_COMPUTE,
                    numbersType
                ) + "\n"
                log = Computer.getLogAsStr()
            } catch (matrix_fail: MatrixFailException) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: NonQuadraticMatrixException) {
                answer = "Матрица не является квадратной. Вычислить обратную невозможно."
            } catch (field_error: FieldErrorException) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            } catch (degenerate_matrix: DegenerateMatrixException) {
                answer = "Матрица является вырожденной. Нахождение обратной невозможно."
            }
            //TODO("Вывод необходимо переделать")
            return "$answer $log"
        }


        fun computeDeterminant(
            listMatr: MutableList<String>,
            TYPE_COMPUTE: String,
            numbersType: String,
            N: Int,
            M: Int
        ): String {
            val list: MutableList<MutableList<String?>> = readMatrix(listMatr, N, M)
            var answer: String
            var log = ""
            try {
                answer =
                    "Ответ: " + Computer.countDeterminant(
                        list,
                        TYPE_COMPUTE,
                        numbersType
                    ) + "\n"
                log = Computer.getLogAsStr()
            } catch (matrix_fail: MatrixFailException) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: NonQuadraticMatrixException) {
                answer =
                    "Матрица не является квадратной. Вычислить определитель невозможно."
            } catch (field_error: FieldErrorException) {
                answer =
                    "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            //TODO("Вывод необходимо переделать")
            return "$answer $log"
        }


        fun computeRank(listMatr: MutableList<String>,
                        TYPE_COMPUTE: String,
                        numbersType: String,
                        N: Int,
                        M: Int) : String{
            val list: MutableList<MutableList<String?>> = readMatrix(listMatr, N, M)

            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + Computer.countRank(list,
                    TYPE_COMPUTE,
                    numbersType
                ) + "\n"

                log = Computer.getLogAsStr()
            } catch (matrix_fail: MatrixFailException) {
                answer = "Матрица пуста"
            } catch (field_error: FieldErrorException) {
                answer =
                    "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            //TODO("Вывод необходимо переделать")
            return "$answer $log"
        }

        fun redText(error_begin: Int, error_end: Int, editText: EditText) {
            val full = editText.text.toString()
            editText.setText("")
            val word0: Spannable
            Log.d(RED_TEXT_TAG, "Error begin : $error_begin error end : $error_end")
            if (error_begin != 0) {
                word0 = SpannableString(full.substring(0, error_begin))
                word0.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    word0.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else word0 = SpannableString("")

            Log.d(RED_TEXT_TAG, "Word 0 : $word0")

            editText.setText(word0)
            val word: Spannable = SpannableString(full.substring(error_begin, error_end + 1))
            word.setSpan(
                ForegroundColorSpan(Color.RED),
                0,
                word.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            Log.d(RED_TEXT_TAG, "Word1: $word")

            editText.append(word)
            val wordTwo: Spannable = SpannableString(full.substring(error_end + 1, full.length))

            Log.d(RED_TEXT_TAG, "Error end: " + error_end + " len " + full.length)
            Log.d(RED_TEXT_TAG, "Word2 : $wordTwo")

            wordTwo.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                wordTwo.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            editText.append(wordTwo)
        }
    }
}