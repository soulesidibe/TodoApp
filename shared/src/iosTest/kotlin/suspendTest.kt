package com.soulesidibe.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun suspendTest(body: suspend CoroutineScope.() -> Unit) {
    runBlocking { body() }
}