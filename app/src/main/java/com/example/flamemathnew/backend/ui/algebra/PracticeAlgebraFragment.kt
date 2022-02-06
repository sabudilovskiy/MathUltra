package com.example.flamemathnew.backend.ui.algebra

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentPracticeAlgebraBinding
import com.example.flamemathnew.ui.algebra.MultMatrixActivity
import com.example.flamemathnew.backend.ui.support.Support.Companion.computeDeterminant
import com.example.flamemathnew.backend.ui.support.Support.Companion.computeInversion
import com.example.flamemathnew.backend.ui.support.Support.Companion.computeRank
import com.example.flamemathnew.backend.ui.support.Support.Companion.computeSLE
import com.example.flamemathnew.backend.ui.support.UISupport.Companion.changeMatrix

fun getOnItemSelectListener(action: (item: String) -> Unit): AdapterView.OnItemSelectedListener {
    val listener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long,
        ) {
            val item = parent.getItemAtPosition(position) as String
            action(item)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
    return listener
}

class PracticeAlgebraFragment : Fragment() {
    private var _binding: FragmentPracticeAlgebraBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferences

    private var listMatr: ArrayList<EditText> = arrayListOf()
    private var listSle: ArrayList<EditText> = arrayListOf()

    private var dimens = arrayOf("1", "2", "3", "4", "5")
    private var detTypes = arrayOf("LAPLASS", "TRIANGLE", "SARUSS", "BASIC")
    private var inverseTypes = arrayOf("GAUSS", "ALGEBRAIC_COMPLEMENT")
    private var rankTypes = arrayOf("TRIANGLE", "MINORS")
    private var operations = arrayOf("Определитель", "Обратная", "Ранг")

    private fun updateSle(N: Int) {
        listSle.clear()
        binding.sleStolbec.removeAllViews()

        for (i in 0 until N) {
            val editText = EditText(context)
            editText.apply {
                hint = "$i"
                textAlignment = View.TEXT_ALIGNMENT_CENTER
                width = 250 - M * 10
                height = 250 - M * 10
                textSize = 35f
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            listSle.add(editText)

            binding.sleStolbec.addView(editText)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentPracticeAlgebraBinding.inflate(inflater, container, false)

        pref = requireContext().getSharedPreferences("TYPE_NUMBERS", Context.MODE_PRIVATE)
        Log.d("PREF_NUMBER_TAG", "Pref: ${pref.getString("TYPE_NUMBERS", "PROPER")}")

        TYPE_NUMBER = pref.getString("TYPE_NUMBERS", "PROPER")!!

        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, dimens)

        val adapterMain = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, operations)

        with(binding) {

            spinnerChoice.adapter = adapterMain

            spinnerTypeDet.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, detTypes)

            spinnerTypeInv.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, inverseTypes)

            spinnerTypeRank.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, rankTypes)

            mSize.adapter = adapter
            nSize.adapter = adapter

            fun changeN(item: String) {
                N = item.toInt()
                changeMatrix(listMatr, linearLayoutCompatMatrix, requireContext(), N, M)
                updateSle(N)
            }
            fun changeM(item: String) {
                M = item.toInt()
                changeMatrix(listMatr, linearLayoutCompatMatrix, requireContext(), N, M)
            }
            fun changeTypeDet(item: String) {
                if (spinnerTypeDet.visibility == View.VISIBLE)
                    TYPE_COMPUTE = item
            }
            fun changeTypeInv(item: String) {
                if (spinnerTypeInv.visibility == View.VISIBLE)
                    TYPE_COMPUTE = item
            }
            fun changeTypeRank(item: String) {
                if (spinnerTypeRank.visibility == View.VISIBLE)
                    TYPE_COMPUTE = item
            }

            fun operationTypeListener(item: String) {
                when (item) {
                    "Определитель" -> {
                        TYPE = "Определитель"
                        spinnerTypeRank.visibility = View.GONE
                        spinnerTypeInv.visibility = View.GONE
                        spinnerTypeDet.visibility = View.VISIBLE
                    }
                    "Обратная" -> {
                        TYPE = "Обратная"
                        spinnerTypeRank.visibility = View.GONE
                        spinnerTypeInv.visibility = View.VISIBLE
                        spinnerTypeDet.visibility = View.GONE
                    }
                    "Ранг" -> {
                        TYPE = "Ранг"
                        spinnerTypeRank.visibility = View.VISIBLE
                        spinnerTypeInv.visibility = View.GONE
                        spinnerTypeDet.visibility = View.GONE
                    }
                }
            }

            val itemSelectedListenerN: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN)
            val itemSelectedListenerM: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM)
            val itemOperationSelectedListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::operationTypeListener)
            val typeDetChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeDet)
            val typeInvChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeInv)
            val typeRankChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeRank)

            mSize.onItemSelectedListener = itemSelectedListenerN
            nSize.onItemSelectedListener = itemSelectedListenerM

            spinnerChoice.onItemSelectedListener = itemOperationSelectedListener

            spinnerTypeDet.onItemSelectedListener = typeDetChangeListener
            spinnerTypeInv.onItemSelectedListener = typeInvChangeListener
            spinnerTypeRank.onItemSelectedListener = typeRankChangeListener

            changeMatrix(listMatr, linearLayoutCompatMatrix, requireContext(), N, M)

            compute.setOnClickListener {
                if (sleStolbec.visibility == View.VISIBLE) {
                    log.text = computeSLE(readMatrixFromEditTextExpanded(), TYPE_COMPUTE, TYPE_NUMBER, N, M)
                } else {
                    when (TYPE) {
                        "Определитель" -> {
                            log.text = computeDeterminant(
                                readMatrixFromEditText(),
                                TYPE_COMPUTE,
                                "DEC",
                                N,
                                M
                            )
                        }
                        "Обратная" -> {
                            log.text = computeInversion(readMatrixFromEditText(), TYPE_COMPUTE, TYPE_NUMBER, N, M)
                        }
                        "Ранг" -> {
                            log.text = computeRank(readMatrixFromEditText(), TYPE_COMPUTE, TYPE_NUMBER, N, M)
                        }
                    }
                }
            }

           plusSle.setOnClickListener {
                if (sleStolbec.visibility == View.VISIBLE) {
                    sleStolbec.visibility = View.GONE

                    spinnerTypeRank.visibility = View.GONE
                    spinnerTypeInv.visibility = View.GONE
                    spinnerTypeDet.visibility = View.VISIBLE
                    spinnerChoice.visibility = View.VISIBLE

                } else {
                    sleStolbec.visibility = View.VISIBLE
                    spinnerTypeRank.visibility = View.GONE
                    spinnerTypeInv.visibility = View.GONE
                    spinnerTypeDet.visibility = View.GONE
                    spinnerChoice.visibility = View.GONE
                }
                updateSle(N)
            }

            multMatr.setOnClickListener {
                val intent = Intent(activity, MultMatrixActivity::class.java)
                startActivity(intent)
            }
        }
        return binding.root
    }

    private fun readMatrixFromEditText(): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in listMatr.indices) list.add(listMatr[i].text.toString())
        return list
    }

    private fun readMatrixFromEditTextExpanded(): ArrayList<String> {
        val list = ArrayList<String>()

        for (i in listMatr.indices) list.add(listMatr[i].text.toString())

        for (i in 0 until N) list.add(listSle[i].text.toString())

        return list
    }

    companion object {
        private var N = 3
        private var M = 3
        private var TYPE = "Определитель"
        private var TYPE_COMPUTE = "LAPLASS"
        private var TYPE_NUMBER = "PROPER"
    }
}
