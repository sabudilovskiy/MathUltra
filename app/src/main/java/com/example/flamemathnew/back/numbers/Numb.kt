package com.example.flamemathnew.back.numbers


import com.example.flamemathnew.back.mathobject.Ring

abstract class Numb : Ring() {
    abstract operator fun compareTo(right : Numb) : Int
}