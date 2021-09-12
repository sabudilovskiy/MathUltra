package com.example.flamemathnew.back.lexemes

import com.example.flamemathnew.back.lexemes.ErrorHandler.Companion.getError
import com.example.flamemathnew.back.lexemes.ErrorHandler.Companion.setError

class Sentence {
    private var _array : MutableList<Lexeme> = mutableListOf()

    constructor() {}
    constructor(input: String) {
        var pos = 0
        while (pos < input.length) {
            while (pos < input.length && input[pos] == ' ') pos++
            if (pos < input.length) {
                val begin = pos
                if (Support.isNumeral(input[pos])) {
                    var buffer = ""
                    var use_point = false
                    while (pos < input.length && (input[pos] == '.' || Support.isNumeral(input[pos]))) {
                        if (input[pos] == '.') {
                            if (use_point == false) {
                                use_point = true
                                buffer += input[pos++]
                            } else {
                                setError(IdErrors.ERROR_SIGNS, pos, pos)
                                return
                            }
                        } else {
                            buffer += input[pos++]
                        }
                    }
                    val temp_array = mutableListOf<Double>()
                    temp_array.add(buffer.toDouble())
                    val temp = Lexeme(IdLexemes.ARGUMENT, temp_array)
                    temp.begin = begin
                    temp.end = pos - 1
                    addLexeme(temp)
                } else {
                    var buffer = String()
                    var a = mutableListOf<Int>()
                    a.add(-1)
                    var max_id = -1
                    var pos_max = pos
                    var max_buffer = ""
                    while (a.size > 0 && pos < input.length) {
                        buffer += input[pos++]
                        a = Archieve.decode(buffer, null)
                        for (i in a.indices) {
                            if (a[i] > 0) {
                                max_buffer = buffer
                                max_id = a[i]
                                pos_max = pos
                                break
                            }
                        }
                    }
                    if (max_id == -1) {
                        val end = pos - 1
                        setError(IdErrors.UNKNOWN_FUNCTION, begin, end)
                        return
                    } else {
                        val id = IdLexemes.getById(max_id)
                        var temp: Lexeme
                        pos = pos_max
                        temp = if (id !== IdLexemes.VARIABLE) {
                            Lexeme(id)
                        } else {
                            Lexeme(max_buffer)
                        }
                        temp.begin = begin
                        temp.end = pos_max - 1
                        addLexeme(temp)
                    }
                }
            }
        }
        var left_brs = 0
        var right_brs = 0
        for (i in _array.indices) {
            if (_array[i].getId() === IdLexemes.LEFT_BR) left_brs++ else if (_array[i].getId() === IdLexemes.RIGHT_BR) right_brs++
            if (right_brs > left_brs) {
                setError(IdErrors.MORE_RIGHT_BRACKETS, _array[i])
                return
            }
        }
        if (left_brs == right_brs) {
            return
        } else {
            val a = findLeftBr()
            setError(IdErrors.HAVE_OPEN_BRACKETS, a, a)
            return
        }
    }

    internal constructor(array: MutableList<Lexeme>) {
        for (lexeme in array) addLexeme(lexeme)
    }

    fun findLeftBr(): Int {
        var i = 0
        while (i < _array.size) {
            if (_array[i].getId() === IdLexemes.LEFT_BR) return i
            i++
        }
        return -1
    }


    //добавить лексему
    fun addLexeme(a: Lexeme) {
        _array.add(a)
    }

    //удалить лексему с таким номером
    fun deleteLexeme(i: Int) {
        _array.removeAt(i)
    }

    //заменить лексему x на значение
    fun substitute(keys: MutableList<String>, values: MutableList<Double>) {
        for (i in keys.indices) {
            val key = keys[i]
            val x = values[i]
            for (j in _array.indices) {
                if (_array[j].getId() === IdLexemes.VARIABLE && _array[j].key == key) {
                    val x0 = mutableListOf<Double>()
                    x0.add(x)
                    _array[j] = Lexeme(IdLexemes.ARGUMENT, x0)
                }
            }
        }
    }

    //создать новое предложение из части старого
    fun createLexemeVector(a: Int, b: Int): Sentence {
        val buffer = mutableListOf<Lexeme>()
        for (i in a..b) {
            buffer.add(_array[i])
        }
        return Sentence(buffer)
    }

    //заменить диапазон на одну лексему
    fun replaceSector(a: Int, b: Int, paste: Lexeme) {
        for (i in a until b) {
            deleteLexeme(a)
        }
        _array[a] = paste
    }

    fun findCommas(begin: Int): MutableList<Int> {
        val answer = mutableListOf<Int>()
        var i = 0
        while (i < _array.size) {
            if (_array[i].getId() === IdLexemes.COMMA) {
                answer.add(i + begin + 1)
            } else if (_array[i].getId() === IdLexemes.LEFT_BR) {
                i = findRightBracket(i)
            }
            i++
        }
        return answer
    }

    //найти закрывающую скобку для открывающей
    fun findRightBracket(a: Int): Int {
        var count_open_brackets = 1
        var i = a + 1
        while (true) {
            if (_array[i].getId() === IdLexemes.LEFT_BR) count_open_brackets++ else if (_array[i].getId() === IdLexemes.RIGHT_BR) count_open_brackets--
            if (count_open_brackets == 0) {
                return i
            }
            i++
        }
    }

    //найти оператор с наивысшим приоритетом
    fun findHighestPriority(): Int {
        var max_priority = 0
        var i = 0
        while (i < _array.size) {
            if (_array[i].getId() !== IdLexemes.ARGUMENT) {
                val cur_priority = Archieve.getPriority(_array[i].getId())
                if (cur_priority > max_priority) {
                    max_priority = cur_priority
                }
            }
            i++
        }
        return max_priority
    }

    fun findCountableOperator(priority: Int): Int {
        var i = 0
        while (i < _array.size) {
            if (_array[i].getId() !== IdLexemes.ARGUMENT && Archieve.getPriority(_array[i].getId()) == priority) {
                val left = Archieve.getLeftArgue(_array[i].getId())
                val right = Archieve.getRightArgue(_array[i].getId())
                if ((left == 0 || _array[i - 1].getId() === IdLexemes.ARGUMENT) && (right == 0 || _array[i + 1].getId() === IdLexemes.ARGUMENT)) return i
            }
            i++
        }
        return -1
    }

    fun haveErrors(): Boolean {
        val n = _array.size
        if (Archieve.getLeftArgue(_array[0].getId()) == 0 && Archieve.getRightArgue(_array[n-1].getId()) == 0) {
            for (i in 1 until n) {
                val cur_id = _array[i].getId()
                val left = Archieve.getLeftArgue(cur_id)
                val right = Archieve.getRightArgue(cur_id)
                if (left > 0 && right > 0) {
                    if (Archieve.getRightArgue(_array[i - 1].getId()) == 0 && Archieve.getLeftArgue(_array[i + 1].getId()) == 0) {
                    } else {
                        setError(IdErrors.MISS_ARGUMENT_BINARY_OPERATOR, _array[i])
                        return true
                    }
                } else if (left > 0) {
                    if (Archieve.getRightArgue(_array[i - 1].getId()) == 0 && Archieve.getLeftArgue(_array[i + 1].getId()) != 0) {
                    } else {
                        setError(IdErrors.MISS_ARGUMENT_POST_OPERATOR, _array[i])
                        return true
                    }
                } else if (right > 0) {
                    if (Archieve.getLeftArgue(_array[i + 1].getId()) == 0 && Archieve.getRightArgue(_array[i - 1].getId()) != 0) {
                    } else {
                        setError(IdErrors.MISS_ARGUMENT_PRE_OPERATOR, _array[i])
                        return true
                    }
                }
            }
        }
        return false
    }

    //посчитать значение предложения
    fun count(): Lexeme {
        var a = findLeftBr()
        while (a != -1) //избавляемся от скобок
        {
            val b = findRightBracket(a)
            val current = createLexemeVector(a + 1, b - 1)
            val commas = current.findCommas(a)
            //в скобках выражение без запятых
            if (commas.size == 0) {
                val replace = current.count()
                if (getError() === IdErrors.NON_ERROR) {
                    replaceSector(a, b, replace)
                } else {
                    return Lexeme(IdLexemes.NULL, mutableListOf())
                }
            } else {
                val A = mutableListOf<Sentence>()
                val values = mutableListOf<Double>()
                for (i in 0 until commas.size + 1) {
                    A.add(Sentence())
                    values.add(0.0)
                }
                A[0] = createLexemeVector(a + 1, commas[0] - 1)
                for (i in 1 until commas.size) {
                    A[1] = createLexemeVector(commas[i - 1] + 1, commas[i] - 1)
                }
                A[A.size - 1] = createLexemeVector(commas[commas.size - 1] + 1, b - 1)
                for (i in values.indices) {
                    values[i] = A[i].count().getValue()
                    if (getError() !== IdErrors.NON_ERROR) {
                        return Lexeme(IdLexemes.NULL, mutableListOf())
                    }
                }
                val replace = Lexeme(IdLexemes.ARGUMENT, values)
                replaceSector(a, b, replace)
            }
            a = findLeftBr()
        }
        if (haveErrors()) {
            return Lexeme(IdLexemes.NULL)
        }
        a = findHighestPriority()
        while (a != 0) {
            var b = findCountableOperator(a)
            while (b != -1) {
                val left = Archieve.getLeftArgue(_array[b].getId())
                val right = Archieve.getRightArgue(_array[b].getId())
                val l = if (left != 0) 1 else 0
                val r = if (right != 0) 1 else 0
                var left_argue : MutableList<Double> = mutableListOf()
                var right_argue : MutableList<Double> = mutableListOf()
                if (l != 0) {
                    left_argue = _array[b - 1].getValues()
                    if (left != left_argue.size) {
                        setError(IdErrors.BAD_ARGUMENTS, _array[b])
                        return Lexeme(IdLexemes.NULL, mutableListOf())
                    }
                }
                if (r != 0) {
                    right_argue = _array[b + 1].getValues()
                    if (right != right_argue.size) {
                        setError(IdErrors.BAD_ARGUMENTS, _array[b])
                        return Lexeme(IdLexemes.NULL, mutableListOf())
                    }
                }
                val argue: MutableList<Double> = Support.union(left_argue, right_argue)
                if (Archieve.checkCountable(_array[b].getId(), argue)) {
                    val x0 = mutableListOf<Double>()
                    x0.add(Archieve.count(_array[b].getId(), argue))
                    val replace = Lexeme(IdLexemes.ARGUMENT, x0)
                    replaceSector(b - 1 * l, b + 1 * r, replace)
                } else {
                    setError(IdErrors.IMPOSSIBLE_COUNT, _array[b])
                    return Lexeme(IdLexemes.NULL, mutableListOf())
                }
                b = findCountableOperator(a)
            }
            a = findHighestPriority()
        }
        return if (_array.size == 1 && _array[0].getId() === IdLexemes.ARGUMENT) {
            _array[0]
        } else {
            setError(IdErrors.UNKNOWN_ERROR, -1, -1)
            Lexeme(IdLexemes.NULL, mutableListOf())
        }
    }

    fun code(): String {
        var A = ""
        var i = 0
        while (i < _array.size) {
            val cur_lexeme = _array[i]
            A += cur_lexeme.key
            i++
        }
        return A
    }
}