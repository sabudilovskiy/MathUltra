package com.example.flamemathnew

import com.example.flamemathnew.backend.back.Logger.Log
import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.Matrix.Matrix
import com.example.flamemathnew.backend.back.Numbers.createNumb
import com.example.flamemathnew.backend.back.Support.createRectangleArrayList

fun main(){
    println("Введите m")
    val m : Int = readLine()!!.toInt()
    println("Введите n")
    val n : Int = readLine()!!.toInt()
    val arr : ArrayList<ArrayList<Ring>> = createRectangleArrayList({ createNumb(0.0) }, m,n)
    for (i in 0 until m){
        println("Введите ${i+1} строку")
        val temp = readLine()
        val temp_arr = temp!!.split(" ")
        for (j in 0 until n) arr[i][j] = createNumb(temp_arr[j].toDouble())
    }
    val matrix = Matrix(arr)
    val jordan = matrix.findJordanForm()
    val logger = Log.get_log()
    for (i in logger.indices) {
        val temp : String = logger[i]
        println(temp)
    }
}