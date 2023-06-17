package com.dichvutaxi.lunarcalendar.models

import android.view.View
import android.widget.TextView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.utils.Constants
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import com.google.android.material.navigation.NavigationView
import java.util.*

class BackgroundWorker(private val mainActivity: MainActivity) : Runnable {

    override fun run() {
        setNavigationViewInfo()
    }

    fun setNavigationViewInfo() {
        try {
            val navigationView = mainActivity.findViewById<NavigationView>(R.id.nav_view)
            val cal = Calendar.getInstance()
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
            val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH) + 1
            val year = cal.get(Calendar.YEAR)
            val today = DateObject(dayOfMonth, month, year, 0)
            mainActivity.setTodaySolarDate(today)
            val lunarDate = DateConverter.convertSolar2Lunar(today, mainActivity.getTimeZone())
            val header = navigationView.getHeaderView(0)
            val textViewDay = header.findViewById<TextView>(R.id.textViewDay)
            textViewDay.text = Constants.DAYS[dayOfWeek]
            val textViewDate = header.findViewById<TextView>(R.id.textViewDate)
            textViewDate.text = "$dayOfMonth tháng $month, $year"
            //val time = SimpleDateFormat("hh:mm:ss a").format(cal.time)
            //val textViewTime = header.findViewById<TextView>(R.id.textViewTime)
            //textViewTime.text = time
            val textViewLunarDate = header.findViewById<TextView>(R.id.textViewLunarDate)
            textViewLunarDate.text = "${lunarDate.day} tháng ${lunarDate.month}, năm ${DateConverter.convertToLunarYear(lunarDate.year)}"
            val menu = navigationView.menu
            val mnuMonth = menu.getItem(0)
            mnuMonth.title = "Tháng (tháng $month)"
            val mnuDay = menu.getItem(1)
            mnuDay.title = "Ngày ($dayOfMonth tháng $month)"
            val mnuAgenda = menu.getItem(2)
            mnuAgenda.title = "Sự kiện ($dayOfMonth tháng $month)"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
