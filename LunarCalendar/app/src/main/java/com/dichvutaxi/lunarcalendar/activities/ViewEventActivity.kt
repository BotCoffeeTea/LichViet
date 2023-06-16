package com.dichvutaxi.lunarcalendar.activities

import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.fragments.EventColorFragment
import com.dichvutaxi.lunarcalendar.fragments.EventPropertiesFragment
import com.dichvutaxi.lunarcalendar.interfaces.IDialogEventListener
import com.dichvutaxi.lunarcalendar.models.EventObject
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class ViewEventActivity : AppCompatActivity(), IDialogEventListener {

    private var selectedColor = R.color.colorRed
    private var eventId = 0
    private lateinit var layoutViewEvent: View
    private lateinit var event: EventObject
    private lateinit var textViewEventName: TextView
    private lateinit var textViewEventTime: TextView
    private lateinit var textViewEventLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)
        val extras = intent.extras
        if (extras == null) {
            return
        }
        eventId = extras.getInt("id")
        val dbHandler = MyDbHandler(this, null, null, 1)
        event = dbHandler.findEvent(eventId)
        selectedColor = event.color
        layoutViewEvent = findViewById(R.id.layout_view_event)
        layoutViewEvent.setBackgroundColor(resources.getColor(selectedColor))
        textViewEventName = findViewById(R.id.textViewEventName)
        textViewEventTime = findViewById(R.id.textViewEventTime)
        textViewEventLocation = findViewById(R.id.textViewEventLocation)
        setEventInfo()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_view_event_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.event_color -> {
                val eventColor = EventColorFragment()
                eventColor.show(fragmentManager, "EventColorFragment")
            }
            R.id.event_delete -> {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Sự kiện")
                    .setMessage("Xóa sự kiện?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialogInterface: DialogInterface?, i: Int ->
                        val dbHandler = MyDbHandler(this, null, null, 1)
                        dbHandler.deleteEvent(eventId)
                        this@ViewEventActivity.finish()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface?, i: Int -> dialogInterface?.cancel() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
            R.id.event_edit -> {
                try {
                    val eventProperties = EventPropertiesFragment()
                    eventProperties.event = event
                    eventProperties.show(fragmentManager, "EventPropertiesFragment")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        if (dialog is EventColorFragment) {
            selectedColor = dialog.selectedColor
            event.color = selectedColor
            val dbHandler = MyDbHandler(this, null, null, 1)
            dbHandler.updateEvent(event)
            layoutViewEvent.setBackgroundColor(resources.getColor(selectedColor))
        } else if (dialog is EventPropertiesFragment) {
            val eventProperties = dialog
            val dbHandler = MyDbHandler(this, null, null, 1)
            dbHandler.updateEvent(eventProperties.event)
            setEventInfo()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        dialog.dialog.cancel()
    }

    override fun finish() {
        val data = Intent()
        data.putExtra("selectedColor", selectedColor)
        data.putExtra("id", eventId)
        setResult(RESULT_OK, data)
        super.finish()
    }

    private fun setEventInfo() {
        textViewEventName.text= event.name
        textViewEventLocation.text = event.location
        if (event.isAllDayEvent) {
            textViewEventTime.text = resources.getText(R.string.all_day_event)
        } else {
            val cal = Calendar.getInstance()
            cal.time = event.fromDate
            val strFrom = String.format(
                "%tT ngày %s tháng %s năm %s",
                cal,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                DateConverter.convertToLunarYear(cal.get(Calendar.YEAR))
            )
            cal.time = event.toDate
            val strTo = String.format(
                "%tT ngày %s tháng %s năm %s",
                cal,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.MONTH) + 1,
                DateConverter.convertToLunarYear(cal.get(Calendar.YEAR))
            )
            textViewEventTime.text = "$strFrom - $strTo"
        }
    }
}
