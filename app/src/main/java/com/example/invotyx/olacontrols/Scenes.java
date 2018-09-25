package com.example.invotyx.olacontrols;

import android.graphics.drawable.Icon;

public class Scenes{

    private String scene_name;
    private String scene_id;
    private int scene_icon;
    private boolean status_icon;

    Scenes(String name)
    {
        this.scene_name = name;
        this.scene_icon = R.drawable.mainscreen_illumination; //example icon
        this.status_icon = true;
    }
    Scenes()
    {
        this.scene_name="Add Scene";
        this.scene_icon=R.drawable.macro_add;
    }

    public int getScene_icon() {
        return scene_icon;
    }

    public boolean getStatus_icon() {
        return status_icon;
    }

    public String getScene_id() {
        return scene_id;
    }

    public String getScene_name() {
        return scene_name;
    }
}
