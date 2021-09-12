package com.example.flamemathnew.mid

class ComputerHelper
{
    companion object
    {
        fun findNullStringInArray(arr : MutableList<String?>): Int{
            var i : Int = 0;
            val n : Int = arr.size
            while (i < n){
                if (arr[i] == null || arr[i] == "") return i
                i++
            }
            return -1
        }
        fun isNumber(str : String): Boolean{
            if (str == "") return false
            var commas : Int = 0
            var a : Int = 0
            if ((str[0] == '+' || str[0] == '-') && str.length > 1 && isDigit(str[1])) a = 2
            else if (isDigit(str[0])) a = 1
            else return false
            for (i in a until str.length){
                val ch : Char = str[i]
                if (isDigit(ch)){}
                else if (ch == '.' && commas == 0) commas++
                else return false
            }
            return true
        }
        fun isDigit(ch : Char ) : Boolean{
            return if ('0' <= ch && ch <= '9') true
            else false
        }
    }
}

