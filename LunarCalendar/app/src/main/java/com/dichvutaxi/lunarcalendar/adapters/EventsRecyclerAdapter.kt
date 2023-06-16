package com.dichvutaxi.lunarcalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.models.EventObject

class EventsRecyclerAdapter(
    private val context: Context,
    private val items: List<EventObject>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_event, parent, false)
        return EventsViewHolder(context, v)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
