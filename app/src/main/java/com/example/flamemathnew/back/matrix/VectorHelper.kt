package com.example.flamemathnew.back.matrix

import com.example.flamemathnew.back.mathobject.Ring
import com.example.flamemathnew.back.numbers.NumbHelper.Companion.createNumb
import com.example.flamemathnew.back.support.Support.Companion.createRectangleArrayList

class VectorHelper
{
    companion object
    {
        public fun createVectorStr(cords: MutableList<Ring>): Matrix {
            val temp: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, 1, cords.size)
            for (i in 0 until cords.size) temp[0][i] = cords[i]
            return Matrix(temp)
        }

        public fun createVectorCol(cords: MutableList<Ring>): Matrix {
            val temp: MutableList<MutableList<Ring>> =
                createRectangleArrayList({ createNumb(0.0) }, cords.size, 1)
            for (i in 0 until cords.size) temp[i][0] = cords[i]
            return Matrix(temp)
        }
    }
}