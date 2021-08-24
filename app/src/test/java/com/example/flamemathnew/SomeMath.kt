package com.example.flamemathnew

import Lexemes.Archieve
import Lexemes.Lexeme
import Lexemes.Sentence
import Support.createSingleArrayList

fun main(){
    println("Введите количество примеров:")
    val n : Int = readLine()!!.toInt()
    var temp = ""
    Archieve(arrayListOf("x"))
    for (j in 0 until n ){
        var current_summ = 0
        val signs : ArrayList<Char> = arrayListOf()
        val numbers : ArrayList<Int> = arrayListOf()
        var count : Int = 0
        var current : Int = 1
        numbers.add((1..100).random())
        var previous : Int = numbers[0]
        current_summ = numbers[0]
        while (current < 4){
            val canBeSign : ArrayList<Boolean> = createSingleArrayList({false}, 4)
            canBeSign[0] = (current_summ < 95)
            canBeSign[1] = (current_summ > 10)
            if ((count < 0 && previous < 10)||(count > 0 && numbers[current-1] < 10)){
                for (k in 2 until 10){
                    temp = "${numbers[0]} "
                    for (f in 1 until numbers.size){
                        temp+="${signs[f-1]} ${numbers[f]} "
                    }
                    temp+="*$k"
                    val temp_sentence = Sentence(temp)
                    val temp_new_summ = temp_sentence.count().get_value().toInt()
                    if (temp_new_summ < 100 && temp_new_summ > -1) {
                        canBeSign[2] = true
                        break
                    }
                }
            }
            else canBeSign[2] = false
            if (numbers[current-1] > 10) for (k in 2 until 10){
                if (numbers[current-1] / k < 10 && numbers[current-1] % k == 0){
                    canBeSign[3] = true
                    break
                }
            }
            var buffer = (1..100).random()
            if (buffer <= 5)buffer = 1
            else if (buffer<=10)buffer = 2
            else if (buffer<=55)buffer = 3
            else buffer = 4
            while (!canBeSign[buffer - 1]) {
                buffer = (1..100).random()
                if (buffer <= 5)buffer = 1
                else if (buffer<=10)buffer = 2
                else if (buffer<=55)buffer = 3
                else buffer = 4
            }
            signs.add(when(buffer){
                1 -> '+'
                2 -> '-'
                3 -> '*'
                else -> ':'
            })
            when (signs[current-1]) {
                '+' -> {
                    numbers.add((2 until 100 - current_summ).random())
                    temp = "${numbers[0]} "
                    for (f in 1 until numbers.size){
                        temp+="${signs[f-1]} ${numbers[f]} "
                    }
                    temp+="+${numbers[current]}"
                    var temp_sentence = Sentence(temp)
                    var temp_new_summ = temp_sentence.count().get_value().toInt()
                    while (temp_new_summ > 100 || temp_new_summ < 0){
                        numbers[current] = (2 until 100-current_summ).random()
                        temp = "${numbers[0]} "
                        for (f in 1 until numbers.size){
                            temp+="${signs[f-1]} ${numbers[f]} "
                        }
                        temp_sentence = Sentence(temp)
                        temp_new_summ = temp_sentence.count().get_value().toInt()
                    }
                    if (count < 0) {
                        previous = numbers[current]
                        count = 1
                    }
                    else {
                        count++
                        previous += numbers[current]
                    }
                }
                '-' -> {
                    numbers.add((2 until current_summ).random())
                    temp = "${numbers[0]} "
                    for (f in 1 until numbers.size){
                        temp+="${signs[f-1]} ${numbers[f]} "
                    }
                    temp+="-${numbers[current]}"
                    var temp_sentence = Sentence(temp)
                    var temp_new_summ = temp_sentence.count().get_value().toInt()
                    while (temp_new_summ > 100 || temp_new_summ < 0){
                        numbers[current] = (2 until current_summ).random()
                        temp = "${numbers[0]} "
                        for (f in 1 until numbers.size){
                            temp+="${signs[f-1]} ${numbers[f]} "
                        }
                        temp_sentence = Sentence(temp)
                        temp_new_summ = temp_sentence.count().get_value().toInt()
                    }
                    if (count < 0) {
                        previous = numbers[current]
                        count = 1
                    }
                    else {
                        count++
                        previous -= numbers[current]
                    }
                }
                '*' -> {
                    numbers.add( (2..10).random())
                    temp = "${numbers[0]} "
                    for (f in 1 until numbers.size){
                        temp+="${signs[f-1]} ${numbers[f]} "
                    }
                    temp+="*${numbers[current]}"
                    var temp_sentence = Sentence(temp)
                    var temp_new_summ = temp_sentence.count().get_value().toInt()
                    while (temp_new_summ > 100 || temp_new_summ < 0){
                        numbers[current] = (2..10).random()
                        temp = "${numbers[0]} "
                        for (f in 1 until numbers.size){
                            temp+="${signs[f-1]} ${numbers[f]} "
                        }
                        temp_sentence = Sentence(temp)
                        temp_new_summ = temp_sentence.count().get_value().toInt()
                    }
                    if (count > 0){
                        previous = numbers[current] * numbers[current-1]
                        count = -1
                    }
                    else {
                        previous *= numbers[current]
                        count--
                    }
                }
                ':' -> {
                    if (count > 0){
                        previous = numbers[current - 1]
                    }
                    numbers.add((2..10).random())
                    while (previous / numbers[current] >= 10 || previous % numbers[current] != 0){
                        numbers[current] = (2..10).random()
                    }
                    if (count > 0){
                        previous = numbers[current] * numbers[current-1]
                        count = -1
                    }
                    else {
                        previous *= numbers[current]
                        count--
                    }
                }
            }
            temp = "${numbers[0]} "
            for (f in 1 until numbers.size){
                temp+="${signs[f-1]} ${numbers[f]} "
            }
            val temp_sentence = Sentence(temp)
            current_summ = temp_sentence.count().get_value().toInt()
            current++
        }
        temp = "${j+1}) ${numbers[0]} "
        for (f in 1 until numbers.size){
            temp+="${signs[f-1]} ${numbers[f]} "
        }
        println(temp)
    }

}