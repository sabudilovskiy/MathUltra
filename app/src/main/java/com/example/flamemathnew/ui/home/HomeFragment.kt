package com.example.flamemathnew.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentAboutBinding
import com.example.flamemathnew.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val navController = findNavController()

        binding.lexeme.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToLexemeFragment()
            navController.navigate(action)
        }

        binding.algebrMath.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavAlgebra()
            navController.navigate(action)
        }

        binding.disrcMath.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToNavDiscrete()
            navController.navigate(action)
        }

        binding.history.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToHistoryFragment()
            navController.navigate(action)
        }

        return binding.root
    }

}