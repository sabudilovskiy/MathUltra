package com.example.flamemathnew.ui.algebra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flamemathnew.databinding.FragmentAlgebraBinding

class AlgebraFragment : Fragment() {

    private var _binding: FragmentAlgebraBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlgebraBinding.inflate(inflater, container, false)


        return  binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}