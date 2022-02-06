package com.example.flamemathnew.ui.algebra

import com.example.flamemathnew.backend.mid.Computer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.flamemathnew.R
import com.example.flamemathnew.backend.ui.algebra.getOnItemSelectListener
import com.example.flamemathnew.databinding.FragmentMultMatrixBinding

class MultMatrixActivity : AppCompatActivity() {

    var numberTypes = arrayOf("PROPER", "DEC")

    private var _binding: FragmentMultMatrixBinding? = null
    private val binding get() = _binding!!

    private var dimens = arrayOf("1", "2", "3", "4", "5", "6", "7")

    private var listMatr: ArrayList<EditText> = arrayListOf()
    private var listMatr2: ArrayList<EditText> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = FragmentMultMatrixBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            changeMatrix(N, M, linearLayoutCompatMatrix1, listMatr)
            changeMatrix(N, M, linearLayoutCompatMatrix2, listMatr2)

            spinnerTimes.adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, numberTypes)

            fun changeN(item: String) {
                N = item.toInt()
                changeMatrix(N, M, linearLayoutCompatMatrix1, listMatr)
            }
            fun changeM(item: String) {
                M = item.toInt()
                changeMatrix(N, M, linearLayoutCompatMatrix1, listMatr)
            }
            fun changeN2(item: String) {
                N2 = item.toInt()
                changeMatrix(N2, M2, linearLayoutCompatMatrix2, listMatr2)
            }
            fun changeM2(item: String) {
                M2 = item.toInt()
                changeMatrix(N2, M2, linearLayoutCompatMatrix2, listMatr2)
            }
            val adapter = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, dimens)

            mSize.adapter = adapter
            nSize.adapter = adapter
            mSize2.adapter = adapter
            nSize2.adapter = adapter

            val itemSelectedListenerN: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN)
            val itemSelectedListenerM: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM)
            val itemSelectedListenerN2: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN2)
            val itemSelectedListenerM2: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM2)

            mSize.onItemSelectedListener = itemSelectedListenerN
            nSize.onItemSelectedListener = itemSelectedListenerM
            mSize2.onItemSelectedListener = itemSelectedListenerN2
            nSize2.onItemSelectedListener = itemSelectedListenerM2

            timesButton.setOnClickListener {
                val leftArr: ArrayList<ArrayList<String?>> = ArrayList(N)
                val rightArr: ArrayList<ArrayList<String?>> = ArrayList(N2)
                var m = 0
                for (i in 0 until N) {
                    val row: ArrayList<String?> = ArrayList(M)
                    for (j in 0 until M) {
                        row.add(listMatr[m].text.toString())
                        m++
                    }
                    leftArr.add(row)
                }
                m = 0
                for (i in 0 until N2) {
                    val row: ArrayList<String?> = ArrayList(M2)
                    for (j in 0 until M2) {
                        row.add(listMatr2[m].text.toString())
                        m++
                    }
                    rightArr.add(row)
                }
                val answer = try {
                    Computer.times(leftArr, rightArr, numberType)
                } catch (matrix_fail: Computer.MATRIX_FAIL) {
                    "Матрица пуста"
                } catch (error: Computer.MATRIX_DIMENSION_MISSMATCH) {
                    "Вторая размерность первой матрицы не равна первой размерности второй. Умножение невозможно выполнить"
                } catch (field_error: Computer.FIELD_ERROR) {
                    "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
                }
                logMult.text = answer
            }
        }
    }
    private fun changeMatrix(N: Int, M: Int, v: LinearLayoutCompat, listMatr: ArrayList<EditText>) {
        v.removeAllViews()
        listMatr.clear()
        for (i in 0 until N) {
            val linearLayoutCompat = LinearLayoutCompat(applicationContext)
            linearLayoutCompat.orientation = LinearLayoutCompat.HORIZONTAL

            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            linearLayoutCompat.layoutParams = params
            for (j in 0 until M) {
                val params2 = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val editText = EditText(applicationContext)
                editText.apply {
                    hint = "A" + (i + 1) + (j + 1)
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                    width = 250 - M * 10
                    height = 250 - M * 10
                    textSize = 20f
                    params2.weight = 1f
                    layoutParams = params2
                }
                linearLayoutCompat.addView(editText)
                listMatr.add(editText)
            }
            v.addView(linearLayoutCompat)
        }
    }
    companion object {
        private var N = 1
        private var M = 1
        private var N2 = 1
        private var M2 = 1
        private var numberType = "PROPER"
    }
}
