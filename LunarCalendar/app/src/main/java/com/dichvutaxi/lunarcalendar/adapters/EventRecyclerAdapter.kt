package com.dichvutaxi.lunarcalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener
import com.dichvutaxi.lunarcalendar.models.DateObject

class EventRecyclerAdapter(
    private val context: Context,
    private val items: List<DateObject>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<AgendaEventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaEventViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_agenda_event, parent, false)
        return AgendaEventViewHolder(context, v)
    }

    override fun onBindViewHolder(holder: AgendaEventViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
