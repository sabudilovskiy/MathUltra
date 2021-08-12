package com.example.flamemathnew.ui.lexeme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentHomeBinding
import com.example.flamemathnew.databinding.FragmentLexemeBinding


class LexemeFragment : Fragment() {

    private var _binding: FragmentLexemeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLexemeBinding.inflate(inflater, container, false)

        return binding.root
    }

}