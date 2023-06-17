package com.dichvutaxi.lunarcalendar.models

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.adapters.EventsRecyclerAdapter
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.fragments.MonthDayFragment
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class DisplayMonthViewCommand(private val mainActivity: MainActivity) : ICommand, View.OnClickListener {
    private var monthView: View? = null
    private var recyclerView: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: EventsRecyclerAdapter? = null
    private var currentDate: DateObject? = null
    private var selectedDay: MonthDayFragment? = null
    private val dayFragments = arrayOfNulls<MonthDayFragment>(42)

    override fun execute(date: DateObject) {
        val contentMain = mainActivity.contentMainView
        currentDate = date
        if (mainActivity.isSelectedNavMenuItemChanged) {
            contentMain.removeAllViews()
            monthView = View.inflate(mainActivity, R.layout.layout_month, contentMain)
            val previousButton = monthView!!.findViewById<Button>(R.id.btnPreviousMonth)
            previousButton.setOnClickListener(this)
            val nextButton = monthView!!.findViewById<Button>(R.id.btnNextMonth)
            nextButton.setOnClickListener(this)
            try {
                genrateCalendar(date, monthView!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        try {
            updateCalendar(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val textViewMonth = monthView!!.findViewById<TextView>(R.id.textViewMonth)
        textViewMonth.text = "Tháng " + date.month + ", " + date.year
        val lunarDate = DateConverter.convertSolar2Lunar(date, mainActivity.timeZone)
        val textViewLunarMonth = monthView!!.findViewById<TextView>(R.id.textViewLunarMonth)
        textViewLunarMonth.text = "Tháng " + lunarDate.month + ", " + DateConverter.convertToLunarYear(lunarDate.year)
        setDateInfo(date, monthView!!)
    }

    private fun setDateInfo(date: DateObject, monthView: View) {
        try {
            val todaySolar = monthView.findViewById<TextView>(R.id.textViewSolarToday)
            todaySolar.text = date.day.toString() + " tháng " + date.month + ", " + date.year
            val todayLunar = monthView.findViewById<TextView>(R.id.textViewLunarToday)
            val lunar = DateConverter.convertSolar2Lunar(date, mainActivity.timeZone)
            todayLunar.text = lunar.day.toString() + " tháng " + lunar.month + ", năm " + DateConverter.convertToLunarYear(lunar.year)
            mainActivity.events.clear()
            val dbHandler = MyDbHandler(mainActivity, null, null, 1)
            val events = dbHandler.findEvent(date.day, date.month, date.year)
            for (ev in events) {
                mainActivity.events.add(ev)
            }
            recyclerView = monthView.findViewById(R.id.recycler_view)
            layoutManager = LinearLayoutManager(mainActivity)
            recyclerView!!.layoutManager = layoutManager
            adapter = EventsRecyclerAdapter(mainActivity, mainActivity.events, mainActivity)
            recyclerView!!.adapter = adapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    private fun genrateCalendar(date: DateObject, monthView: View) {
        val fm = mainActivity.fragmentManager
        for (i in 0..5) {
            for (j in 0..6) {
                dayFragments[i * 7 + j] = MonthDayFragment.newInstance(1, 1, 2000, false)
                val strId = "md" + (i + 1) + "" + (j + 1)
                val resId = mainActivity.resources.getIdentifier(strId, "id", mainActivity.packageName)
                val ll = monthView.findViewById<LinearLayout>(resId)
                val ft = fm.beginTransaction()
                ft.add(ll.id, dayFragments[i * 7 + j]!!, strId)
                ft.commit()
            }
        }
    }

    @Throws(Exception::class)
    private fun updateCalendar(date: DateObject) {
        selectedDay?.setSelected(false)
        val cal = Calendar.getInstance()
        cal.set(date.year, date.month - 1, date.day)
        val maxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val startDay = cal.get(Calendar.DAY_OF_WEEK)
        val lunarDate = DateConverter.convertSolar2Lunar(date, mainActivity.timeZone)
        var lunarDay = lunarDate.day
        var lunarMonth = lunarDate.month
        var lunarYear = lunarDate.year
        for (i in 1..42) {
            if (i <= startDay - 1 || i > maxDays + startDay - 1) {
                dayFragments[i - 1]?.setDay(0)
                dayFragments[i - 1]?.setLunarDay(0, 0, 0, false)
            } else {
                val dayNum = i - startDay + 1
                dayFragments[i - 1]?.setDay(dayNum)
                dayFragments[i - 1]?.setLunarDay(lunarDay, lunarMonth, lunarYear, false)
                if (dayNum == date.day) {
                    dayFragments[i - 1]?.setSelected(true)
                    selectedDay = dayFragments[i - 1]
                } else {
                    dayFragments[i - 1]?.setSelected(false)
                }
                lunarDay++
                val maxLunarDays = DateConverter.getMaxDayOfMonthLunar(lunarMonth, lunarYear)
                if (lunarDay > maxLunarDays) {
                    lunarDay = 1
                    lunarMonth++
                    if (lunarMonth > 12) {
                        lunarMonth = 1
                        lunarYear++
                    }
                }
            }
        }
    }

    override fun onClick(view: View) {
        val cal = Calendar.getInstance()
        cal.set(currentDate!!.year, currentDate!!.month - 1, 1)
        when (view.id) {
            R.id.btnPreviousMonth -> {
                cal.add(Calendar.MONTH, -1)
                mainActivity.displayMonthView(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            }
            R.id.btnNextMonth -> {
                cal.add(Calendar.MONTH, 1)
                mainActivity.displayMonthView(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR))
            }
        }
    }
}
