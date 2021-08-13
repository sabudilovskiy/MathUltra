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
import androidx.navigation.fragment.findNavController
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentPracticeAlgebraBinding
import com.example.flamemathnew.ui.support.Support.Companion.computeDeterminant
import com.example.flamemathnew.ui.support.Support.Companion.computeInversion
import com.example.flamemathnew.ui.support.Support.Companion.computeRank


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
    var operations = arrayOf("Определитель", "Обратная", "Ранг")
    var numberTypes = arrayOf("PROPER", "DEC")

    fun updateSle(N: Int) {
        listSle.clear()
        binding.sleStolbec.removeAllViews()

        val i = 0
        for (i in 0 until N) {
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

    fun changeMatrix(N: Int, M: Int) {
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

        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, dimens)

        val adapterMain = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            operations
        )
        binding.spinnerChoice.adapter = adapterMain
        binding.spinnerTypeDet.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            detTypes
        )

        binding.spinnerTypeInv.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            inverseTypes
        )

        binding.spinnerTypeRank.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            rankTypes
        )


        //  adapter.setDropDownViewResource(R.layout.my_spinner_item)

        binding.mSize.adapter = adapter
        binding.nSize.adapter = adapter

        binding.spinnerTypeNumbers.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            numberTypes
        )

        fun changeN(item: String) {
            N = item.toInt()
            changeMatrix(N, M)
            updateSle(N)
        }

        fun changeM(item: String) {
            M = item.toInt()
            changeMatrix(N, M)
        }


        fun changeNumberTyper(item: String) {
            numbersType = item
        }

        fun changeTypeDet(item: String){
            if(binding.spinnerTypeDet.visibility == View.VISIBLE)
                TYPE_COMPUTE = item
        }
        fun changeTypeInv(item: String){
            if(binding.spinnerTypeInv.visibility == View.VISIBLE)
                TYPE_COMPUTE = item
        }

        fun changeTypeRank(item: String){
            if(binding.spinnerTypeRank.visibility == View.VISIBLE)
                TYPE_COMPUTE = item
        }

        fun operationTypeListener(item: String) {
            when (item) {
                "Определитель" -> {
                    TYPE = "Определитель"
                    binding.spinnerTypeRank.visibility = View.GONE
                    binding.spinnerTypeInv.visibility = View.GONE
                    binding.spinnerTypeDet.visibility = View.VISIBLE
                }
                "Обратная" -> {
                    TYPE = "Обратная"
                    binding.spinnerTypeRank.visibility = View.GONE
                    binding.spinnerTypeInv.visibility = View.VISIBLE
                    binding.spinnerTypeDet.visibility = View.GONE
                }
                "Ранг" -> {
                    TYPE = "Ранг"
                    binding.spinnerTypeRank.visibility = View.VISIBLE
                    binding.spinnerTypeInv.visibility = View.GONE
                    binding.spinnerTypeDet.visibility = View.GONE
                }
            }
        }

        val itemSelectedListenerN: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeN)
        val itemSelectedListenerM: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeM)
        val itemSelectedListenerNumberType: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeNumberTyper)
        val intemOperationSelectedListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::operationTypeListener)
        val typeDetChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeDet)
        val typeInvChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeInv)
        val typeRankChangeListener: AdapterView.OnItemSelectedListener = getOnItemSelectListener(::changeTypeRank)

        binding.mSize.onItemSelectedListener = itemSelectedListenerN
        binding.nSize.onItemSelectedListener = itemSelectedListenerM

        binding.spinnerTypeNumbers.onItemSelectedListener = itemSelectedListenerNumberType
        binding.spinnerChoice.onItemSelectedListener = intemOperationSelectedListener

        binding.spinnerTypeDet.onItemSelectedListener = typeDetChangeListener
        binding.spinnerTypeInv.onItemSelectedListener = typeInvChangeListener
        binding.spinnerTypeRank.onItemSelectedListener = typeRankChangeListener

        changeMatrix(N, M)


        fun readMatrixFromEditText() : ArrayList<String> {
            var list = ArrayList<String>()
            for (i in listMatr.indices) {
                list.add(listMatr[i].text.toString())
            }
            return list
        }

        binding.compute.setOnClickListener {
            when(TYPE){
                "Определитель" -> {
                    binding.log.text = computeDeterminant(readMatrixFromEditText(), TYPE_COMPUTE, numbersType, N, M)
                }
                "Обратная" -> {
                    binding.log.text = computeInversion(readMatrixFromEditText(), TYPE_COMPUTE, numbersType, N, M)
                }
                "Ранг" -> {
                    binding.log.text = computeRank(readMatrixFromEditText(), TYPE_COMPUTE, numbersType, N, M)
                }
            }
        }


//        val navController = findNavController()
//
//        binding.goMultMatrix.setOnClickListener{
//            navController.navigate(R.id.action_nav_gallery_to_multMatrixFragment2)
//        }

        binding.plusSle.setOnClickListener {
            if (binding.sleStolbec.visibility == View.VISIBLE)
                binding.sleStolbec.visibility = View.GONE
            else binding.sleStolbec.visibility = View.VISIBLE
            updateSle(N)
        }

        return binding.root
    }


    companion object {
        private var N = 3
        private var M = 3
        private var numbersType = "PROPER"
        private var TYPE = "Определитель"
        private var TYPE_COMPUTE = "LAPLASS"
    }
}