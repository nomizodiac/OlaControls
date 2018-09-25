package dataclasses;

import com.example.invotyx.olacontrols.R;

import java.util.ArrayList;

public class Rooms {

    private String room_id;
    private String room_name;
    private int room_icon;
    private String room_icon_name;
    private String room_camera;
    private String room_camera_password;
    private ArrayList<Target> targets;


    public Rooms(String room_id, String room_name)
    {
        this.room_id=room_id;
        this.room_name=room_name;
        setRoomIcon();
    }

    public Rooms(String room_id, String room_name, int room_icon)
    {
        this.room_id=room_id;
        this.room_name=room_name;
        this.room_icon = room_icon;

    }

    public void setRoom_icon(int room_icon) {
        this.room_icon = room_icon;
    }

    public String getRoom_camera() {
        return room_camera;
    }

    public void setRoom_camera_password(String room_camera_password) {
        this.room_camera_password = room_camera_password;
    }

    public void setId(String id)
    {
        this.room_id= id;
    }
    public void setName(String name)
    {
        this.room_name = name;
    }

    public void setTargets(ArrayList<Target> target) {
        this.targets = target;
    }

    public String getId()
    {
        return this.room_id;
    }
    public String getName()
    {
        return this.room_name;
    }
    public int getIcon()
    {
        return this.room_icon;
    }

    public ArrayList<Target> getTargets() {
        return targets;
    }

    public void setRoomIcon()
    {
        switch (room_name)
        {
            case "Living Room" : this.room_icon =  R.drawable.mainscreen_livingroom;
                break;
            case "Kitchen" : this.room_icon = R.drawable.mainscreen_kitchen;
                break;
            case "Bathroom" : this.room_icon = R.drawable.mainscreen_bathroom;
                break;
            case "Add Room" : this.room_icon = R.drawable.macro_add;
            break;

            default: this.room_icon = R.drawable.mainscreen_livingroom;
        }
    }





}

