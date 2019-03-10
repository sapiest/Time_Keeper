package com.example.toxaxab.timekeeper.Model

import android.os.SystemClock

data class MyActivity(var name: String) {
    var currentTime: Long = 0
    var comment: String? = null
    var condition: Condition = Condition.INACTIVE
} //todo нужен полноценный класс, с комментами и временем


enum class Condition{
    ACTIVE,
    INACTIVE
}

object Supplier {
    val activities = listOf<MyActivity>(MyActivity("Sleeping"), MyActivity("Coding"))
}