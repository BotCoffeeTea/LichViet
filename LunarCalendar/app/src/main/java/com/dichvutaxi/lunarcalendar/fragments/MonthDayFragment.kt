package com.dichvutaxi.lunarcalendar.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dichvutaxi.lunarcalendar.R
import com.dichvutaxi.lunarcalendar.activities.MainActivity
import com.dichvutaxi.lunarcalendar.data.MyDbHandler
import com.dichvutaxi.lunarcalendar.models.DateObject
import com.dichvutaxi.lunarcalendar.models.EventObject
import com.dichvutaxi.lunarcalendar.utils.DateConverter
import java.util.*

class MonthDayFragment : Fragment(), View.OnClickListener {
    private var mDay = 0
    private var mMonth = 0
    private var mYear = 0
    private var mIsCurrent = false
    private var events: Array<EventObject>? = null
    private var isSelected = false
    private var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments == null) {
            return
        }
        if (mDay == 0) {
            mDay = arguments!!.getInt(ARG_DAY)
        }
        if (mMonth == 0) {
            mMonth = arguments!!.getInt(ARG_MONTH)
        }
        if (mYear == 0) {
            mYear = arguments!!.getInt(ARG_YEAR)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_month_day, container, false)
        this.view = view
        update(mDay, mMonth, mYear, mIsCurrent)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onClick(v: View) {
        try {
            val activity = activity as MainActivity
            val date = DateObject(mDay, mMonth, mYear, 0)
            activity.setSelectedSolarDate(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun update(dd: Int, mm: Int, yyyy: Int, isCurrent: Boolean) {
        mDay = dd
        mMonth = mm
        mYear = yyyy
        mIsCurrent = isCurrent
        if (view == null) {
            return
        }
        val rootView = view!!.findViewById<View>(R.id.rootLayoutMonthDayFrag)
        val gridLayout = view!!.findViewById<View>(R.id.gridLayoutMonthDayFrag) as LinearLayout
        gridLayout.setOnClickListener(this)
        val textViewSolarDay = view!!.findViewById<View>(R.id.textViewMonthSolarDay) as TextView
        textViewSolarDay.text = mDay.toString()
        val textViewLunarDay = view!!.findViewById<View>(R.id.textViewMonthLunarDay) as TextView
        val activity = activity as MainActivity
        try {
            val date = DateObject(mDay, mMonth, mYear)
            val dbHandler = MyDbHandler(activity, null, null, 1)
            events = dbHandler.findEvent(date.day, date.month, date.year)
            val eventView = view!!.findViewById<View>(R.id.layoutMonthDayEvent) as LinearLayout
            eventView.removeAllViews()
            val min = Math.min(3, events!!.size)
            for (i in 0 until min) {
                val tv = TextView(activity)
                val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                llp.setMargins(1, 1, 1, 1)
                tv.layoutParams = llp
                tv.height = 10
                tv.width = 20
                tv.setBackgroundColor(resources.getColor(events!![i].color))
                eventView.addView(tv)
            }
            if (mDay == activity.todaySolarDate.day &&
                    mMonth == activity.todaySolarDate.month &&
                    mYear == activity.todaySolarDate.year) {
                rootView.setBackgroundColor(activity.resources.getColor(R.color.colorToday))
                gridLayout.setBackgroundColor(activity.resources.getColor(R.color.colorToday))
            } else {
                rootView.setBackgroundColor(Color.WHITE)
                gridLayout.setBackgroundColor(Color.WHITE)
            }
            val selected = activity.selectedSolarDate
            if (selected.day == mDay &&
                    selected.month == mMonth &&
                    selected.year == mYear) {
                setSelected(true)
            }
            val d = DateObject(mDay, mMonth, mYear, 0)
            val lunar = DateConverter.convertSolar2Lunar(d, activity.timeZone)
            var lunarDay = lunar.day.toString()
            if (lunarDay.length == 1) {
                lunarDay = "0$lunarDay"
            }
            val lunarMonth = DateConverter.LUNAR_MONTH[lunar.month - 1]
            textViewLunarDay.text = "$lunarDay $lunarMonth"

        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }

    fun setSelected(selected: Boolean) {
        isSelected = selected
        val rootView = view!!.findViewById<View>(R.id.rootLayoutMonthDayFrag)
        val textViewSolarDay = view!!.findViewById<View>(R.id.textViewMonthSolarDay) as TextView
        val textViewLunarDay = view!!.findViewById<View>(R.id.textViewMonthLunarDay) as TextView
        if (isSelected) {
            rootView.setBackgroundResource(R.drawable.bg_selected_day)
            textViewSolarDay.setTextColor(Color.WHITE)
            textViewLunarDay.setTextColor(Color.WHITE)
        } else {
            rootView.setBackgroundColor(Color.WHITE)
            textViewSolarDay.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            textViewLunarDay.setTextColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }

    companion object {
        private const val ARG_DAY = "day"
        private const val ARG_MONTH = "month"
        private const val ARG_YEAR = "year"
        private const val TAG = "MonthDayFragment"

        fun newInstance(day: Int, month: Int, year: Int): MonthDayFragment {
            val fragment = MonthDayFragment()
            val args = Bundle()
            args.putInt(ARG_DAY, day)
            args.putInt(ARG_MONTH, month)
            args.putInt(ARG_YEAR, year)
            fragment.arguments = args
            return fragment
        }
    }
}
