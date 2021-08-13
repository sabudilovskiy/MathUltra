package com.example.flamemathnew.ui.algebra

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.flamemathnew.R
import com.example.flamemathnew.databinding.FragmentAlgebraBinding
import com.example.flamemathnew.ui.adapters.MyPagerAdapter
import com.google.android.material.tabs.TabLayout

class AlgebraFragment : AppCompatActivity() {
    private lateinit var myPagerAdapter: MyPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    private var _binding: FragmentAlgebraBinding? = null
    private val binding get() = _binding!!

    val args: AlgebraFragmentArgs by navArgs()
    var key : String = "linal"

    override fun onCreate(savedInstanceState: Bundle?) {
        myPagerAdapter =
            MyPagerAdapter(applicationContext, supportFragmentManager, key)
        super.onCreate(savedInstanceState)
        args.let {
            key = it.key
        }

        Log.d("NE_PONIMAYU" , "1321ewrw124edq2rer21r3f342t43tt24t24t $key")
        _binding = FragmentAlgebraBinding.inflate(layoutInflater)

        tabLayout = binding.tabs

        viewPager = binding.viewPagerAlgebra
        viewPager.setOffscreenPageLimit(3)
        viewPager.setAdapter(myPagerAdapter)

        tabLayout.setupWithViewPager(viewPager)
        setContentView(binding.root)
    }
}