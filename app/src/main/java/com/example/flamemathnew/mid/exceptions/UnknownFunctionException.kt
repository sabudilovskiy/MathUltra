package com.example.flamemathnew.mid.exceptions

import com.example.flamemathnew.back.lexemes.ErrorHandler.Companion.getBeginError
import com.example.flamemathnew.back.lexemes.ErrorHandler.Companion.getEndError

class UnknownFunctionException : LexemesException(getBeginError(), getEndError())