package com.example.flamemathnew.backend.ui.support


import com.example.flamemathnew.backend.mid.Computer
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.widget.EditText
import java.util.ArrayList

class Support {

    companion object {
        private val RED_TEXT_TAG = "RED_TEXT_TAG"



        fun computeSLE(listMatr: ArrayList<String>,
                       TYPE_COMPUTE: String,
                       numbersType: String,
                       N: Int,
                       M: Int) : String {
            val list: ArrayList<ArrayList<String?>> = readMatrix(listMatr, N, M)
            val free_arr : ArrayList<ArrayList<String?>> = readMatrixend(listMatr, 1, M)
            for (i in 0 until M) list[i].add(free_arr[0][i])
            var answer: String
            var log = ""
            try {

                for (i in list.indices){
                    for (j in list[i].indices){
                        Log.d("TAG_MATRIX_SLE", "... ${list[i][j]}")
                    }
                }

                answer = "Ответ: \n" + Computer.SLE(list, "method", numbersType)
                log = Computer.getLogAsStr()
            } catch (matrix_fail: Computer.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: Computer.NON_QUADRATIC_MATRIX) {
                answer = "Матрица не является квадратной. Вычислить обратную невозможно."
            } catch (field_error: Computer.FIELD_ERROR) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            } catch (degenerate_matrix: Computer.DEGENERATE_MATRIX) {
                answer = "Матрица является вырожденной. Нахождение обратной невозможно."
            }
            return "$answer $log"
        }


        fun computeLexemes(
            editText: EditText,
            keys: ArrayList<String?>,
            values: ArrayList<String?>
        ): String {
            var temp = ""
            try {
                temp = """Result """.trimIndent() + java.lang.String.valueOf(
                    Computer.countLexemes(
                        editText.text.toString(), keys, values
                    )
                )
            } catch (error: Computer.ARGUMENT_LIST_MISMATCH) {
                temp = "Списки аргументов не соответствуют."
            } catch (error: Computer.UNKNOWN_FUNCTION) {
                temp = "Неизвестная функция "
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.ERROR_SIGNS) {
                temp =
                    "Какое-то из чисел записано с ошибкой: слишком много точек." + "Место ошибки: " + Integer.toString(
                        error.error_begin
                    )
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.IMPOSSIBLE_COUNT) {
                temp = "Функцию в заданной точке невозможно вычислить."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.MISS_ARGUMENT_BINARY_OPERATOR) {
                temp = "У какого-то из бинарных операторов отсутствует аргумент."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.MISS_ARGUMENT_PRE_OPERATOR) {
                temp = "У какого-то из преоператоров отсутствует аргумент."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.MISS_ARGUMENT_POST_OPERATOR) {
                temp =
                    "У какого-то из постоператоров отсутствует аргумент." + "Ошибка от: " + error.error_begin + " до: " + error.error_end
            } catch (error: Computer.HAVE_OPEN_BRACKETS) {
                temp = "Есть незакрытая скобка."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.MORE_RIGHT_BRACKETS) {
                temp = "Закрыто больше скобок, чем открыто."
            } catch (error: Computer.BAD_ARGUMENTS) {
                temp = "У какого-то операторов недостаточно или слишком много аргументов."
                redText(error.error_begin, error.error_end, editText)
            } catch (error: Computer.UNKNOWN_ERROR) {
                temp = "Неизвестная ошибка."
            } catch (error: Computer.Input_Input_Lexemes_Exception) {
                temp = "Функция не введена"
            } catch (error: Computer.Input_Keys_Lexemes_Exception) {
                temp = "Ключ переменной №" + (error.number_key + 1) + " не введён"
            } catch (error: Computer.Input_Values_Lexemes_Exception) {
                temp =
                    "В вводе значения переменной №" + (error.number_value + 1) + " допущена ошибка"
            }
            return temp
        }


        fun readMatrix(listMatr: ArrayList<String>, N : Int, M : Int) : ArrayList<ArrayList<String?>> {
            val list: ArrayList<ArrayList<String?>> = ArrayList( N)
            var m = 0
            for (i in 0 until  N) {
                val row: ArrayList<String?> = ArrayList<String?>(M)
                for (j in 0 until  M) {
                    row.add(listMatr[m])
                    m++
                }
                list.add(row)
            }
            return list
        }
        fun readMatrixend(listMatr: ArrayList<String>, N : Int, M : Int) : ArrayList<ArrayList<String?>> {
            val list: ArrayList<ArrayList<String?>> = ArrayList( N)
            var m = listMatr.size - M*N
            for (i in 0 until  N) {
                val row: ArrayList<String?> = ArrayList<String?>(M)
                for (j in 0 until  M) {
                    row.add(listMatr[m])
                    m++
                }
                list.add(row)
            }
            return list
        }
        fun computeInversion(
            listMatr: ArrayList<String>,
            TYPE_COMPUTE: String,
            numbersType: String,
            N: Int,
            M: Int
        ): String {
            val list: ArrayList<ArrayList<String?>> = readMatrix(listMatr, N, M)
            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + Computer.findInverseMatrix(
                    list,
                    TYPE_COMPUTE,
                    numbersType
                ) + "\n"
                log = Computer.getLogAsStr()
            } catch (matrix_fail: Computer.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: Computer.NON_QUADRATIC_MATRIX) {
                answer = "Матрица не является квадратной. Вычислить обратную невозможно."
            } catch (field_error: Computer.FIELD_ERROR) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            } catch (degenerate_matrix: Computer.DEGENERATE_MATRIX) {
                answer = "Матрица является вырожденной. Нахождение обратной невозможно."
            }
            //TODO("Вывод необходимо переделать")
            return "$answer $log"
        }


        fun computeDeterminant(
            listMatr: ArrayList<String>,
            TYPE_COMPUTE: String,
            numbersType: String,
            N: Int,
            M: Int
        ): String {
            val list: ArrayList<ArrayList<String?>> = readMatrix(listMatr, N, M)
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
            } catch (matrix_fail: Computer.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: Computer.NON_QUADRATIC_MATRIX) {
                answer =
                    "Матрица не является квадратной. Вычислить определитель невозможно."
            } catch (field_error: Computer.FIELD_ERROR) {
                answer =
                    "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            //TODO("Вывод необходимо переделать")
            return "$answer $log"
        }


        fun computeRank(listMatr: ArrayList<String>,
                        TYPE_COMPUTE: String,
                        numbersType: String,
                        N: Int,
                        M: Int) : String{
            val list: ArrayList<ArrayList<String?>> = readMatrix(listMatr, N, M)

            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + Computer.countRank(list,
                    TYPE_COMPUTE,
                    numbersType
                ) + "\n"

                log = Computer.getLogAsStr()
            } catch (matrix_fail: Computer.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (field_error: Computer.FIELD_ERROR) {
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