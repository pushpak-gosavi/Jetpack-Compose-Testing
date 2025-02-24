package com.pushpak.textfield.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var cryptoText = MutableLiveData("")
    fun setDataToCryptoText(text: String) {
        viewModelScope.launch {
            cryptoText.postValue(text)
        }
    }

    fun getData(): MutableLiveData<String> {
        return cryptoText
    }

    fun flowData(): Flow<Int> {
        return flow<Int> {
            for (i in 1..5) {
                delay(1000)
                emit(i)
            }
        }
    }
}