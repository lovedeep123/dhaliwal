package com.example.wil;

import java.util.ArrayList;
import java.util.List;

public class Util {

    List<String> task1_description = new ArrayList<>();
    List<String> task1_dates = new ArrayList<>();
    List<String> task1_status = new ArrayList<>();
    List<String> task2_description = new ArrayList<>();
    List<String> task2_dates = new ArrayList<>();
    List<String> task2_status = new ArrayList<>();


    public List<String> getTask1_description() {
        return task1_description;
    }

    public void setTask1_description(String task1_description_String) {
        task1_description.add(task1_description_String);
    }

    public List<String> getTask1_dates() {
        return task1_dates;
    }

    public void setTask1_dates(String task1_dates_String) {
        task1_dates.add(task1_dates_String);
    }

    public List<String> getTask1_status() {
        return task1_status;
    }

    public void setTask1_status(String task1_status_String) {
        task1_status.add(task1_status_String);
    }

    public List<String> getTask2_description() {
        return task2_description;
    }

    public void setTask2_description(String task2_description_String) {
        task2_description.add(task2_description_String);
    }

    public List<String> getTask2_dates() {
        return task2_dates;
    }

    public void setTask2_dates(String task2_dates_String) {
        task2_dates.add(task2_dates_String);
    }

    public List<String> getTask2_status() {
        return task2_status;
    }

    public void setTask2_status(String task2_status_String) {
        task2_status.add(task2_status_String);
    }

    private static Util single_instance = null;
    private Util()
    {

    }
    public static Util getInstance()
    {
        if (single_instance == null)
            single_instance = new Util();

        return single_instance;
    }

    public void updateDescAtIndex(int position, String desc){
        task1_description.set(position, desc);
    }
    public void updateStatAtIndex(int position, String stat){
        task1_status.set(position, stat);
    }
    public void updateDatAtIndex(int position, String dat){
        task1_dates.set(position, dat);
    }

}
