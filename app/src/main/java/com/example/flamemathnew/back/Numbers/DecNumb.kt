package Number

import MathObject.Ring
import MRV.Computer
import Matrix.Matrix
import com.example.flamemathnew.back.Polynom.Polynom

class DecNumb : Numb {
    var value : Double = 0.0
    constructor(_value : Double){
        value = _value
    }
    constructor(_value : Int){
        value = _value.toDouble()
    }
    constructor(_value : Long){
        value = _value.toDouble()
    }
    override operator fun compareTo(right: Numb): Int {
        if (right is DecNumb){
            if (value == right.value) return 0
            else if (value > right.value) return 1
            else return -1
        }
        else if (right is FractionalNumb){
            return FractionalNumb(value).compareTo(right)
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun plus(right: Ring): Ring {
        if (right is DecNumb){
            return createNumb(value+right.value)
        }
        else if (right is Polynom){
            return right + this
        }
        else{
            throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override fun minus(right: Ring): Ring {
        if (right is DecNumb){
            return createNumb(value-right.value)
        }
        else if (right is Polynom){
            return this + (-right)
        }
        else{
            throw Computer.NON_COMPLIANCE_TYPES()
        }
    }

    override fun times(right: Ring): Ring {
        if (right is DecNumb){
            return createNumb(value*right.value)
        }
        else if (right is Matrix){
            return right*this
        }
        else if (right is Polynom) {
            return right * this
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }
    override fun div(right: Ring): Ring {
        if (right is DecNumb){
            return createNumb(value/right.value)
        }
        else if (right is Matrix){
            return right*this
        }
        else throw Computer.NON_COMPLIANCE_TYPES()
    }

    override fun equals(other: Any?): Boolean {
        if (other === null) return false
        else if (other is DecNumb) return value == other.value
        else if (other is Double) return value == other
        else return false
    }

    override fun unaryMinus() : Ring {
        return DecNumb(-value)
    }

    override fun toString(): String {
        return value.toString()
    }
}