package com.example.flamemathnew

import com.example.flamemathnew.backend.back.MathObject.Ring
import com.example.flamemathnew.backend.back.Numbers.createNumb
import com.example.flamemathnew.backend.back.Support.createSingleArrayList
import com.example.flamemathnew.back.polynom.Polynom

fun main(){
    println("Введите n: ")
    val n : Int = readLine()?.toInt() ?: 0
    val arr : ArrayList<Ring> = createSingleArrayList({ createNumb(0.0) }, n)
    for (i in 0 until n) {
        println("Введите ${i+1} коэффициент: ")
        arr[i] = createNumb(readLine()!!.toDouble())
    }
    arr.reverse()
    val left = Polynom(arr, "x")
    val roots = left.solve()
    for (root in roots) println(root)
}