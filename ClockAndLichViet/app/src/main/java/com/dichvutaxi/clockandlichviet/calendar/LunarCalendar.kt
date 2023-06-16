package com.dichvutaxi.clockandlichviet.calendar

object LunarCalendar {
    private val LUNAR_MONTH_DAYS = intArrayOf(
        29, 30, 29, 30, 29, 30, 29, 30, 29, 30, 29, 30
    )

    private val LUNAR_MONTH_NAMES = arrayOf(
        "Giêng", "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Chạp"
    )

    private val CAN_NAMES = arrayOf(
        "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý"
    )

    private val CHI_NAMES = arrayOf(
        "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tị", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi"
    )

    private val LUNAR_START_DATE = SolarDate(1900, 1, 31)

    private fun getLunarMonthDays(lunarYear: Int, lunarMonth: Int): Int {
        val leapMonth = getLeapMonth(lunarYear)
        return if (lunarMonth == leapMonth) {
            LUNAR_MONTH_DAYS[lunarMonth - 1] + if (isLeapMonth(lunarYear, lunarMonth)) 1 else 0
        } else {
            LUNAR_MONTH_DAYS[lunarMonth - 1]
        }
    }

    private fun getLeapMonth(lunarYear: Int): Int {
        return LunarData.LUNAR_MONTH_DAYS[lunarYear - LunarData.LUNAR_START_YEAR] shr 4
    }

    private fun isLeapMonth(lunarYear: Int, lunarMonth: Int): Boolean {
        return (LunarData.LUNAR_MONTH_DAYS[lunarYear - LunarData.LUNAR_START_YEAR] and (0x10000 shr lunarMonth)) != 0
    }

    private fun getLunarYearDays(lunarYear: Int): Int {
        var days = 0
        for (i in 0..11) {
            days += getLunarMonthDays(lunarYear, i + 1)
        }
        return days
    }

    private fun getLunarMonthName(lunarMonth: Int, isLeapMonth: Boolean): String {
        return if (isLeapMonth) "Nhuận ${LUNAR_MONTH_NAMES[lunarMonth - 1]}" else LUNAR_MONTH_NAMES[lunarMonth - 1]
    }

    fun getLunarDate(solarDate: SolarDate): LunarDate {
        val solarCal = solarDate.toCalendar()
        val baseCal = LUNAR_START_DATE.toCalendar()
        val offset = ((solarCal.timeInMillis - baseCal.timeInMillis) / 86400000L).toInt()
        var days = 0
        var lunarYear = 0
        var lunarMonth = 0
        var lunarDay = 0
        while (lunarYear < 2100 && days < offset) {
            val yearDays = getLunarYearDays(lunarYear)
            days += yearDays
            lunarYear++
        }
        if (days == offset) {
            lunarYear--
            lunarMonth = 12
            lunarDay = 29
        } else {
            days -= getLunarYearDays(lunarYear - 1)
            lunarYear--
            while (lunarMonth < 12 && days < offset) {
                val monthDays = getLunarMonthDays(lunarYear, lunarMonth + 1)
                days += monthDays
                lunarMonth++
            }
            if (days == offset) {
                lunarDay = getLunarMonthDays(lunarYear, lunarMonth)
            } else {
                days -= getLunarMonthDays(lunarYear, lunarMonth)
                lunarDay = days + 1
            }
        }
        val isLeapMonth = isLeapMonth(lunarYear, lunarMonth)
        val lunarMonthName = getLunarMonthName(lunarMonth, isLeapMonth)
        val canIndex = (lunarYear + 6) % 10
        val chiIndex = (lunarYear + 8) % 12
        val lunarYearName = CAN_NAMES[canIndex] + " " + CHI_NAMES[chiIndex]
    return LunarDate(lunarYear, lunarMonth, lunarDay, isLeapMonth, lunarMonthName, lunarYearName)
    }
}
