package com.example.flamemathnew.ui.algebra

import MRV.MRV
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
import androidx.navigation.fragment.findNavController
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentPracticeAlgebraBinding


fun getOnItemSelectListener(action: (item: String) -> Unit): AdapterView.OnItemSelectedListener {
    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            val item = parent.getItemAtPosition(position) as String
            action(item)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    return listener
}

class PracticeAlgebraFragment : Fragment() {

    private var matrixLayout: LinearLayoutCompat? = null

    private var listMatr: ArrayList<EditText> = arrayListOf()
    private var listSle: ArrayList<EditText> = arrayListOf()

    private var dimens = arrayOf("1", "2", "3", "4", "5")
    private var detTypes = arrayOf("LAPLASS", "TRIANGLE", "SARUSS", "BASIC")
    var inverseTypes = arrayOf("GAUSS", "ALGEBRAIC_COMPLEMENT")
    var rankTypes = arrayOf("TRIANGLE", "MINORS")
    var operations = arrayOf("Determinant", "Inverse", "Rank")
    var numberTypes = arrayOf("PROPER", "DEC")

    fun updateSle(N : Int) {
        listSle.clear()
        binding.sleStolbec.removeAllViews()

        val i = 0
        for (i in 0 until N){
            val editText = EditText(context)
            editText.hint = "$i"
            editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
            editText.width = 250 - M * 10
            editText.height = 250 - M * 10
            editText.textSize = 20f
            listSle.add(editText)

            binding.sleStolbec.addView(editText)
        }
    }


    private var _binding: FragmentPracticeAlgebraBinding? = null
    private val binding get() = _binding!!

    fun changeMatrix(N : Int, M: Int) {
        binding.linearLayoutCompatMatrix.removeAllViews()
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
            binding.linearLayoutCompatMatrix.addView(linearLayoutCompat)
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPracticeAlgebraBinding.inflate(inflater, container, false)

        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, dimens)
      //  adapter.setDropDownViewResource(R.layout.my_spinner_item)

        binding.mSize.adapter = adapter
        binding.nSize.adapter = adapter
        binding.spinnerDet.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, detTypes)
        binding.spinnerInv.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            inverseTypes
        )
        binding.spinnerRank.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, rankTypes)
        binding.spinnerTypeNumbers.adapter =  ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, numberTypes)

        fun changeN(item: String) {
            N = item.toInt()
            changeMatrix(N, M)
            updateSle(N)
        }

        fun changeM(item: String) {
            M = item.toInt()
            changeMatrix(N, M)
        }

        fun changeTypeDet(item : String){
            typeDet = item
        }

        fun changeTypeInv(item: String){
            typeInv = item
        }

        fun changeTypeRank(item: String){
            typeRank = item
        }

        fun changeNumberTyper(item: String){
            numbersType = item
        }
        val itemSelectedListenerN: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN)
        val itemSelectedListenerM: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM)
        val itemSelectedListenerDET: AdapterView.OnItemSelectedListener = getOnItemSelectListener (::changeTypeDet)
        val itemSelectedListenerINV: AdapterView.OnItemSelectedListener = getOnItemSelectListener (::changeTypeInv)
        val itemSelectedListenerRANK : AdapterView.OnItemSelectedListener = getOnItemSelectListener (::changeTypeRank)
        val itemSelectedListenerNumberType : AdapterView.OnItemSelectedListener = getOnItemSelectListener (::changeNumberTyper)

        binding.mSize.onItemSelectedListener = itemSelectedListenerN
        binding.nSize.onItemSelectedListener = itemSelectedListenerM
        binding.spinnerDet.onItemSelectedListener = itemSelectedListenerDET
        binding.spinnerInv.onItemSelectedListener = itemSelectedListenerINV
        binding.spinnerRank.onItemSelectedListener = itemSelectedListenerRANK
        binding.spinnerTypeNumbers.onItemSelectedListener = itemSelectedListenerNumberType


        changeMatrix(N, M)

        binding.detButton.setOnClickListener {
            val list: ArrayList<ArrayList<String?>> = ArrayList(N)
            var m = 0
            for (i in 0 until N) {
                val row: ArrayList<String?> = ArrayList<String?>(M)
                for (j in 0 until M) {
                    row.add(listMatr[m].text.toString())
                    m++
                }
                list.add(row)
            }
            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + MRV.count_determinant(list, typeDet, numbersType) + "\n"
                log = MRV.get_log_as_str()
            } catch (matrix_fail: MRV.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: MRV.NON_QUADRATIC_MATRIX) {
                answer = "Матрица не является квадратной. Вычислить определитель невозможно."
            } catch (field_error: MRV.FIELD_ERROR) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            //TODO("Вывод необходимо переделать")
            binding.log.text = answer + log
        }


        binding.invButton.setOnClickListener {
            val list: ArrayList<ArrayList<String?>> = ArrayList(N)
            var m = 0
            for (i in 0..N - 1) {
                val row: ArrayList<String?> = ArrayList(M)
                for (j in 0..M - 1) {
                    row.add(listMatr[m].text.toString())
                    m++
                }
                list.add(row)
            }
            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + MRV.find_inverse_matrix(list, typeInv, numbersType) + "\n"
                log = MRV.get_log_as_str()
            } catch (matrix_fail: MRV.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (non_quadratic_matrix: MRV.NON_QUADRATIC_MATRIX) {
                answer = "Матрица не является квадратной. Вычислить обратную невозможно."
            } catch (field_error: MRV.FIELD_ERROR) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            } catch (degenerate_matrix: MRV.DEGENERATE_MATRIX) {
                answer = "Матрица является вырожденной. Нахождение обратной невозможно."
            }
            //TODO("Вывод необходимо переделать")
            binding.log.text = answer + log
        }


        binding.rankButton.setOnClickListener {
            val list: ArrayList<ArrayList<String?>> = ArrayList(N)
            var m = 0
            for (i in 0 until N) {
                val row: ArrayList<String?> = ArrayList<String?>(M)
                for (j in 0 until M) {
                    row.add(listMatr[m].text.toString())
                    m++
                }
                list.add(row)
            }
            var answer: String
            var log = ""
            try {
                answer = "Ответ: " + MRV.count_rank(list, typeRank, numbersType) + "\n"
                log = MRV.get_log_as_str()
            } catch (matrix_fail: MRV.MATRIX_FAIL) {
                answer = "Матрица пуста"
            } catch (field_error: MRV.FIELD_ERROR) {
                answer = "Допущена ошибка в вводе A" + (field_error.i + 1) + (field_error.j + 1)
            }
            //TODO("Вывод необходимо переделать")
            binding.log.text = answer + log
        }

//        val navController = findNavController()
//
//        binding.goMultMatrix.setOnClickListener{
//            navController.navigate(R.id.action_nav_gallery_to_multMatrixFragment2)
//        }

        binding.plusSle.setOnClickListener {
            if(binding.sleStolbec.visibility == View.VISIBLE)
                binding.sleStolbec.visibility = View.GONE
            else binding.sleStolbec.visibility = View.VISIBLE
            updateSle(N)
        }

        return binding.root
    }


    companion object {
        private var N = 3
        private var M = 3
        private var typeDet: String = "LAPLASS"
        private var typeRank: String = "TRIANGLE"
        private var typeInv: String = "GAUSS"
        private var numbersType = "PROPER"

    }
}