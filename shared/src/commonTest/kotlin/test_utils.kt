package com.soulesidibe.domain

import kotlinx.coroutines.CoroutineScope

expect fun suspendTest(body: suspend CoroutineScope.() -> Unit)