package amaterek.movie.app.ui.splash

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.movie.app.ui.splash.SplashViewModel.Event.SplashFinished
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(private val splashTask: SplashTask) : BaseViewModel() {

    sealed interface Event {
        object SplashFinished : Event
    }

    private val mutableEventFlow = MutableSharedFlow<Event>()
    val eventFlow = mutableEventFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            splashTask.execute(viewModelScope)
            mutableEventFlow.emit(SplashFinished)
        }
    }

    fun requestFinish() {
        splashTask.requestFinish()
    }
}