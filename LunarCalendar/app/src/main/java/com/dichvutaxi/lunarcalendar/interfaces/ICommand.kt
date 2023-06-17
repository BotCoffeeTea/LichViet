package com.dichvutaxi.lunarcalendar.interfaces

import com.dichvutaxi.lunarcalendar.models.DateObject

interface ICommand {
    @Throws(Exception::class)
    fun execute(date: DateObject)
}
