package com.dichvutaxi.lunarcalendar.adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.models.EventObject
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val itemColor: TextView = itemView.findViewById(R.id.textViewEventColor)
    private val itemName: TextView = itemView.findViewById(R.id.textViewEventName)
    private val itemTime: TextView = itemView.findViewById(R.id.textViewEventTime)
    private val itemLocation: TextView = itemView.findViewById(R.id.textViewEventLocation)
    private var context: Context? = null

    constructor(context: Context, v: View) : this(v) {
        this.context = context
    }

    fun getContext(): Context? {
        return context
    }

    fun bind(eventObject: EventObject, listener: OnItemClickListener) {
        itemColor.setBackgroundColor(context!!.resources.getColor(eventObject.color))
        itemName.text = eventObject.name
        if (eventObject.isAllDayEvent) {
            itemTime.text = context!!.resources.getText(R.string.all_day_event)
        } else {
            val cal = Calendar.getInstance()
            cal.time = eventObject.fromDate
            val strFrom = String.format(
                Locale.getDefault(), "%tT ngày %s tháng %s năm %s",
                cal,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                DateConverter.convertToLunarYear(cal.get(Calendar.YEAR))
            )
            cal.time = eventObject.toDate
            val strTo = String.format(
                Locale.getDefault(), "%tT ngày %s tháng %s năm %s",
                cal,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                DateConverter.convertToLunarYear(cal.get(Calendar.YEAR))
            )
            itemTime.text = "$strFrom - $strTo"
        }
        itemLocation.text = eventObject.location
        itemView.setOnClickListener { listener.onItemClick(eventObject) }
    }
}
