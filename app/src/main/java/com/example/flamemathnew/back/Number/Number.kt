package Number

import MRV.Computer
import MathObject.Ring
import Parameters.Number.PROPER
import com.example.flamemathnew.mid.Settings

public fun createNumber(value : Double) : Ring {
    val cur_set : Parameters.Number = Settings.numbers.type;
    if (cur_set.equals(PROPER)) {
        return FractionalNumber(value)
    }
    else {
        return Dec_Number(value)
    }
}
public fun max(left : Ring, right : Ring): Ring {
    if ((left is FractionalNumber || left is Dec_Number) && (right is FractionalNumber || right is Dec_Number) ){
        val temp_left : Double;
        val temp_right : Double;
        if (left is FractionalNumber) temp_left = left.toDouble()
        else temp_left = (left as Dec_Number).value
        if (right is FractionalNumber) temp_right = right.toDouble()
        else temp_right = (right as Dec_Number).value
        if (temp_left>temp_right) return createNumber(temp_left)
        else return createNumber(temp_right)
    }
    else throw Computer.NON_COMPLIANCE_TYPES()
}