package com.example.flamemathnew.ui.algebra

import android.view.View
import android.widget.AdapterView

class AlgebraHelper {
    companion object
    {
        fun getOnItemSelectListener(action: (item: String) -> Unit): AdapterView.OnItemSelectedListener {
            val listener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    val item = parent.getItemAtPosition(position) as String
                    action(item)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            return listener
        }
    }
}