package com.example.flamemathnew.backend.ui.support

import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat

class UISupport {

    companion object {
        fun changeMatrix(
            listMatr: ArrayList<EditText>,
            linearLayout: LinearLayoutCompat,
            context: Context,
            N: Int,
            M: Int
        ) {
            linearLayout.removeAllViews()
            listMatr.clear()
            for (i in 0 until N) {
                val linearLayoutCompat = LinearLayoutCompat(context)
                linearLayoutCompat.orientation = LinearLayoutCompat.HORIZONTAL
                val params = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )

                linearLayoutCompat.layoutParams = params
                for (j in 0 until M) {
                    val params2 = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    val editText = EditText(context)
                    editText.apply {
                        hint = " ${(i + 1)} ${(j + 1)}"
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        width = 250 - M * 10
                        height = 250 - M * 10
                        textSize = 35f
                        params2.weight = 1f
                        layoutParams = params2
                        inputType = InputType.TYPE_CLASS_NUMBER
                    }
                    linearLayoutCompat.addView(editText)
                    listMatr.add(editText)
                }
                linearLayout.addView(linearLayoutCompat)
            }
        }
    }
}