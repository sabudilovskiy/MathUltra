package com.example.flamemathnew.ui.adapters

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flamemathnew.ui.algebra.PracticeAlgebraFragment
import com.example.flamemathnew.ui.algebra.TheoryAlgebraFragment

class MyPagerAdapter(private val context: Context, manager: FragmentManager?, val key : String)
    : FragmentPagerAdapter(manager!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var type = "Практика"
        when (position) {
            0 -> type = "Практика"
            1 -> type = "Теория"
        }

        when(key){
            "linal"->{
                return if(type == "Практика") return PracticeAlgebraFragment() else return TheoryAlgebraFragment()
            }
        }
        return ActionFragment.newInstance(type)
    }


    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Практика"
            1 -> return "Теория"
        }
        Log.e("PagerAdapter getItem", "Error at position$position")
        return "Практика"

    }
}