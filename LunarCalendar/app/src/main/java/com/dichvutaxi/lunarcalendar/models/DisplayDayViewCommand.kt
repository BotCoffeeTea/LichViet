package com.dichvutaxi.lunarcalendar.models

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.activities.ViewEventActivity
import com.dichvutaxi.lunarcalendar.adapters.EventsRecyclerAdapter
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.interfaces.ICommand
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.utils.DateConverter

class DisplayDayViewCommand(private val mainActivity: MainActivity) : ICommand, View.OnClickListener {
    private lateinit var dayView: View
    private lateinit var currentDate: DateObject
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: EventsRecyclerAdapter

    override fun execute(date: DateObject) {
        currentDate = date
        val contentMain: ViewGroup = mainActivity.contentMainView
        contentMain.removeAllViews()
        dayView = View.inflate(mainActivity, R.layout.layout_day, contentMain)
        setInfo(date)
    }

    override fun onClick(view: View) {
        val id = view.id
        try {
            val cal = Calendar.getInstance()
            cal.set(currentDate.year, currentDate.month - 1, currentDate.day)
            if (id == R.id.btnNextDay) {
                cal.add(Calendar.DAY_OF_YEAR, 1)
                val newDate = DateObject(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR),
                    0
                )
                mainActivity.setSelectedSolarDate(newDate)
            } else if (id == R.id.btnPreviousDay) {
                cal.add(Calendar.DAY_OF_YEAR, -1)
                val newDate = DateObject(
                    cal.get(Calendar.DAY_OF_MONTH),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.YEAR),
                    0
                )
                mainActivity.setSelectedSolarDate(newDate)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setInfo(date: DateObject) {
        try {
            val btnPrevious = dayView.findViewById<Button>(R.id.btnPreviousDay)
            btnPrevious.setOnClickListener(this)
            val btnNext = dayView.findViewById<Button>(R.id.btnNextDay)
            btnNext.setOnClickListener(this)
            val lunarDate = DateConverter.convertSolar2Lunar(date, mainActivity.timeZone)
            val cal = Calendar.getInstance()
            cal.set(currentDate.year, currentDate.month - 1, currentDate.day)
            val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
            val textViewDay = dayView.findViewById<TextView>(R.id.textViewDay)
            textViewDay.text = DateConverter.VN_DAYS[dayOfWeek - 1]
            val textViewSolarMonth = dayView.findViewById<TextView>(R.id.textViewSolarMonth)
            textViewSolarMonth.text = "Tháng ${date.month}, ${date.year}"
            val textViewSolarDay = dayView.findViewById<TextView>(R.id.textViewSolarDay)
            textViewSolarDay.text = date.day.toString()
            val textViewLunarMonth = dayView.findViewById<TextView>(R.id.textViewLunarMonth)
            textViewLunarMonth.text = "Tháng ${lunarDate.month}"
            val textViewLunarDay = dayView.findViewById<TextView>(R.id.textViewLunarDay)
            textViewLunarDay.text = lunarDate.day.toString()
            val textViewLunarYear = dayView.findViewById<TextView>(R.id.textViewLunarYear)
            textViewLunarYear.text = "Năm " + DateConverter.convertToLunarYear(lunarDate.year)
            val textViewNgayCanChi = dayView.findViewById<TextView>(R.id.textViewDayCanChi)
            val dayCanChi = DateConverter.convertToCanChiDay(
                currentDate.day, currentDate.month, currentDate.year
            )
            textViewNgayCanChi.text = "Ngày $dayCanChi"
            val textViewMonthCanChi = dayView.findViewById<TextView>(R.id.textViewMonthCanChi)
            val monthCanChi = DateConverter.convertToCanChiMonth(
                this.currentDate.month, this.currentDate.year)
            textViewMonthCanChi.text = "Tháng $monthCanChi"
            val textViewHourCanChi = dayView.findViewById<TextView>(R.id.textViewHourCanChi)
            val hourCanChi = DateConverter.convertToCanChiHour(
                this.currentDate.day,
                this.currentDate.month,
                this.currentDate.year,
                0
            )
            textViewHourCanChi.text = "Giờ $hourCanChi"
            val textViewTietAmLich =dayView.findViewById<TextView>(R.id.textViewTietAmLich)
            val tietAmLich = DateConverter.getTietKhiAmLich(lunarDate)
            textViewTietAmLich.text = tietAmLich
            recyclerView = dayView.findViewById(R.id.recyclerViewEvents)
            layoutManager = LinearLayoutManager(mainActivity)
            recyclerView.layoutManager = layoutManager
            adapter = EventsRecyclerAdapter(mainActivity, MyDbHandler(mainActivity))
            recyclerView.adapter = adapter
            adapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    val intent = Intent(mainActivity, ViewEventActivity::class.java)
                    intent.putExtra("event_id", adapter.getItem(position).id)
                    mainActivity.startActivity(intent)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
