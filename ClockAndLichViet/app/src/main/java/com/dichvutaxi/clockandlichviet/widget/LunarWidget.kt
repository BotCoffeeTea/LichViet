package com.dichvutaxi.clockandlichviet.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.clockandlichviet.calendar.LunarCalendar
import com.dichvutaxi.clockandlichviet.calendar.LunarDate
import java.util.*

class LunarWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val currentDate = Calendar.getInstance().time
            val lunarDate = LunarCalendar.getLunarDate(currentDate)
            val remoteViews = RemoteViews(context.packageName, R.layout.widget_lunar)
            remoteViews.setTextViewText(R.id.lunar_date_text, lunarDate.toString())
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}
