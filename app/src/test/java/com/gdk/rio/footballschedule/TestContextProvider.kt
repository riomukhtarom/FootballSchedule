package com.gdk.rio.footballschedule

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TestContextProvider: CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}