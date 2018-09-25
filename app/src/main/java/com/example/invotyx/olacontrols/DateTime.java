package com.example.invotyx.olacontrols;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTime {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("dd MMM yyyy  |  hh:mm:ss a");
    String dateTime = mdformat.format(calendar.getTime());

    public String getDateTime() {
        return dateTime;
    }
}
