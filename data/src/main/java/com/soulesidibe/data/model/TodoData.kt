package com.soulesidibe.data.model

import java.util.*

data class TodoData(val id: String = UUID.randomUUID().toString(), val title: String)