package com.example.invotyx.olacontrols;

import android.graphics.drawable.Icon;

public class Macros {

    private String macro_name;
    private String macro_id;
    private int macro_icon;
    private int status_icon;
    private int status_id;

   Macros(String name, int status_id)
    {
        this.macro_name = name;
        this.status_id = status_id;
        this.macro_icon = R.drawable.mainscreen_camera; //example icon
        setStatus_icon(status_id);
    }
    Macros()
    {
        this.macro_name = "Add Macro";
        this.macro_icon = R.drawable.macro_add;

    }

    public void setStatus_icon(int status_id) {
       if(status_id==0)
        this.status_icon = R.drawable.macro_off;
       else
           this.status_icon=R.drawable.macro_on;
    }

    public String getMacro_id() {
        return macro_id;
    }

    public String getMacro_name() {
        return macro_name;
    }

    public int getMacro_icon() {
        return macro_icon;
    }

    public int getStatus_icon() {
        return status_icon;
    }
}
