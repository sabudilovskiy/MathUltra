package com.example.flamemathnew.ui.lexeme


import Lexemes.ErrorHandler
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flamemathnew.databinding.FragmentLexemeBinding
import com.example.flamemathnew.ui.support.Support.Companion.computeLexemes
import java.util.*

class LexemeFragment : Fragment() {

    private val lexemeViewModelImpl: LexemeViewModelImpl by viewModels()

    private var _binding: FragmentLexemeBinding? = null
    private val binding get() = _binding!!
    private val keys = ArrayList<String?>()
    private val values = ArrayList<String?>()
    private val editTextsKey = ArrayList<EditText>()
    private val editTextsValues = ArrayList<EditText>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLexemeBinding.inflate(inflater, container, false)

        lexemeViewModelImpl.editTextLexeme.value = binding.editText.text.toString()
        lexemeViewModelImpl.editTextVals.value = 0
        lexemeViewModelImpl.editTextKeys.value = 0
        lexemeViewModelImpl.editTextValues.value = 0

        lexemeViewModelImpl.editTextArgs.observe(viewLifecycleOwner, {
            val temp: Int? = binding.editTextVar.text.toString().toIntOrNull()
            if (temp == null) lexemeViewModelImpl.editTextValues.value = 0
            else lexemeViewModelImpl.editTextValues.value = temp

            editTextsKey.clear()
            editTextsValues.clear()
            binding.keys.removeAllViews()
            binding.values.removeAllViews()
            for (i in 0 until lexemeViewModelImpl.editTextValues.value!!) {
                val editText = EditText(context)
                editText.setTextColor(Color.BLACK)
                editText.hint = "x" + (i + 1)
                editText.setHintTextColor(Color.GRAY)

                binding.keys.addView(editText)
                editTextsKey.add(editText)
                val editTextValue = EditText(context)
                editTextValue.setTextColor(Color.BLACK)
                editTextValue.hint = "0.5"
                editTextValue.setHintTextColor(Color.GRAY)

                binding.values.addView(editTextValue)
                editTextsValues.add(editTextValue)
            }

            lexemeViewModelImpl.editTextValues.value = editTextsValues.size

            for (i in editTextsValues.indices) {
                editTextsValues[i].doOnTextChanged { _, _, _, _ ->

                    if (editTextsValues[i].text.toString().isNotEmpty() && lexemeViewModelImpl.editTextVals.value != editTextsValues.size) {
                        lexemeViewModelImpl.editTextVals.value =
                            lexemeViewModelImpl.editTextVals.value!! + 1
                    } else if(editTextsKey[i].text.toString().isEmpty()) {
                        lexemeViewModelImpl.editTextVals.value =
                            lexemeViewModelImpl.editTextVals.value!! - 1
                    }

                    lexemeViewModelImpl.computeEnabled.value = (lexemeViewModelImpl.editTextKeys.value == editTextsKey.size
                            && lexemeViewModelImpl.editTextVals.value == editTextsValues.size
                            && lexemeViewModelImpl.editTextLexeme.value!!!="")
                }

                editTextsKey[i].doOnTextChanged { _, _, _, _ ->
                     if (editTextsKey[i].text.toString().isNotEmpty() && lexemeViewModelImpl.editTextKeys.value != editTextsKey.size) {
                        lexemeViewModelImpl.editTextKeys.value =
                            lexemeViewModelImpl.editTextKeys.value!! + 1
                    } else if(editTextsKey[i].text.toString().isEmpty()){
                        lexemeViewModelImpl.editTextKeys.value =
                            lexemeViewModelImpl.editTextKeys.value!! - 1
                    }

                    lexemeViewModelImpl.computeEnabled.value = (lexemeViewModelImpl.editTextKeys.value == editTextsKey.size
                            && lexemeViewModelImpl.editTextVals.value == editTextsValues.size
                            && lexemeViewModelImpl.editTextLexeme.value!!!="")
                }
            }

            lexemeViewModelImpl.computeEnabled.observe(viewLifecycleOwner, {
                binding.btnCompute.isEnabled = lexemeViewModelImpl.computeEnabled.value!!
            })
        })

        binding.editText.doOnTextChanged { _, _, _, _ ->
            lexemeViewModelImpl.editTextLexeme.value = binding.editText.text.toString()
            lexemeViewModelImpl.computeEnabled.value = (lexemeViewModelImpl.editTextKeys.value!=0 && lexemeViewModelImpl.editTextKeys.value == editTextsKey.size
                    && lexemeViewModelImpl.editTextVals.value == editTextsValues.size
                    && lexemeViewModelImpl.editTextLexeme.value!!!="")
        }

        binding.editTextVar.doOnTextChanged { _, _, _, _ ->
            lexemeViewModelImpl.editTextArgs.value = binding.editTextVar.text.toString()
            if(binding.editTextVar.text.toString() == "") {
                lexemeViewModelImpl.computeEnabled.value = false
            }

//            lexemeViewModelImpl.editTextKeys.value = 0
//            lexemeViewModelImpl.editTextVals.value = 0
         }


        binding.btnCompute.setOnClickListener {
            if (binding.btnCompute.isEnabled) {
                for (i in 0 until lexemeViewModelImpl.editTextValues.value!!) {
                    keys.add(editTextsKey[i].text.toString())
                    values.add(editTextsValues[i].text.toString())
                }
                val temp = computeLexemes(binding.editText, keys, values)
                binding.result.text = temp
                binding.floatingRestart.visibility = View.VISIBLE
            }
        }

        binding.floatingRestart.setOnClickListener {
            binding.result.text = ""
            binding.editText.setText("")
            binding.editTextVar.setText("")
            binding.keys.removeAllViews()
            binding.values.removeAllViews()
            editTextsKey.clear()
            editTextsValues.clear()
            keys.clear()
            values.clear()
            ErrorHandler.set_default()
            binding.floatingRestart.visibility = View.GONE
            binding.btnCompute.isEnabled = false
            lexemeViewModelImpl.editTextLexeme.value = ""
            lexemeViewModelImpl.editTextVals.value = 0
            lexemeViewModelImpl.editTextKeys.value = 0
            lexemeViewModelImpl.editTextValues.value = 0
            lexemeViewModelImpl.computeEnabled.value = false
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}