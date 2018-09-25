package com.example.invotyx.olacontrols;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MacroViewAdapter extends ArrayAdapter<Macros> {

    private Context mContext;
    private List<Macros> macroList = new ArrayList<>();

    public MacroViewAdapter(@NonNull Context context, ArrayList<Macros> list) {
        super(context,0, list);
        mContext = context;
        macroList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.macros_cell,parent,false);

        Macros currentMacro = macroList.get(position);

        TextView macroName = (TextView) listItem.findViewById(R.id.macro_name);
        macroName.setText(currentMacro.getMacro_name());

        ImageView macroIcon = (ImageView) listItem.findViewById(R.id.macro_icon);
        macroIcon.setImageResource(currentMacro.getMacro_icon());

        ImageView statusIcon = (ImageView) listItem.findViewById(R.id.macros_status);
        statusIcon.setImageResource(currentMacro.getStatus_icon());
        if(currentMacro.getMacro_name()=="Add Macro")
        {
            statusIcon.setVisibility(View.GONE);
        }

        return listItem;
    }



}
