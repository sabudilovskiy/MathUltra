package Support

import MathObject.Ring
import java.lang.Math.abs
import kotlin.IllegalArgumentException

//создаём массив с n одинаковых элементов. Первый аргумент - экземпляр, второй аргумент - количество
//inline fun <reified T> createSingleArrayList(something : T, n : Int) : ArrayList <T> {
//    val array : ArrayList<T> = arrayListOf()
//    if (n > 0) {
//        var i : Int = 0;
//        while (i < n) {
//            array.add(something)
//            i++
//        }
//        return array
//    }
//    else throw IllegalArgumentException()
//}
//создаём массив с n элементов, создаваемых при помощи лямбды. Первый аргумент - лямба, второй аргумент - количество
inline fun <reified T> createSingleArrayList(func : () -> T, n : Int) : ArrayList <T> {
    val array : ArrayList<T> = arrayListOf()
    if (n > 0) {
        var i : Int = 0;
        while (i < n) {
            array.add(func())
            i++
        }
        return array
    }
    else throw IllegalArgumentException()
}
fun Int.toBinary(len: Int): String {
    return String.format("%" + len + "s", this.toString(2)).replace(" ".toRegex(), "0")
}
//создаём массив с m*n элементов, создаваемых при помощи лямбды. Первый аргумент - лямба, m, n
inline fun <reified T> createRectangleArrayList(func : () -> T, m: Int, n: Int) : ArrayList<ArrayList<T>> {
    val array : ArrayList<ArrayList<T>> = arrayListOf()
    if (n > 0) {
        var i : Int = 0;
        while (i++ < m) array.add(createSingleArrayList(func, n))
        return array
    }
    else throw IllegalArgumentException()
}
//inline fun <reified T> createRectangleArrayList(something: T, m: Int, n: Int) : ArrayList<ArrayList<T>> {
//    val array : ArrayList<ArrayList<T>> = arrayListOf()
//    if (n > 0) {
//        var i : Int = 0;
//        while (i++ < m) array.add(createSingleArrayList(something, n))
//        return array
//    }
//    else throw IllegalArgumentException()
//}
//Convert To Power
fun CTP(ch : Char) : Char{
    when (ch){
        '0' -> return '⁰'
        '1' -> return '¹'
        '2' -> return '²'
        '3' -> return '³'
        '4' -> return '⁴'
        '5' -> return '⁵'
        '6' -> return '⁶'
        '7' -> return '⁷'
        '8' -> return '⁸'
        '9' -> return '⁹'
        else -> return ch
    }
}
//Convert To Power
fun CTP(numb : Int) : String{
    var temp : String = ""
    return CTP(numb.toString())
}
//Convert To Index
fun CTP(numb : String) : String{
    var i : Int = 0
    var temp : String = ""
    while (i < numb.length){
        temp += CTP(numb[i])
        i++
    }
    return temp
}

//Convert To Index
fun CTI(ch : Char) : Char{
    when (ch){
        '0' -> return '₀'
        '1' -> return '₁'
        '2' -> return '₂'
        '3' -> return '₃'
        '4' -> return '₄'
        '5' -> return '₅'
        '6' -> return '₆'
        '7' -> return '₇'
        '8' -> return '₈'
        '9' -> return '₉'
        else -> return ch
    }
}
//Convert To Index
fun CTI(numb : Int) : String{
    return CTI(numb.toString())
}
//Convert To Index
fun CTI(numb : String) : String{
    var i : Int = 0
    var temp : String = ""
    while (i < numb.length){
        temp += CTI(numb[i])
        i++
    }
    return temp
}
fun find_GCD(a : Long, b : Long) : Long {
    var temp_a: Long = abs(a)
    var temp_b: Long = abs(b)
    while (temp_a != 0L && temp_b != 0L) if (temp_a > temp_b) temp_a %= temp_b else temp_b %= temp_a
    return temp_a + temp_b
}
