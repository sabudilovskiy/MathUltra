package com.example.flamemathnew.ui.support

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat

class UISupport {

    companion object {
        fun changeMatrix(
            listMatr: MutableList<EditText>,
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
                    editText.hint = "A" + (i + 1) + (j + 1)
                    editText.textAlignment = View.TEXT_ALIGNMENT_CENTER
                    editText.width = 250 - M * 10
                    editText.height = 250 - M * 10
                    editText.textSize = 20f
                    params2.weight = 1f
                    editText.layoutParams = params2
                    linearLayoutCompat.addView(editText)
                    listMatr.add(editText)
                }
                linearLayout.addView(linearLayoutCompat)
            }
        }
    }
}