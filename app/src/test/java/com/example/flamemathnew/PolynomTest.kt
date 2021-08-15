package com.example.flamemathnew

import Number.createNumb
import Number.findDividers
import Support.createSingleArrayList
import com.example.flamemathnew.back.Polynom.Polynom

fun main(){
    println("Введите число")
    val n : Long = readLine()!!.toLong()
    val arr = findDividers(createNumb(n))
    for (l in arr) println(l)
}