package com.example.flamemathnew.back.lexemes

object Support {
    fun union(left: MutableList<Double>, right: MutableList<Double>): MutableList<Double> {
        return if (left.size != 0) {
            if (right.size == 0) left else {
                for (element in right) {
                    left.add(element)
                }
                left
            }
        } else right
    }

    fun isNumeral(a: Char): Boolean {
        return '0' <= a && a <= '9'
    }
}