package com.example.wil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class AllTaskAdapter extends ArrayAdapter<String> {

    Activity context;
    List<String> description;
    List<String> status;
    List<String> dates;

    public AllTaskAdapter( Activity context, List<String> description, List<String> status, List<String> dates) {
        super(context,R.layout.all_task_item,description);
        this.context = context;
        this.description = description;
        this.status= status;
        this.dates = dates;
    }


    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.all_task_item, null,true);

        TextView descriptionTxt = rowView.findViewById(R.id.task_description);
        TextView datesTxt = rowView.findViewById(R.id.task_dates);


        descriptionTxt.setText(description.get(position));
        datesTxt.setText(dates.get(position));

        if(status.get(position) == "Pending"){
            rowView.setBackgroundColor(Color.BLUE);
        }
        else if(status.get(position) == "In Progress"){
            rowView.setBackgroundColor(Color.YELLOW);
        }
        else if(status.get(position) == "Done"){
            rowView.setBackgroundColor(Color.RED);
        }

        return rowView;

    };
}
