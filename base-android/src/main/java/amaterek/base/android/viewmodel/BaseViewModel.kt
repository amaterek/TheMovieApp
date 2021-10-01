package amaterek.base.android.viewmodel

import amaterek.base.log.Log
import amaterek.base.logTag
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    init {
        Log.v(logTag(), "init()")
    }

    override fun onCleared() {
        Log.v(logTag(), "onCleared()")
        super.onCleared()
    }
}