package com.example.flamemathnew.ui.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentSettingsBinding
import com.example.flamemathnew.ui.algebra.AlgebraHelper.Companion.getOnItemSelectListener


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private var numberTypes = arrayOf("PROPER", "DEC")

    var settings: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settings = requireContext().getSharedPreferences("TYPE_NUMBERS", MODE_PRIVATE);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)


        fun changeTypeNumbers(item: String) {
            TYPE_NUMBERS = item
            val prefEditor = settings!!.edit()
            prefEditor.putString("TYPE_NUMBERS", item)
            prefEditor.apply()
        }

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            getOnItemSelectListener(::changeTypeNumbers)

        val pref = requireContext().getSharedPreferences("TYPE_NUMBERS", Context.MODE_PRIVATE).getString("TYPE_NUMBERS","PROPER")
        when (pref){
            "PROPER"-> numberTypes = arrayOf("PROPER", "DEC")
            else -> numberTypes = arrayOf("DEC", "PROPER")
        }
        binding.spinnerTypeNumbers.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            numberTypes
        )
        binding.spinnerTypeNumbers.onItemSelectedListener = itemSelectedListener

        return binding.root
    }

    companion object {
        var TYPE_NUMBERS = "PROPER"
    }

}