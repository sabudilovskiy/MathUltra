package com.example.flamemathnew.ui.lexeme

import android.text.Editable
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LexemeViewModelImpl : ViewModel() {

    val editTextLexeme : MutableLiveData<String> = MutableLiveData<String>()
    val editTextArgs : MutableLiveData<String> = MutableLiveData<String>()

}