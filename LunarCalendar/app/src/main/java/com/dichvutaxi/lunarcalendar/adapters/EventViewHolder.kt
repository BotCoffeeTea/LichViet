package com.dichvutaxi.lunarcalendar.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.activities.ViewEventActivity
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.models.DateObject
import com.dichvutaxi.lunarcalendar.models.EventObject
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemDay: TextView = itemView.findViewById(R.id.textViewDay)
    private val itemDate: TextView = itemView.findViewById(R.id.textViewDate)
    private var context: Context? = null
    private val recyclerView: RecyclerView = itemView.findViewById(R.id.event_recycler_view)
    private val layoutManager: LinearLayoutManager = LinearLayoutManager(itemView.context)
    private var adapter: EventsRecyclerAdapter? = null

    constructor(context: Context, v: View) : this(v) {
        this.context = context
        recyclerView.layoutManager = layoutManager
    }

    fun getContext(): Context? {
        return context
    }

    fun bind(dateObject: DateObject, listener: OnItemClickListener) {
        val cal = Calendar.getInstance()
        cal.set(dateObject.year, dateObject.month - 1, dateObject.day)
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        itemDay.text = DateConverter.VN_DAYS[dayOfWeek - 1]
        itemDate.text = String.format(
            "Ngày %s tháng %s năm %s",
            dateObject.day,
            dateObject.month,
            DateConverter.convertToLunarYear(dateObject.year)
        )
        if (dayOfWeek == 1) {
            itemDay.setTextColor(Color.RED)
            itemDate.setTextColor(Color.RED)
        } else if (dayOfWeek == 7) {
            itemDay.setTextColor(Color.BLUE)
            itemDate.setTextColor(Color.BLUE)
        }
        val dbHandler = MyDbHandler(context, null, null, 1)
        val results = dbHandler.findEvent(dateObject.day, dateObject.month, dateObject.year)
        val events = ArrayList<EventObject>()
        for (i in results.indices) {
            events.add(results[i])
        }
        adapter = EventsRecyclerAdapter(context!!, events, object : OnItemClickListener {
            override fun onItemClick(item: EventObject) {
                val intent = Intent(context, ViewEventActivity::class.java)
                intent.putExtra("id", item.id)
                (context as MainActivity).startActivityForResult(intent, MainActivity.REQUEST_CODE)
            }
        })
        recyclerView.adapter = adapter
    }
}
