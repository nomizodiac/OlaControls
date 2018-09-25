package com.example.invotyx.olacontrols;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SceneViewAdapter extends ArrayAdapter<Scenes> {

    private Context mContext;
    private List<Scenes> sceneList = new ArrayList<>();

    public SceneViewAdapter(@NonNull Context context, ArrayList<Scenes> list) {
        super(context,0, list);
        mContext = context;
        sceneList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.scenes_cell,parent,false);

        Scenes currentScene = sceneList.get(position);


        ImageView sceneIcon = (ImageView) listItem.findViewById(R.id.scene_icon);
       sceneIcon.setImageResource(currentScene.getScene_icon());

        SwitchCompat scene_switch = listItem.findViewById(R.id.scene_switch);
        scene_switch.setText(currentScene.getScene_name());

        TextView add_scene = listItem.findViewById(R.id.add_scene);
        if(currentScene.getScene_name()=="Add Scene")
        {
            scene_switch.setVisibility(View.GONE);
            add_scene.setVisibility(View.VISIBLE);
        }

        return listItem;
    }
}
