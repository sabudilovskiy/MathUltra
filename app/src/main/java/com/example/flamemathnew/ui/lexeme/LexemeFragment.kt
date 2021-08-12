package com.example.flamemathnew.ui.lexeme


import Lexemes.ErrorHandler
import Lexemes.Log
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.flamemathnew.databinding.FragmentLexemeBinding
import com.example.flamemathnew.ui.support.Support.Companion.computeLexemes

import java.util.ArrayList

class LexemeFragment : Fragment() {

    private val lexemeViewModelImpl: LexemeViewModelImpl by viewModels()

    private var _binding: FragmentLexemeBinding? = null
    private val binding get() = _binding!!
    private var N = 1
    private val keys = ArrayList<String?>()
    private val values = ArrayList<String?>()
    private val editTextsKey = ArrayList<EditText>()
    private val editTextsValue = ArrayList<EditText>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLexemeBinding.inflate(inflater, container, false)

        lexemeViewModelImpl.editTextLexeme.value = binding.editText.text.toString()

        lexemeViewModelImpl.editTextArgs.observe(viewLifecycleOwner, {
            val temp: Int? = binding.editTextVar.getText().toString().toIntOrNull()
            if (temp == null) N = 0
            else N = temp
            binding.keys.removeAllViews()
            binding.values.removeAllViews()
            for (i in 0 until N) {
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
                editTextValue.doOnTextChanged { text, start, before, count ->
                    Log.d("CHANGED_AAA_A", "Change : $i")
                }


                binding.values.addView(editTextValue)
                editTextsValue.add(editTextValue)
            }
            if (N > 0) binding.btnCompute.isEnabled = true
        })

        //        info.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (aboutImage.getVisibility() == View.GONE)
//                    aboutImage.setVisibility(View.VISIBLE);
//                else aboutImage.setVisibility(View.GONE);
//            }
//        });

        binding.editText.doOnTextChanged { text, start, before, count ->
            lexemeViewModelImpl.editTextLexeme.value = binding.editText.toString()
        }

        binding.editTextVar.doOnTextChanged { text, start, before, count ->
            lexemeViewModelImpl.editTextArgs.value = binding.editTextVar.toString()
        }

        binding.btnCompute.setEnabled(false)


        binding.btnCompute.setOnClickListener { v: View? ->
            for (i in 0 until N) {
                keys.add(editTextsKey[i].text.toString())
                values.add(editTextsValue[i].text.toString())
            }
            val temp = computeLexemes(binding.editText, keys, values)
            binding.result.text = temp
            binding.floatingRestart.visibility = View.VISIBLE
        }

        binding.floatingRestart.setOnClickListener(View.OnClickListener { // step++;
            binding.result.setText("")
            binding.editText.setText("")
            binding.editTextVar.setText("")
            binding.keys.removeAllViews()
            binding.values.removeAllViews()
            editTextsKey.clear()
            editTextsValue.clear()
            keys.clear()
            values.clear()
            ErrorHandler.set_default()
            binding.floatingRestart.setVisibility(View.GONE)
            binding.btnCompute.isEnabled = false
         })


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun redText(error_begin: Int, error_end: Int) {
        val full = binding.editText.text.toString()
        binding.editText.setText("")
        val word0: Spannable
        println("Error begin : $error_begin error end : $error_end")
        if (error_begin != 0) {
            word0 = SpannableString(full.substring(0, error_begin))
            word0.setSpan(
                ForegroundColorSpan(Color.BLACK),
                0,
                word0.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else word0 = SpannableString("")
        println("Word 0 : $word0")
        binding.editText.setText(word0)
        val word: Spannable = SpannableString(full.substring(error_begin, error_end + 1))
        word.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            word.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        println("Word1: $word")
        binding.editText.append(word)
        val wordTwo: Spannable = SpannableString(full.substring(error_end + 1, full.length))
        println("Error end: " + error_end + " len " + full.length)
        println("Word2 : $wordTwo")
        wordTwo.setSpan(
            ForegroundColorSpan(Color.BLACK),
            0,
            wordTwo.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.editText.append(wordTwo)
    }
}