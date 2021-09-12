package com.example.flamemathnew.back.lexemes

abstract class Function {
    abstract fun count(args: MutableList<Double>): Double
    open fun check(args: MutableList<Double>): Boolean  = true;
}

class FunctionSin : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.sin(args[0])
    }
}

class FunctionCos : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.cos(args[0])
    }
}

class FunctionTg : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.tan(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return if (Math.cos(args[0]) == 0.0) false else true
    }
}

class FunctionCtg : Function() {
    override fun count(args: MutableList<Double>): Double {
        return 1 / Math.tan(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return if (Math.sin(args[0]) == 0.0) false else true
    }
}

class FunctionAbs : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.abs(args[0])
    }
}

class FunctionArcsin : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.asin(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return -1 <= args[0] && args[0] <= 1
    }
}

class FunctionArcCos : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.acos(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return -1 <= args[0] && args[0] <= 1
    }
}

class FunctionArctg : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.atan(args[0])
    }
}

class FunctionArcCtg : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.PI / 2 - Math.atan(args[0])
    }
}

class FunctionExp : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.exp(args[0])
    }
}

class FunctionLn : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.log(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return args[0] > 0
    }
}

class FunctionLog : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.log(args[1]) / Math.log(args[0])
    }

    override fun check(args: MutableList<Double>): Boolean {
        return args[0] != 1.0 && args[0] > 0 && args[1] > 0
    }
}

class FunctionPow : Function() {
    override fun count(args: MutableList<Double>): Double {
        return Math.pow(args[0], args[1])
    } //        @Override
    //        public boolean check(ArrayList<Double> args) {
    //                if (args.get(0) > 0) return true;
    //                else
    //                {
    //                        if (args.get(1) - trunc(args.get(1)) == 0)
    //                        {
    //                                if (args.get(0) != 0 || args.get(1) >= 0)
    //                                {
    //                                        return true;
    //                                }
    //                        }
    //                }
    //                return false;
    //        }
}

class FunctionMult : Function() {
    override fun count(args: MutableList<Double>): Double {
        return args[0] * args[1]
    }
}

class FunctionDiv : Function() {
    override fun count(args: MutableList<Double>): Double {
        return args[0] / args[1]
    }
    override fun check(args: MutableList<Double>): Boolean {
        return args[1] != 0.0
    }
}

class FunctionPlus : Function() {
    override fun count(args: MutableList<Double>): Double {
        return args[0] + args[1]
    }
}

class FunctionMinus : Function() {
    override fun count(args: MutableList<Double>): Double {
        return args[0] - args[1]
    }
}