package com.dichvutaxi.lunarcalendar.utils
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import com.dichvutaxi.lunarcalendar.models.LunarCalendar
import com.dichvutaxi.lunarcalendar.utils.LunarCalendarConverter
import com.dichvutaxi.lunarcalendar.utils.HoangDaoUtils
import com.dichvutaxi.lunarcalendar.utils.CanChiUtils

object SolarTermUtils {
    private val solarTerms = arrayOf(
        "Tiểu hàn", "Đại hàn",
        "Lập xuân", "Vũ Thủy",
        "Kinh trập", "Xuân phân",
        "Thanh minh", "Cốc vũ",
        "Lập hạ", "Tiểu mãn",
        "Mang chủng", "Hạ chí",
        "Tiểu thử", "Đại thử",
        "Lập thu", "Xử thử",
        "Bạch lộ", "Thu phân",
        "Hàn lộ", "Sương giáng",
        "Lập đông", "Tiểu tuyết",
        "Đại tuyết", "Đông chí"
    )

    private val solarTermsDates: Array<LocalDate> by lazy {
        // Tính ngày julian day tương ứng với ngày 1900-01-06 (là Tiểu hàn đầu tiên trong chu kỳ 60 ngày)
        val jd19000106 = solarDateToJulianDay(SolarDate(1900, 1, 6))
        val solarTermsDates = Array(24) { i ->
            // Tính ngày julian day tương ứng với ngày n+15*xi (xi là thứ tự của tiết khí trong chu kỳ 60 ngày)
            val jd = (jd19000106 + 15.0 * i) % 360
            julianDayToSolarDate(jd)
        }
        solarTermsDates
    }

    // Hàm tính ngày julian day tương ứng với một ngày dương lịch
    private fun solarDateToJulianDay(solarDate: SolarDate): Double {
        val year = solarDate.year
        val month = solarDate.month
        val day = solarDate.day

        val a = (14 - month) / 12
        val y = year + 4800 - a
        val m = month + 12 * a - 3

        var jd = day + ((m * 153 + 2) / 5 + y * 365 + y / 4 - y / 100 + y / 400 - 32045).toDouble()

        // Chuyển đổi múi giờ về múi giờ GMT
        val zoneId = ZoneId.systemDefault()
        val offset = zoneId.rules.getOffset(solarDate.atStartOfDay(zoneId).toInstant())
        jd -= offset.totalSeconds / (24.0 * 60 * 60)

        return jd
    }

    // Hàm tính ngày dương lịch tương ứng với một ngày julian day
    private fun julianDayToSolarDate(jd: Double): LocalDate {
        // Chuyển đổi múi giờ GMT về múi giờ địa phương
        val zoneId = ZoneId.systemDefault()
        val offset = zoneId.rules.getOffset(Instant.EPOCH)
        val localDateTime = LocalDateTime.ofEpochSecond((jd + offset.totalSeconds) * 24 * 60 * 60, 0, offset)
        return localDateTime.toLocalDate()
    }

    // Hàm tính tiết khí của một ngày dương lịch
    fun getSolarTerm(solarDate: SolarDate): String {
        val jd = solarDateToJulianDay(solarDate)

        // Tìm tiết khí gần nhất trước ngày được cho
        var index = ((jd + 15.0) / 30.0).toInt()
        if (index >= solarTerms.size) {
            index = 0
        }

        // Kiểm tra ngày được cho có nằm trong khoảng của tiết khí này hay không
        val solarTermStartDate = solarTermsDates[index]
        val nextSolarTermStartDate = solarTermsDates[(index + 1) % solarTermsDates.size]
        val daysBetween = ChronoUnit.DAYS.between(solarTermStartDate, nextSolarTermStartDate)
        val daysSinceSolarTermStart = ChronoUnit.DAYS.between(solarTermStartDate, solarDate.toLocalDate())

        return if (daysSinceSolarTermStart < daysBetween / 2) {
            solarTerms[index]
        } else {
            solarTerms[(index + 1) % solarTerms.size]
        }
    }

    // Hàm tính ngày dương lịch tương ứng với một ngày âm lịch
    fun getSolarDateFromLunarDate(lunarDate: LunarDate): SolarDate {
        val daysOffset = lunarDateToDaysOffset(lunarDate)
        val jd19000131 = solarDateToJulianDay(SolarDate(1900, 1, 31))
        val jd = jd19000131 + daysOffset
        return julianDayToSolarDate(jd).let { SolarDate(it.year, it.monthValue, it.dayOfMonth) }
    }
}
