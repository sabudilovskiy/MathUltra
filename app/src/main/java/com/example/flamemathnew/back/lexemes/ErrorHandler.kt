package com.example.flamemathnew.back.lexemes

class ErrorHandler
{
    companion object {
        private var error: IdErrors = IdErrors.NON_ERROR
        private var begin_error = -1
        private var end_error = -1
        fun setError(error: IdErrors, fail_lexeme: Lexeme) {
            ErrorHandler.error = error
            begin_error = fail_lexeme.begin
            end_error = fail_lexeme.end
        }

        fun setError(error: IdErrors, begin: Int, end: Int) {
            ErrorHandler.error = error
            begin_error = begin
            end_error = end
        }

        fun getError(): IdErrors {
            return error
        }

        fun getBeginError(): Int {
            return begin_error
        }

        fun getEndError(): Int {
            return end_error
        }

        fun setDefault() {
            error = IdErrors.NON_ERROR
            begin_error = -1
            end_error = -1
        }
    }
}