package com.dichvutaxi.clockandlichviet.calendar

import java.util.*

class SolarDate(val year: Int, val month: Int, val day: Int) {
    override fun toString(): String {
        return String.format("%02d/%02d/%04d", day, month, year)
    }

    fun toCalendar(): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
    }
}
