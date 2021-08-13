package com.example.flamemathnew.ui.algebra

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentAlgebraBinding
import com.example.flamemathnew.ui.adapters.MyPagerAdapter
import com.google.android.material.tabs.TabLayout

class AlgebraFragment : Fragment() {
    private var myPagerAdapter: MyPagerAdapter? = null
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private var _binding: FragmentAlgebraBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlgebraBinding.inflate(inflater, container, false)

        myPagerAdapter = MyPagerAdapter(requireContext(), requireActivity().supportFragmentManager, "linal")
        tabLayout = binding.tabs

        viewPager = binding.viewPagerAlgebra
        viewPager.setOffscreenPageLimit(3)
        viewPager.setAdapter(myPagerAdapter)
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}