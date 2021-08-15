package com.example.flamemathnew

import Number.createNumb
import Support.createSingleArrayList
import com.example.flamemathnew.back.Polynom.Polynom

fun main(){
    println("Введите n: ")
    val n : Int = readLine()?.toInt() ?: 0
    println("Введите n2: ")
    val n2 : Int = readLine()?.toInt() ?: 0
    val arr = createSingleArrayList({ createNumb(0.0)}, n)
    for (i in 0 until n) {
        println("Введите ${i+1} коэффициент: ")
        arr[i] = createNumb(readLine()!!.toDouble())
    }

    val temp_arr = createSingleArrayList({ createNumb(0.0)}, n2)
    for (j in 0 until n2) {
        println("Введите ${j+1} коэффициент: ")
        temp_arr[j] = createNumb(readLine()!!.toDouble())
    }
    val left = Polynom(arr, "x")
    val right = Polynom(temp_arr, "x")
    val result = left/right
    println("end")
}