package com.example.flamemathnew.ui.support

import MRV.MRV
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


    fun computeLexemes(
        editText: EditText,
        keys: ArrayList<String?>,
        values: ArrayList<String?> ) : String{
        var temp = ""
        try {
            temp = """Result """.trimIndent() + java.lang.String.valueOf(
                MRV.count_lexemes(
                    editText.text.toString(), keys, values
                )
            )
        } catch (error: MRV.ARGUMENT_LIST_MISMATCH) {
            temp = "Списки аргументов не соответствуют."
        } catch (error: MRV.UNKNOWN_FUNCTION) {
            temp = "Неизвестная функция "
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.ERROR_SIGNS) {
            temp =
                "Какое-то из чисел записано с ошибкой: слишком много точек." + "Место ошибки: " + Integer.toString(
                    error.error_begin
                )
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.IMPOSSIBLE_COUNT) {
            temp = "Функцию в заданной точке невозможно вычислить."
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.MISS_ARGUMENT_BINARY_OPERATOR) {
            temp = "У какого-то из бинарных операторов отсутствует аргумент."
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.MISS_ARGUMENT_PRE_OPERATOR) {
            temp = "У какого-то из преоператоров отсутствует аргумент."
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.MISS_ARGUMENT_POST_OPERATOR) {
            temp =
                "У какого-то из постоператоров отсутствует аргумент." + "Ошибка от: " + error.error_begin + " до: " + error.error_end
        } catch (error: MRV.HAVE_OPEN_BRACKETS) {
            temp = "Есть незакрытая скобка."
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.MORE_RIGHT_BRACKETS) {
            temp = "Закрыто больше скобок, чем открыто."
        } catch (error: MRV.BAD_ARGUMENTS) {
            temp = "У какого-то операторов недостаточно или слишком много аргументов."
            redText(error.error_begin, error.error_end, editText)
        } catch (error: MRV.UNKNOWN_ERROR) {
            temp = "Неизвестная ошибка."
        } catch (error: MRV.Input_Input_Lexemes_Exception) {
            temp = "Функция не введена"
        } catch (error: MRV.Input_Keys_Lexemes_Exception) {
            temp = "Ключ переменной №" + (error.number_key + 1) + " не введён"
        } catch (error: MRV.Input_Values_Lexemes_Exception) {
            temp = "В вводе значения переменной №" + (error.number_value + 1) + " допущена ошибка"
        }
        return temp
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