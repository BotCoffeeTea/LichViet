package com.dichvutaxi.lunarcalendar.utils
import com.dichvutaxi.lunarcalendar.models.LunarCalendar
import java.util.*
class LunarCalendarConverter {

    private val lunarInfo = intArrayOf(0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0)
    /**
     * Tính ngày dương lịch từ ngày âm lịch được cung cấp.
     */
    fun lunarToSolar(lunarDate: LunarDate): SolarDate {
        val daysOffset = lunarDateToDaysOffset(lunarDate)
        var solarYear = 1900
        var solarMonth = 1
        var solarDay = 1

        while (daysOffset > 0) {
            val daysInYear = solarDaysInYear(solarYear)
            val daysInMonth = solarDaysInMonth(solarYear, solarMonth)

            if (daysOffset - daysInMonth >= 0) {
                solarDay++
                daysOffset -= daysInMonth
            } else {
                if (solarMonth == 2 && isLeapYear(solarYear)) {
                    if (daysOffset - daysInMonth - 1 >= 0) {
                        solarDay++
                        daysOffset -= daysInMonth + 1
                    } else {
                        break
                    }
                } else {
                    if (daysOffset - daysInMonth >= 0) {
                        solarDay++
                        daysOffset -= daysInMonth
                    } else {
                        break
                    }
                }
            }

            if (solarDay > daysInMonth) {
                solarMonth++
                solarDay = 1
            }

            if (solarMonth > 12) {
                solarYear++
                solarMonth = 1
            }
        }

        return SolarDate(solarYear, solarMonth, solarDay)
    }

    /**
     * Tính ngày âm lịch từ ngày dương lịch được cung cấp.
     */
    fun solarToLunar(solarDate: SolarDate): LunarDate {
        val julianDay = julianDay(solarDate.year, solarDate.month, solarDate.day)
        val lunarYearStartTiếp tục mã nguồn hoàn chỉnh của hàm `solarToLunar` trong Kotlin:

        val lunarYearStart = 1900
        val lunarMonthStart = 1
        val lunarDayStart = 31
        val offset = julianDay.toInt() - julianDay(lunarYearStart, lunarMonthStart, lunarDayStart).toInt()
        var lunarYear = lunarYearStart
        var lunarMonth = 1
        var lunarDay = 1
        var isLeapMonth = false

        while (offset >= 0) {
            val daysInMonth = lunarDaysInMonth(lunarYear, lunarMonth)

            if (offset - daysInMonth >= 0) {
                if (lunarMonth == getLeapMonth(lunarYear)) {
                    if (!isLeapMonth) {
                        isLeapMonth = true
                        lunarDay--
                    } else {
                        isLeapMonth = false
                    }
                }

                offset -= daysInMonth
                lunarDay++
                lunarMonth++
            } else {
                if (offset == (lunarLeapMonth(lunarYear) - lunarMonth) && (lunarMonth < lunarLeapMonth(lunarYear))) {
                    isLeapMonth = true
                    lunarMonth++
                    lunarDay = 1
                } else if (lunarMonth == lunarLeapMonth(lunarYear) && isLeapMonth) {
                    offset -= lunarDaysInMonth(lunarYear, lunarMonth) + 1
                    if (offset >= 0) {
                        lunarMonth++
                        isLeapMonth = false
                        lunarDay = 1
                    } else {
                        lunarDay--
                    }
                } else {
                    if (lunarMonth == lunarLeapMonth(lunarYear) && !isLeapMonth) {
                        isLeapMonth = true
                    }
                    lunarDay += offset
                    offset = -1
                }
            }

            if (lunarMonth > 12) {
                lunarYear++
                lunarMonth = 1
            }
        }

        return LunarDate(lunarYear, lunarMonth, lunarDay, isLeapMonth)
    }
}
