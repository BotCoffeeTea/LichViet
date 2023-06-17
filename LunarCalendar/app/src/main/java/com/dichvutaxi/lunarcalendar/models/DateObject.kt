package com.dichvutaxi.lunarcalendar.models

class DateObject @Throws(Exception::class) constructor(
    var day: Int,
    var month: Int,
    var year: Int,
    var leap: Int = 0,
    var hourOfDay: Int = 0,
    var minute: Int = 0
) {

    init {
        setDay(day)
        setMonth(month)
    }

    @Throws(Exception::class)
    fun setDay(day: Int) {
        if (day < 1 || day > 31) {
            throw Exception("day must be >= 1 and <=31")
        }
        this.day = day
    }

    @Throws(Exception::class)
    fun setMonth(month: Int) {
        if (month > 12 || month < 1) {
            throw Exception("month must be >= 1 and <= 12")
        }
        this.month = month
    }
}
