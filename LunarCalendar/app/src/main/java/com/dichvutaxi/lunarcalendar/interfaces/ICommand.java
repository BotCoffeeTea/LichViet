package com.dichvutaxi.lunarcalendar.interfaces;

import com.dichvutaxi.lunarcalendar.models.DateObject;

/**
 * Created by dichvutaxi on 11/23/2016.
 */

public interface ICommand {
    void execute(DateObject date) throws Exception;
}
