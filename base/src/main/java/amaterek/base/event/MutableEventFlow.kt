package amaterek.base.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class MutableEventFlow<T> {

    private val eventsChannel = Channel<T>()

    fun asFlow(): Flow<T> = eventsChannel.receiveAsFlow()

    suspend fun emit(event: T) = eventsChannel.send(event)
}