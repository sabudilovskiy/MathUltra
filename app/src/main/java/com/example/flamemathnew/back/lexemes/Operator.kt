package com.example.flamemathnew.back.lexemes

open class Operator {
    protected var id: IdLexemes = IdLexemes.NULL;
    protected var func: Function = FunctionSin()
    protected var left_argue = 0
    protected var right_argue = 0
    protected var priority = 0
    protected var decode_base = mutableListOf<String>()
    fun getId(): Int {
        return IdLexemes.getId(id!!)
    }

    fun getLeftArgue(): Int {
        return left_argue
    }

    fun getRightArgue(): Int {
        return right_argue
    }

    fun isIt(input: String): Int {
        var max_code = 0
        for (i in decode_base.indices) {
            if (decode_base[i].length == input.length && decode_base[i] == input) return 2 else if (decode_base[i].length >= input.length && decode_base[i].startsWith(
                    input
                )
            ) {
                max_code = 1
            }
        }
        return max_code
    }

    fun getOpPriority(): Int {
        return priority
    }

    fun count(args: MutableList<Double>): Double {
        return func.count(args)
    }

    fun check(args: MutableList<Double>): Boolean {
        return func.check(args)
    }

    //id, количество аргументов слева, количество аргументов справа, приоритет, количество кодовых слов, кодовые слова
    internal constructor(
        func: Function,
        id: IdLexemes,
        left_argue: Int,
        right_argue: Int,
        priority: Int,
        list_of_words: MutableList<String>
    ) {
        this.id = id
        this.left_argue = left_argue
        this.right_argue = right_argue
        this.priority = priority
        for (i in list_of_words.indices) {
            decode_base.add(list_of_words[i])
        }
    }

    internal constructor() {}

    fun code(): String {
        return decode_base[0]
    } //    protected void load_decode_base(String src){
    //        if (src.equals ( "" )) return;
    //        else {
    //            try {
    //                FileReader localisation_file = new FileReader (src);
    //                BufferedReader scan = new BufferedReader ( localisation_file );
    //                String line;
    //                while ((line = scan.readLine ()) != null){
    //                    if (line.equals ("")!=true) decode_base.add ( line );
    //                }
    //
    //            } catch (FileNotFoundException e) {
    //                e.printStackTrace ();
    //            } catch (IOException e) {
    //                e.printStackTrace ();
    //            }
    //        }
    //
    //    }
}

class Argument : Operator() {
    init {
        id = IdLexemes.ARGUMENT
    }
}

class Variable(decode_base: MutableList<String>) : Operator() {
    init {
        id = IdLexemes.VARIABLE
        this.decode_base = decode_base
    }
}

class LeftBr : Operator() {
    init {
        id = IdLexemes.LEFT_BR
        decode_base.add("(")
    }
}

class RightBr : Operator() {
    init {
        id = IdLexemes.RIGHT_BR
        decode_base.add(")")
    }
}

class Comma : Operator() {
    init {
        id = IdLexemes.COMMA
        decode_base.add(",")
    }
}

class Abs : Operator() {
    init {
        decode_base.add("abs")
        id = IdLexemes.ABS
        left_argue = 0
        right_argue = 1
        func = FunctionAbs()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Sin : Operator() {
    init {
        decode_base.add("sin")
        id = IdLexemes.SIN
        left_argue = 0
        right_argue = 1
        func = FunctionSin()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Cos : Operator() {
    init {
        decode_base.add("cos")
        id = IdLexemes.COS
        left_argue = 0
        right_argue = 1
        func = FunctionCos()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Tg : Operator() {
    init {
        decode_base.add("tg")
        decode_base.add("tan")
        id = IdLexemes.TG
        left_argue = 0
        right_argue = 1
        func = FunctionTg()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Ctg : Operator() {
    init {
        decode_base.add("ctg")
        decode_base.add("cot")
        decode_base.add("cotan")
        id = IdLexemes.CTG
        left_argue = 0
        right_argue = 1
        func = FunctionCtg()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class ArcSin : Operator() {
    init {
        decode_base.add("arcsin")
        decode_base.add("asin")
        id = IdLexemes.ARCSIN
        left_argue = 0
        right_argue = 1
        func = FunctionArcsin()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class ArcCos : Operator() {
    init {
        decode_base.add("arccos")
        decode_base.add("acos")
        id = IdLexemes.ARCCOS
        left_argue = 0
        right_argue = 1
        func = FunctionArcCos()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Arctg : Operator() {
    init {
        decode_base.add("arctg")
        decode_base.add("arctan")
        decode_base.add("atg")
        decode_base.add("atan")
        id = IdLexemes.ARCTG
        left_argue = 0
        right_argue = 1
        func = FunctionArctg()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class ArcCtg : Operator() {
    init {
        decode_base.add("arcctg")
        decode_base.add("arccot")
        decode_base.add("arccotan")
        decode_base.add("actg")
        decode_base.add("acot")
        decode_base.add("acotan")
        id = IdLexemes.ARCCTG
        left_argue = 0
        right_argue = 1
        func = FunctionArcCtg()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Exp : Operator() {
    init {
        decode_base.add("exp")
        id = IdLexemes.EXP
        left_argue = 0
        right_argue = 1
        func = FunctionExp()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Ln : Operator() {
    init {
        decode_base.add("ln")
        id = IdLexemes.LN
        left_argue = 0
        right_argue = 1
        func = FunctionLn()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Log : Operator() {
    init {
        decode_base.add("log")
        id = IdLexemes.LOG
        left_argue = 0
        right_argue = 2
        func = FunctionLog()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_FUNC)
    }
}

class Pow : Operator() {
    init {
        decode_base.add("^")
        id = IdLexemes.POW
        left_argue = 1
        right_argue = 1
        func = FunctionPow()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_POW)
    }
}

class Mult : Operator() {
    init {
        decode_base.add("*")
        decode_base.add("×")
        decode_base.add("⋅")
        decode_base.add("∙")
        decode_base.add("·")
        id = IdLexemes.MULT
        left_argue = 1
        right_argue = 1
        func = FunctionMult()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_MULT_DIV)
    }
}

class Div : Operator() {
    init {
        decode_base.add("/")
        decode_base.add("÷")
        decode_base.add("∶")
        id = IdLexemes.DIV
        left_argue = 1
        right_argue = 1
        func = FunctionDiv()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_MULT_DIV)
    }
}

class Plus : Operator() {
    init {
        decode_base.add("+")
        id = IdLexemes.PLUS
        left_argue = 1
        right_argue = 1
        func = FunctionPlus()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_PLUS_MINUS)
    }
}

class Minus : Operator() {
    init {
        decode_base.add("—")
        decode_base.add("–")
        decode_base.add("−")
        decode_base.add("-")
        id = IdLexemes.MINUS
        left_argue = 1
        right_argue = 1
        func = FunctionMinus()
        priority = PriorityOperators.getId(PriorityOperators.PRIOR_PLUS_MINUS)
    }
}