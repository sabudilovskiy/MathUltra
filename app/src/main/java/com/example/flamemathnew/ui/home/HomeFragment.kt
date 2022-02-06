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

/*
 * @author Yana Glad
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val navController = findNavController()

        with(binding) {
            lexeme.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToLexemeFragment()
                navController.navigate(action)
            }

            algebrMath.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToPracticeAlgebraFragment()
                navController.navigate(action)
            }

            history.setOnClickListener {
                val action = HomeFragmentDirections.actionNavHomeToHistoryFragment()
                navController.navigate(action)
            }
        }
        return binding.root
    }
}
