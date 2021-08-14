package com.example.flamemathnew.ui.algebra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.viewpager.widget.ViewPager
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentAlgebraBinding
import com.example.flamemathnew.ui.adapters.MyPagerAdapter
import com.google.android.material.tabs.TabLayout

class AlgebraActivity : AppCompatActivity() {
    private lateinit var myPagerAdapter: MyPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private var _binding: FragmentAlgebraBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {

        myPagerAdapter =
            MyPagerAdapter(applicationContext, supportFragmentManager, "linal")
        super.onCreate(savedInstanceState)

        _binding = FragmentAlgebraBinding.inflate(layoutInflater)

        tabLayout = binding.tabs

        viewPager = binding.viewPagerAlgebra
        viewPager.setOffscreenPageLimit(3)
        viewPager.setAdapter(myPagerAdapter)

        tabLayout.setupWithViewPager(viewPager)
        setContentView(binding.root)
    }

}