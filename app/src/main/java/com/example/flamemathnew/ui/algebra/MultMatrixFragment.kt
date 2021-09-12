package com.example.flamemathnew.ui.algebra

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.flamemathnew.R
import com.example.flamemathnew.back.parameters.Number
import com.example.flamemathnew.databinding.FragmentMultMatrixBinding
import com.example.flamemathnew.mid.Computer
import com.example.flamemathnew.mid.exceptions.FieldErrorException
import com.example.flamemathnew.mid.exceptions.MatrixDimensionMismatchException
import com.example.flamemathnew.mid.exceptions.MatrixFailException
import com.example.flamemathnew.ui.algebra.AlgebraHelper.Companion.getOnItemSelectListener


class MultMatrixFragment : Fragment() {


    private var dimens = arrayOf("1", "2", "3", "4", "5", "6", "7")

    private var _binding: FragmentMultMatrixBinding? = null
    private val binding get() = _binding!!
    private var listMatr: MutableList<EditText> = mutableListOf()
    private var listMatr2: MutableList<EditText> = mutableListOf()
   // var numberTypes = arrayOf("PROPER", "DEC")
    var numberTypes = arrayOf(Number.PROPER, Number.DEC)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    fun changeMatrix(N: Int, M: Int, v: LinearLayoutCompat, listMatr: MutableList<EditText>) {
        v.removeAllViews()
        listMatr.clear()
        for (i in 0 until N) {
            val linearLayoutCompat = LinearLayoutCompat(requireContext())
            linearLayoutCompat.orientation = LinearLayoutCompat.HORIZONTAL
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )

            linearLayoutCompat.layoutParams = params
            for (j in 0 until M) {
                val params2 = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                val editText = EditText(context)
                editText.hint = "A" + (i + 1) + (j + 1)
                editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                editText.width = 250 - M * 10
                editText.height = 250 - M * 10
                editText.textSize = 20f
                params2.weight = 1f
                editText.layoutParams = params2
                linearLayoutCompat.addView(editText)
                listMatr.add(editText)
            }
            v.addView(linearLayoutCompat)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState:
        Bundle?
    ): View {

        _binding = FragmentMultMatrixBinding.inflate(inflater, container, false)

        changeMatrix(N, M, binding.linearLayoutCompatMatrix1, listMatr)
        changeMatrix(N, M, binding.linearLayoutCompatMatrix2, listMatr2)
        binding.spinnerTimes.adapter =  ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, numberTypes)
        fun changeN(item: String){
            N = item.toInt()
            changeMatrix(N, M, binding.linearLayoutCompatMatrix1, listMatr)
        }

        fun changeM(item: String){
            M = item.toInt()
            changeMatrix(N, M, binding.linearLayoutCompatMatrix1, listMatr)
        }

        fun changeN2(item: String){
            N2 = item.toInt()
            changeMatrix(N2, M2, binding.linearLayoutCompatMatrix2, listMatr2)
        }

        fun changeM2(item: String){
            M2 = item.toInt()
            changeMatrix(N2, M2, binding.linearLayoutCompatMatrix2, listMatr2)
        }
        fun changeNumberTyper(item: String){
            MultMatrixFragment.numberType = Number.valueOf(item)
        }

        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, dimens)
      //  adapter.setDropDownViewResource(R.layout.my_spinner_item)

        binding.mSize.adapter = adapter
        binding.nSize.adapter = adapter
        binding.mSize2.adapter = adapter
        binding.nSize2.adapter = adapter

        val itemSelectedListenerN: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN)
        val itemSelectedListenerM: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM)
        val itemSelectedListenerN2: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN2)
        val itemSelectedListenerM2: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM2)
        val itemSelectedListenerNumberType : AdapterView.OnItemSelectedListener = getOnItemSelectListener (::changeNumberTyper)

        binding.mSize.onItemSelectedListener = itemSelectedListenerN
        binding.nSize.onItemSelectedListener = itemSelectedListenerM
        binding.mSize2.onItemSelectedListener = itemSelectedListenerN2
        binding.nSize2.onItemSelectedListener = itemSelectedListenerM2
        binding.timesButton.setOnClickListener {
            val left_arr : MutableList<MutableList<String?>> = MutableList(N) {i -> mutableListOf()}
            val right_arr : MutableList<MutableList<String?>> = MutableList(N2) {i -> mutableListOf()}
            var m = 0
            for (i in 0..N - 1) {
                val row: MutableList<String?> = MutableList(M) {i -> ""}
                for (j in 0..M - 1) {
                    row.add(listMatr[m].text.toString())
                    m++
                }
                left_arr.add(row)
            }
            m = 0
            for (i in 0..N2 - 1) {
                val row: MutableList<String?> = MutableList(M2) {i -> ""}
                for (j in 0..M2 - 1) {
                    row.add(listMatr2[m].text.toString())
                    m++
                }
                right_arr.add(row)
            }
            var answer: String
            try {
                answer = Computer.times(left_arr, right_arr, numberType)
            }
            catch (matrix_fail: MatrixFailException) {
                answer = "Матрица пуста"
            } catch (error: MatrixDimensionMismatchException) {
                answer = "Вторая размерность первой матрицы не равна первой размерности второй. Умножение невозможно выполнить"
            } catch (field_error: FieldErrorException) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            binding.logMult.text = answer
        }

        return binding.root
    }

    companion object {
        private var N = 1
        private var M = 1
        private var N2 = 1
        private var M2 = 1
        var numberType = Number.PROPER
    }
}