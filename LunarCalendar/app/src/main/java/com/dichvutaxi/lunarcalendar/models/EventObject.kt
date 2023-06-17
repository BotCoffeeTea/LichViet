package com.dichvutaxi.lunarcalendar.models

import com.dichvutaxi.lunarcalendar.R
import java.util.*

class EventObject(name: String) {
    var id = 0
    var name: String = name
    var color = R.color.colorRed
    var location: String? = null
    var fromDate: Date = Date()
    var toDate: Date = Date()
    var isAllDayEvent = false
    var repetitionType = RepetitionTypeEnum.ONCE

    fun setColor(color: Int) {
        this.color = color
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun setLocation(location: String?) {
        this.location = location
    }

    fun setAllDayEvent(allDayEvent: Boolean) {
        isAllDayEvent = allDayEvent
    }

    fun setRepetitionType(repetitionType: RepetitionTypeEnum) {
        this.repetitionType = repetitionType
    }

    fun setRepetitionType(value: Int) {
        repetitionType = when (value) {
            0 -> RepetitionTypeEnum.ONCE
            1 -> RepetitionTypeEnum.DAILY
            2 -> RepetitionTypeEnum.WEEKLY
            3 -> RepetitionTypeEnum.MONTHLY
            4 -> RepetitionTypeEnum.YEARLY
            else -> RepetitionTypeEnum.ONCE
        }
    }
}
