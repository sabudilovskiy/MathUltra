package com.example.flamemathnew.back.lexemes

enum class PriorityOperators {
    END, PRIOR_BOOL_FUNC, PRIOR_PLUS_MINUS, PRIOR_MULT_DIV, PRIOR_POW, PRIOR_FUNC;

    companion object {
        fun getId(priority: PriorityOperators): Int {
            for (i in values().indices) {
                if (priority == values()[i]) return i
            }
            throw NumberFormatException("No such element in enum")
        }

        fun getById(id: Int): PriorityOperators {
            return values()[id]
        }
    }
}