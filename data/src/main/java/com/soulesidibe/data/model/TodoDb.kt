package com.soulesidibe.data.model

import java.util.*

internal data class TodoDb(val id: String = UUID.randomUUID().toString(), val title: String)