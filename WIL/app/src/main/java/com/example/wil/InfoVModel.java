package com.example.wil;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class InfoVModel extends AndroidViewModel {

    private InfoRepository infoRepository;
    private List<Info> allData;

    public InfoVModel(@NonNull Application application) {
        super(application);
        infoRepository = new InfoRepository(application);
        allData = infoRepository.getAllData();
    }
    public void insert(Info note) {
        Log.d("DATABASE::", "insert: in VModel");
        infoRepository.insert(note);
    }

    public void update(Info note) {
        infoRepository.update(note);
    }

    public void updateFirstId() {
        infoRepository.updateFirstRow();
    }

    public void delete(Info note) {
        infoRepository.delete(note);
    }

    public List<Info> getAllNotes() {
        return allData;
    }
}
