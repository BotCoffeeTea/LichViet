object HoangDaoUtils {
    private val hoangDao = arrayOf(
        intArrayOf(23, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11),
        intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8),
        intArrayOf(6, 7, 8, 9, 10, 0, 1, 2, 3, 4, 5, 6),
        intArrayOf(2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 1),
        intArrayOf(10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
        intArrayOf(5, 6, 7, 8, 9, 10, 0, 1, 2, 3, 4, 5),
        intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 1)
    )

    // Hàm kiểm tra xem một ngày có phải là ngày hoàng đạo hay không
    fun isHoangDao(lunarDate: LunarDate): Boolean {
        val month = lunarDate.month - 1
        val day = lunarDate.day - 1
        val chi = lunarDate.chi
        return chi == hoangDao[month][day]
    }
}
