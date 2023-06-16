package com.dichvutaxi.lunarcalendar.models
import com.dichvutaxi.lunarcalendar.utils.LunarCalendarConverter
import java.util.*
data class SolarDate(val year: Int, val month: Int, val day: Int)

data class LunarDate(val year: Int, val month: Int, val day: Int, val isLeapMonth: Boolean = false)

object LunarCalendar {
    /**
     * Tính số ngày từ ngày 1/1/4713 TCN đến ngày Julian được cung cấp.
     */
    private fun julianDay(year: Int, month: Int, day: Int): Double {
    val a = (14 - month) / 12
    val y = year + 4800 - a
    val m = month + 12 * a - 3
    val jdn = day + (((153 * m + 2) / 5) + 365 * y + y / 4 - y / 100 + y / 400 - 32045)
    return jdn + 0.5
}

    /**
     * Tính số ngày trong một tháng âm lịch.
     */
        private fun lunarDaysInMonth(lunarYear: Int, lunarMonth: Int): Int {
        val bit = if (lunarLeapMonth(lunarYear) == lunarMonth) 1 else 0
        val lunarInfo = this.lunarInfo[lunarYear - 1900]
        return 29 + ((lunarInfo shr (16 - lunarMonth - bit)) and 0x01)
    }

    private fun lunarLeapMonth(lunarYear: Int): Int {
        val lunarInfo = this.lunarInfo[lunarYear - 1900]
        return (lunarInfo shr 20) and 0x0F
    }

    private fun isLeapMonth(lunarYear: Int, lunarMonth: Int): Boolean {
        return lunarLeapMonth(lunarYear) == lunarMonth
    }

    private fun getLeapMonth(lunarYear: Int): Int {
        return lunarLeapMonth(lunarYear) + 1
    }

    private fun lunarDateToDaysOffset(lunarDate: LunarDate): Int {
        var lunarDay = lunarDate.day
        val lunarMonth = lunarDate.month
        var lunarYear = lunarDate.year
        val lunarLeapMonth = lunarDate.isLeapMonth

        var daysOffset = 0
        var leapMonth = getLeapMonth(lunarYear)

        for (i in 1900 until lunarYear) {
            daysOffset += lunarDaysInYear(i)
        }

        var isLeapMonth = false
        for (i in 1 until lunarMonth) {
            if (leapMonth > 0 && i == leapMonth && !isLeapMonth) {
                isLeapMonth = true
                i -= 1
            } else {
                isLeapMonth = false
            }
            daysOffset += lunarDaysInMonth(lunarYear, i)
        }

        if (lunarLeapMonth && lunarMonth > leapMonth) {
            daysOffset += lunarDaysInMonth(lunarYear, lunarMonth - 1)
        }

        daysOffset += lunarDay - 1

        return daysOffset
    }

    private fun lunarDaysInYear(lunarYear: Int): Int {
        var sum = 348
        for (i in 0..12) {
            if (lunarDaysInMonth(lunarYear, i) == 29) sum += 1
        }
        return sum + lunarLeapMonth(lunarYear)
    }

    private fun solarDaysInMonth(solarYear: Int, solarMonth: Int): Int {
        return when (solarMonth) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(solarYear)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month")
        }
    }

    private fun solarDaysInYear(solarYear: Int): Int {
        return if (isLeapYear(solarYear)) 366 else 365
    }

    private fun isLeapYear(solarYear: Int): Boolean {
        return (solarYear % 4 == 0 && solarYear % 100 != 0) || solarYear % 400 == 0
    }
}
