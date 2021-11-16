package amaterek.movie.app.ui.splash

import amaterek.base.android.viewmodel.BaseViewModel
import amaterek.base.event.MutableEventFlow
import amaterek.movie.app.ui.splash.SplashViewModel.Event.SplashFinished
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SplashViewModel @Inject constructor(private val splashTask: SplashTask) :
    BaseViewModel() {

    sealed interface Event {
        object SplashFinished : Event
    }

    private val _eventFlow = MutableEventFlow<Event>()
    val eventFlow = _eventFlow.asFlow()

    init {
        viewModelScope.launch {
            splashTask.execute(viewModelScope)
            _eventFlow.emit(SplashFinished)
        }
    }

    fun requestFinish() {
        splashTask.requestFinish()
    }
}