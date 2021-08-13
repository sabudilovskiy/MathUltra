package Number

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