package com.dichvutaxi.clockandlichviet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.clockandlichviet.R
import com.dichvutaxi.clockandlichviet.calendar.SolarDate
import java.text.DateFormatSymbols
import java.util.*

class CalendarAdapter(private val context: Context) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {

    private var calendarList = mutableListOf<SolarDate>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonthTextView: TextView = itemView.findViewById(R.id.day_of_month_text_view)
        val dayOfWeekTextView: TextView = itemView.findViewById(R.id.day_of_week_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val solarDate = calendarList[position]
        holder.dayOfMonthTextView.text = solarDate.dayOfMonth.toString()
        val calendar = Calendar.getInstance()
        calendar.set(solarDate.year, solarDate.month - 1, solarDate.dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        val weekdays = DateFormatSymbols().shortWeekdays
        holder.dayOfWeekTextView.text = weekdays[dayOfWeek]
    }

    override fun getItemCount(): Int {
        return calendarList.size
    }

    fun setCalendarList(list: List<SolarDate>) {
        calendarList.clear()
        calendarList.addAll(list)
        notifyDataSetChanged()
    }
}
