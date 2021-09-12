package com.example.flamemathnew.back.lexemes

//token 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
class Archieve(variables: MutableList<String>) {
    fun addOperator(A: Operator) {
        val n: Int = A.getId()
        base[n] = A
    }

    companion object {
        var base: MutableList<Operator> = mutableListOf<Operator>()
        fun decode(input: String, verif: MutableList<Int>?): MutableList<Int> {
            val answer = mutableListOf<Int>()
            if (verif == null || verif.size == 0) {
                for (i in 0 until IdLexemes.getId(IdLexemes.NUMBER_OPERATORS)) {
                    val check: Int = base[i].isIt(input)
                    if (check == 2) {
                        answer.add(i)
                    } else if (check == 1) {
                        answer.add(-i)
                    }
                }
            } else {
                for (i in verif.indices) {
                    val b = Math.abs(verif[i])
                    val check: Int = base[b].isIt(input)
                    if (check == 2) {
                        answer.add(b)
                    } else if (check == 1) {
                        answer.add(-b)
                    }
                }
            }
            return answer
        }

        fun code(id: IdLexemes): String {
            return base[IdLexemes.getId(id)].code()
        }

        fun getPriority(id: IdLexemes): Int {
            return if (IdLexemes.getId(id) <= base.size) base[IdLexemes.getId(id)].getOpPriority() else 0
        }

        fun getLeftArgue(id: IdLexemes): Int {
            return if (IdLexemes.getId(id) <= base.size) base[IdLexemes.getId(id)].getLeftArgue() else 0
        }

        fun getRightArgue(id: IdLexemes): Int {
            return if (IdLexemes.getId(id) <= base.size) base[IdLexemes.getId(id)].getRightArgue() else 0
        }

        fun checkCountable(id: IdLexemes, argues: MutableList<Double>): Boolean {
            return base[IdLexemes.getId(id)].check(argues)
        }

        fun count(id: IdLexemes, argues: MutableList<Double>): Double {
            return base[IdLexemes.getId(id)].count(argues)
        }
    }

    init {
        val n: Int = IdLexemes.getId(IdLexemes.NUMBER_OPERATORS)
        while (base.size < n) {
            base.add(Sin())
        }
        addOperator(Argument())
        addOperator(Variable(variables))
        addOperator(LeftBr())
        addOperator(RightBr())
        addOperator(Comma())
        addOperator(Abs())
        addOperator(Sin())
        addOperator(Cos())
        addOperator(Tg())
        addOperator(Ctg())
        addOperator(ArcSin())
        addOperator(ArcCos())
        addOperator(Arctg())
        addOperator(ArcCtg())
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