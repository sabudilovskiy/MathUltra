package Number

import MRV.Computer
import MathObject.Ring
import Parameters.Number.PROPER
import com.example.flamemathnew.mid.Settings
import kotlin.math.abs

public fun createNumb(value : Double) : Numb {
    val cur_set : Parameters.Number = Settings.numbers.type;
    if (cur_set.equals(PROPER)) {
        return FractionalNumb(value)
    }
    else {
        return DecNumb(value)
    }
}
public fun createNumb(value : Long) : Numb {
    val cur_set : Parameters.Number = Settings.numbers.type;
    if (cur_set.equals(PROPER)) {
        return FractionalNumb(value)
    }
    else {
        return DecNumb(value)
    }
}
public fun findDividers(numb : Numb) : ArrayList<Ring>{
    var temp : Long
    val answer = arrayListOf<Ring>()
    if (numb is DecNumb){
        if (numb.value.toLong().toDouble() - numb.value == 0.0) temp = numb.value.toLong()
        else throw Computer.NON_INTEGER()
    }
    else if (numb is FractionalNumb){
        if (numb.denominator == 1L) temp = numb.numerator
        else throw Computer.NON_INTEGER()
    }
    else throw Computer.NON_COMPLIANCE_TYPES()
    temp = abs(temp)
    var l : Long = 3L
    while (l < temp ){
        while (temp % l == 0L){
            temp = temp/l
            answer.add(createNumb(l))
        }
        l++
    }
    return answer
}
public fun max(left : Ring, right : Ring): Ring {
    if ((left is FractionalNumb || left is DecNumb) && (right is FractionalNumb || right is DecNumb) ){
        val temp_left : Double;
        val temp_right : Double;
        if (left is FractionalNumb) temp_left = left.toDouble()
        else temp_left = (left as DecNumb).value
        if (right is FractionalNumb) temp_right = right.toDouble()
        else temp_right = (right as DecNumb).value
        if (temp_left>temp_right) return createNumb(temp_left)
        else return createNumb(temp_right)
    }
    else throw Computer.NON_COMPLIANCE_TYPES()
}

abstract class Numb : Ring() {
    abstract fun compareTo(right : Numb) : Int
}