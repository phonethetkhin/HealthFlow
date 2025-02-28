package com.ptk.healthflow.util

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

object GlobalEventBus {
    private val _eventFlow = MutableSharedFlow<GlobalEvent>(replay = 0, extraBufferCapacity = 1)
    val eventFlow = _eventFlow.asSharedFlow()

    fun triggerEvent(event: GlobalEvent) {
        Log.e("testASDF", "TriggerEvent $event")

        GlobalScope.launch {
            _eventFlow.emit(event)
        }
    }
}
