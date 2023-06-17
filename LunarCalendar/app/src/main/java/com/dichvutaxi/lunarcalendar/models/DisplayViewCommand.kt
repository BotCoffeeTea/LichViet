package com.dichvutaxi.lunarcalendar.models

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.activities.ViewEventActivity
import com.dichvutaxi.lunarcalendar.adapters.EventRecyclerAdapter
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.interfaces.ICommand
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class DisplayViewCommand(private val mainActivity: MainActivity) : ICommand,
    OnItemClickListener {
    private lateinit var eventView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: EventRecyclerAdapter
    private lateinit var currentDate: DateObject

    @Throws(Exception::class)
    override fun execute(date: DateObject) {
        val contentMain = mainActivity.contentMainView
        contentMain.removeAllViews()
        eventView = View.inflate(mainActivity, R.layout.layout_event, contentMain)

        val dates = ArrayList<DateObject>()
        val cal = Calendar.getInstance()
        cal.set(date.year, date.month - 1, date.day)
        val lastDayOfMonth = cal.getActualMaximum(Calendar.DATE)
        val dbHandler = MyDbHandler(mainActivity, null, null, 1)
        for (i in 1..lastDayOfMonth) {
            val solar = DateObject(i, date.month, date.year)
            val lunar = DateConverter.convertSolar2Lunar(solar, mainActivity.timeZone)
            val count = dbHandler.findEventCount(lunar.day, lunar.month, lunar.year)
            if (count > 0) {
                dates.add(lunar)
            }
        }
        recyclerView = eventView.findViewById(R.id.recycler_view)
        layoutManager = LinearLayoutManager(mainActivity)
        recyclerView.layoutManager = layoutManager

        adapter = EventRecyclerAdapter(mainActivity, dates, this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(item: EventObject) {}
}
