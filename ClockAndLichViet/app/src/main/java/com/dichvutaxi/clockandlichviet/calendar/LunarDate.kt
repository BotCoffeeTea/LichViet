package com.dichvutaxi.clockandlichviet.calendar

class LunarDate(val year: Int, val month: Int, val day: Int, val isLeapMonth: Boolean) {
    companion object {
        private val LUNAR_MONTHS =
            arrayOf("Giêng", "Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "Tám", "Chín", "Mười", "Mười một", "Chạp")

        private val LUNAR_DAYS = arrayOf(
            "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20", "21", "22",
            "23", "24", "25", "26", "27", "28", "29",
            "30"
        )
    }

    override fun toString(): String {
        val monthStr = if (isLeapMonth) "Nhuận $LUNAR_MONTHS[month - 1]" else LUNAR_MONTHS[month - 1]
        return "$day $monthStr năm $year"
    }
}
