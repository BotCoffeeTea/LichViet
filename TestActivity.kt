import android.app.DatePickerDialog

import android.app.TimePickerDialog

import android.os.Bundle

import android.text.TextUtils

import android.view.View

import android.widget.DatePicker

import android.widget.TextView

import android.widget.TimePicker

import androidx.appcompat.app.AlertDialog

import androidx.appcompat.app.AppCompatActivity

import java.util.*

class TestActivity : AppCompatActivity() {

    private lateinit var mThangDuongTextView: TextView

    private lateinit var mNgayDuongTextView: TextView

    private lateinit var mThuDuongTextView: TextView

    private lateinit var mThangAmTextView: TextView

    private lateinit var mCanChiThangTextView: TextView

    private lateinit var mNgayAmTextView: TextView

    private lateinit var mCanChiNgayTextView: TextView

    private lateinit var mNamAmTextView: TextView

    private lateinit var mCanChiGioTextView: TextView

    private lateinit var mDayInfoTextView: TextView

    private lateinit var mHoangDaoTextView: TextView

    private lateinit var mCalendar: Calendar

    private val mDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

        mCalendar.set(Calendar.YEAR, year)

        mCalendar.set(Calendar.MONTH, monthOfYear)

        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        updateDateTimeViews()

    }

    private val mTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)

        mCalendar.set(Calendar.MINUTE, minute)

        updateDateTimeViews()

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        mThangDuongTextView = findViewById(R.id.thangduong)

        mNgayDuongTextView = findViewById(R.id.ngayduong)

        mThuDuongTextView = findViewById(R.id.thuduong)

        mThangAmTextView = findViewById(R.id.thangam)

        mCanChiThangTextView = findViewById(R.id.canchithang)

        mNgayAmTextView = findViewById(R.id.ngayam)

        mCanChiNgayTextView = findViewById(R.id.canchingay)

        mNamAmTextView = findViewById(R.id.namam)

        mCanChiGioTextView = findViewById(R.id.canchigio)

        mDayInfoTextView = findViewById(R.id.dayinfo)

        mHoangDaoTextView = findViewById(R.id.hoangdao)

        mCalendar = Calendar.getInstance()

        updateDateTimeViews()

        mNgayDuongTextView.setOnClickListener {

            showDatePickerDialog()

        }

        mThangDuongTextView.setOnClickListener {

            showDatePickerDialog()

        }

        mNgayAmTextView.setOnClickListener {

            showDatePickerDialog()

        }

        mThangAmTextView.setOnClickListener {

            showDatePickerDialog()

        }

    }

    private fun showDatePickerDialog() {

        DatePickerDialog(

            this, mDateSetListener,

            mCalendar.get(Calendar.YEAR),

            mCalendar.get(Calendar.MONTH),

            mCalendar.get(Calendar.DAY_OF_MONTH)

        ).show()

    }

    private fun showTimePickerDialog() {

        TimePickerDialog(

            this, mTimeSetListener,

            mCalendar.get(Calendar.HOUR_OF_DAY),

            mCalendar.get(Calendar.MINUTE), true

        ).show()

    }

    private fun updateDateTimeViews() {

        val year = mCalendar.get(Calendar.YEAR)

        val month = mCalendar.get(Calendar.MONTH)

        val dayOfMonth = mCalendar.get(Calendar.DAY_OF_MONTH)

        val hourOfDay = mCalendar.get(Calendar.HOUR_OF_DAY)

        val minute = mCalendar.get(Calendar.MINUTE)

        // Update duong lich views

        mThangDuongTextView.text = String.format("Tháng %d năm %d", month + 1, year)

        mNgayDuongTextView.text = dayOfMonth.toString()

        mThuDuongTextView.text = getThuDuong(dayOfMonth, month + 1, year)

        // Update am lich views

        val lunarDate = LunarCalendarConverter.convertSolar2Lunar(dayOfMonth, month + 1, year, 7)

        mThangAmTextView.text = getThangAm(lunarDate)

        mCanChiThangTextView.text = getCanChi(lunarDate.lunarYear, lunarDate.lunarMonth)

        mNgayAmTextView.text = lunarDate.lunarDay.toString()

        mCanChiNgayTextView.text = getCanChi(lunarDate.lunarYear, lunarDate.lunarDay)

        mNamAmTextView.text = getNamAm(lunarDate.lunarYear)

        mCanChiGioTextView.text = getCanChiGio(hourOfDay)

        // Update day info textview

        val dayInfo = getDayInfo(dayOfMonth, month + 1, year)

        mDayInfoTextView.text = dayInfo

        // Update hoang dao text view

        val hoangDao = getHoangDao(hourOfDay)

        mHoangDaoTextView.text = hoangDao

    }

    private fun getThuDuong(day: Int, month: Int, year: Int): String {

        val calendar = Calendar.getInstance()

        calendar.set(year, month - 1, day)

        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        return when (dayOfWeek) {

            Calendar.SUNDAY -> "Chủ nhật"

            Calendar.MONDAY -> "Thứ hai"

            Calendar.TUESDAY -> "Thứ ba"

            Calendar.WEDNESDAY -> "Thứ tư"

            Calendar.THURSDAY -> "Thứ năm"

            Calendar.FRIDAY -> "Thứ sáu"

            Calendar.SATURDAY -> "Thứ bảy"

            else -> ""

        }

    }

    private fun getThangAm(lunarDate: LunarDate): String {

        return when (lunarDate.lunarMonth) {

            1 -> "Tháng giêng"

            2 -> "Tháng hai"

            3 -> "Tháng ba"

            4 -> "Tháng tư"

            5 -> "Tháng năm"

            6 -> "Tháng sáu"

            7 -> "Tháng bảy"

            8 -> "Tháng tám"

            9 -> "Tháng chín"

            10 -> "Tháng mười"

            11 -> "Tháng mười một"

            12 -> "Tháng mười hai"

            else -> ""

        }

    }

    private fun getCanChi(year: Int, monthOrDay: Int): String {

        val can = arrayOf("Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ")

        val chi = arrayOf("Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi")

        return can[(year % 10 + 6) % 10] + " " + chi[(monthOrDay - 1) / 2]

    }

    private fun getNamAm(lunarYear: Int): String {

        val can = arrayOf("Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ")

        val chi = arrayOf("Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi")

        return can[(lunarYear % 10 + 6) % 10] + " " + chi[(lunarYear - 4) % 12]

    }

    private fun getCanChiGio(hourOfDay: Int): String {

        val can = arrayOf("Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ", "Canh", "Tân", "Nhâm", "Quý")

        val chi = arrayOf("Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")

        return can[hourOfDay / 2] + " " + chi[hourOfDay % 12]

    }

    private fun getDayInfo(day: Int, month: Int, year: Int): String {

        val solarTerm = SolarTermUtil.getSolarTerm(day, month, year)

        if (!TextUtils.isEmpty(solarTerm)) {

            return "Giờ chuyển " + solarTerm

        }

        val lunarDate = LunarCalendarConverter.convertSolar2Lunar(day, month, year, 7)

        val canChiNgay = getCanChi(lunarDate.lunarYear, lunarDate.lunarDay)

        val canChiThang = getCanChi(lunarDate.lunarYear, lunarDate.lunarMonth)

        return "Ngày " + canChiNgay + ", tháng " + canChiThang

    }

    private fun getHoangDao(hourOfDay: Int): String {

    val hoangDao = arrayOf("Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi", "Thân", "Dậu", "Tuất", "Hợi")

    val index = (hourOfDay + 1) / 2 % 12

    return "Giờ hoàng đạo: ${hoangDao[index]}"

}

private object SolarTermUtil {

    // Các ngày chuyển tiết trong năm

    private val SOLAR_TERMS = arrayOf(

            intArrayOf(6, 2, 315), intArrayOf(21, 2, 330), intArrayOf(6, 3, 0), intArrayOf(21, 3, 15),

            intArrayOf(5, 4, 30), intArrayOf(20, 4, 45), intArrayOf(6, 5, 60), intArrayOf(21, 5, 75),

            intArrayOf(6, 6, 90), intArrayOf(21, 6, 105), intArrayOf(7, 7, 120), intArrayOf(23, 7, 135),

            intArrayOf(7, 8, 150), intArrayOf(23, 8, 165), intArrayOf(8, 9, 180), intArrayOf(23, 9, 195),

            intArrayOf(8, 10, 210), intArrayOf(23, 10, 225), intArrayOf(7, 11, 240), intArrayOf(22, 11, 255),

            intArrayOf(7, 12, 270), intArrayOf(22, 12, 285), intArrayOf(5, 1, 300)

    )

    fun getSolarTerm(day: Int, month: Int, year: Int): String? {

        var solarTerm: String? = null

        for (i in SOLAR_TERMS.indices) {

            val solarTermDay = SOLAR_TERMS[i][0]

            val solarTermMonth = SOLAR_TERMS[i][1]

            val solarTermAngle = SOLAR_TERMS[i][2]

            if (solarTermDay == day && solarTermMonth == month) {

                solarTerm = getSolarTermName(solarTermAngle)

                break

            }

        }

        return solarTerm

    }

    private fun getSolarTermName(angle: Int): String {

        return when (angle) {

            15 -> "Lập xuân"

            30 -> "Vũ Thủy"

            45 -> "Kinh trập"

            60 -> "Xuân phân"

            75 -> "Thanh minh"

            90 -> "Cốc vũ"

            105 -> "Lập hạ"

            120 -> "Tiểu mãn"

            135 -> "Mang chủng"

            150 -> "Hạ chí"

            165 -> "Tiểu thử"

            180 -> "Đại thử"

            195 -> "Lập thu"

            210 -> "Xử thử"

            225 -> "Bạch lộ"

            240 -> "Thu phân"

            255 -> "Hàn lộ"

            270 -> "Sương giáng"

            285 -> "Lập đông"

            300 -> "Tiểu tuyết"

            else -> ""

        }

    }

}

}
