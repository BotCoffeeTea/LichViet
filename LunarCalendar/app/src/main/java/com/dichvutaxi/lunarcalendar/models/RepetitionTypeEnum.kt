package com.dichvutaxi.lunarcalendar.models

enum class RepetitionTypeEnum(private val value: Int) {
    ONCE(0),
    DAILY(1),
    WEEKLY(2),
    MONTHLY(3),
    YEARLY(4);

    fun getValue(): Int {
        return value
    }
}
