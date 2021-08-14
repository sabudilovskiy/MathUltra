package com.example.flamemathnew

import Number.createNumber
import Support.createSingleArrayList
import com.example.flamemathnew.back.Polynom.Polynom
import org.junit.Test

fun main(){
    println("Введите n: ")
    val n : Int = readLine()?.toInt() ?: 0
    println("Введите n2: ")
    val n2 : Int = readLine()?.toInt() ?: 0
    val arr = createSingleArrayList({ createNumber(0.0)}, n)
    for (i in 0 until n) {
        println("Введите ${i+1} коэффициент: ")
        arr[i] = createNumber(readLine()!!.toDouble())
    }

    val temp_arr = createSingleArrayList({ createNumber(0.0)}, n2)
    for (j in 0 until n2) {
        println("Введите ${j+1} коэффициент: ")
        temp_arr[j] = createNumber(readLine()!!.toDouble())
    }
    val left = Polynom(arr)
    val right = Polynom(temp_arr)
    val result = left/right
    println("end")
}