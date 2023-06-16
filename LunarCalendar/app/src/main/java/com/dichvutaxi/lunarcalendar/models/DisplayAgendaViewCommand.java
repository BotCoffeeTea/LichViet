package com.dichvutaxi.lunarcalendar.models;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.dichvutaxi.lunarcalendar.R;
import com.dichvutaxi.lunarcalendar.activities.MainActivity;
import com.dichvutaxi.lunarcalendar.activities.ViewEventActivity;
import com.dichvutaxi.lunarcalendar.adapters.AgendaEventRecyclerAdapter;
import com.dichvutaxi.lunarcalendar.adapters.EventRecyclerAdapter;
import com.dichvutaxi.lunarcalendar.data.MyDbHandler;
import com.dichvutaxi.lunarcalendar.interfaces.ICommand;
import com.dichvutaxi.lunarcalendar.interfaces.OnItemClickListener;
import com.dichvutaxi.lunarcalendar.utils.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by dichvutaxi on 12/8/2016.
 */

public class DisplayAgendaViewCommand implements ICommand, OnItemClickListener {
    private MainActivity mainActivity;
    private View agendaView;
    private DateObject currentDate;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private AgendaEventRecyclerAdapter adapter;

    public DisplayAgendaViewCommand(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void execute(DateObject date) throws Exception {
        ViewGroup contentMain = this.mainActivity.getContentMainView();
        contentMain.removeAllViews();
        agendaView = View.inflate(this.mainActivity, R.layout.layout_agenda, contentMain);

        ArrayList<DateObject> dates = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() - 1, date.getDay());
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DATE);
        MyDbHandler dbHandler = new MyDbHandler(this.mainActivity, null, null, 1);
        for (int i = 1; i<= lastDayOfMonth; i++) {
            DateObject solar = new DateObject(i, date.getMonth(), date.getYear());
            DateObject lunar = DateConverter.convertSolar2Lunar(solar, this.mainActivity.getTimeZone());
            int count = dbHandler.findEventCount(lunar.getDay(), lunar.getMonth(), lunar.getYear());
            if (count > 0) {
                dates.add(lunar);
            }
        }
        recyclerView = (RecyclerView) agendaView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this.mainActivity);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AgendaEventRecyclerAdapter(this.mainActivity, dates, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(EventObject item) {

    }
}
