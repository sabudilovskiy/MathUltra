package com.example.flamemathnew.backend.back.Lexemes

//token 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
class Archieve(variables: ArrayList<String>) {

    private fun addOperator(A: Operator) {
        val n: Int = A.get_id()
        base[n] = A
    }

    companion object {
        var base: ArrayList<Operator> = ArrayList()
        fun decode(input: String, verif: ArrayList<Int>?): ArrayList<Int> {
            val answer = ArrayList<Int>()
            if (verif == null || verif.size == 0) {
                for (i in 0 until Id_lexemes.getId(Id_lexemes.NUMBER_OPERATORS)) {
                    val check: Int = base[i].is_it(input)
                    if (check == 2) {
                        answer.add(i)
                    } else if (check == 1) {
                        answer.add(-i)
                    }
                }
            } else {
                for (i in verif.indices) {
                    val b = Math.abs(verif[i])
                    val check: Int = base[b].is_it(input)
                    if (check == 2) {
                        answer.add(b)
                    } else if (check == 1) {
                        answer.add(-b)
                    }
                }
            }
            return answer
        }

        fun code(id: Id_lexemes): String {
            return base[Id_lexemes.getId(id)].code()
        }

        fun getPriority(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_priority() else 0
        }

        fun getLeftArgue(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_left_argue() else 0
        }

        fun getRightArgue(id: Id_lexemes): Int {
            return if (Id_lexemes.getId(id) <= base.size) base[Id_lexemes.getId(id)].get_right_argue() else 0
        }

        fun checkCountable(id: Id_lexemes, argues: ArrayList<Double>): Boolean {
            return base[Id_lexemes.getId(id)].check(argues)
        }

        fun count(id: Id_lexemes, argues: ArrayList<Double>): Double {
            return base[Id_lexemes.getId(id)].count(argues)
        }
    }

    init {
        val n: Int = Id_lexemes.getId(Id_lexemes.NUMBER_OPERATORS)
        while (base.size < n) {
            base.add(Sin())
        }
        addOperator(Argument())
        addOperator(Variable(variables))
        addOperator(Left_br())
        addOperator(Right_br())
        addOperator(Comma())
        addOperator(Abs())
        addOperator(Sin())
        addOperator(Cos())
        addOperator(Tg())
        addOperator(Ctg())
        addOperator(Arcsin())
        addOperator(Arccos())
        addOperator(Arctg())
        addOperator(Arcctg())
        addOperator(Exp())
        addOperator(Ln())
        addOperator(Log())
        addOperator(Pow())
        addOperator(Mult())
        addOperator(Div())
        addOperator(Plus())
        addOperator(Minus())
    }
}
