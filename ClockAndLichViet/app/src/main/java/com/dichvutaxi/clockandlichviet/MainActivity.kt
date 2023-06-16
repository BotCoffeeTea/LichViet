package com.dichvutaxi.clockandlichviet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.clockandlichviet.adapter.CalendarAdapter
import com.dichvutaxi.clockandlichviet.calendar.LunarCalendar
import com.dichvutaxi.clockandlichviet.calendar.SolarDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.calendar_recycler_view)
        calendarAdapter = CalendarAdapter(this)
        recyclerView.adapter = calendarAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        displaySolarCalendar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.solar_calendar -> {
                displaySolarCalendar()
                true
            }
            R.id.lunar_calendar -> {
                displayLunarCalendar()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displaySolarCalendar() {
        val currentDate = Calendar.getInstance().time
        val calendarList = mutableListOf<SolarDate>()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..maxDays) {
            val solarDate = SolarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, i)
            calendarList.add(solarDate)
        }
        calendarAdapter.setCalendarList(calendarList)
        supportActionBar?.title = getString(R.string.solar_calendar_title)
    }

    private fun displayLunarCalendar() {
        val currentDate = Calendar.getInstance().time
        val calendarList = mutableListOf<SolarDate>()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in 1..maxDays) {
            val solarDate = SolarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, i)
            val lunarDate = LunarCalendar.getLunarDate(solarDate)
            calendarList.add(lunarDate)
        }
        calendarAdapter.setCalendarList(calendarList)
        supportActionBar?.title = getString(R.string.lunar_calendar_title)
    }
}
