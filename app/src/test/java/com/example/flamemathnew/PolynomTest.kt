package com.example.flamemathnew

import MathObject.Ring
import Number.createNumb
import Support.createSingleArrayList
import com.example.flamemathnew.back.Polynom.Polynom

fun main(){
    println("Введите n: ")
    val n : Int = readLine()?.toInt() ?: 0
    val arr : ArrayList<Ring> = createSingleArrayList({ createNumb(0.0)}, n)
    for (i in 0 until n) {
        println("Введите ${i+1} коэффициент: ")
        arr[i] = createNumb(readLine()!!.toDouble())
    }
    arr.reverse()
    val left = Polynom(arr, "x")
    val roots = left.solve()
    for (root in roots) println(root)
}