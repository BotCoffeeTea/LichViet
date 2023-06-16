object CanChiUtils {
    private val can = arrayOf("Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ")
    private val chi = arrayOf("Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi")

    // Hàm tính Can chi của một ngày dương lịch
    fun getCanChiFromDate(solarDate: SolarDate): String {
        // Tính ngày julian day tương ứng với ngày 1900-01-31 (âm lịch)
        val jd19000131 = SolarTermUtils.solarDateToJulianDay(SolarDate(1900, 1, 31))

        // Tính số ngày chênh lệch giữa ngày được cho và ngày 1900-01-31
        val daysOffset = ChronoUnit.DAYS.between(SolarDate(1900, 1, 31).toLocalDate(), solarDate.toLocalDate())

        // Tính Can chi của ngày được cho
        val canIndex = ((jd19000131 + daysOffset) % 10).toInt()
        val chiIndex = ((jd19000131 + daysOffset) % 12).toInt()
        return "${can[canIndex]} ${chi[chiIndex]}"
    }

    // Hàm tính Can chi của một năm âm lịch
    fun getCanChiFromLunarYear(lunarYear: Int): String {
        val canIndex = (lunarYear - 4) % 10
        val chiIndex = (lunarYear - 4) % 12
        return "${can[canIndex]} ${chi[chiIndex]}"
    }
}
