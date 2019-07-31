package com.example.wil;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;

public class InfoRepository {

    private InfoDao infoDao;
    private List<Info> allData;

    public InfoRepository(Application application){
        InfoDB infoDB = InfoDB.getInstance(application);
        infoDao = infoDB.infoDao();
        allData = infoDao.getAllData();

    }

    public void insert(Info info){
        Log.d("DATABASE::", "insert: inside Repo");
        new InsertAsyncTask(infoDao).execute(info);
    }

    public void update(Info info){
        new UpdateAsyncTask(infoDao).execute(info);
    }

    public void updateFirstRow(){
        new UpdateFirstRowAsyncTask(infoDao).execute();
    }

    public void delete(Info info){
        new DeleteAsyncTask(infoDao).execute(info);
    }

    public List<Info> getAllData() {
        return allData;
    }

    private class InsertAsyncTask extends AsyncTask<Info,Void,Void> {

        private InfoDao noteDao;

        InsertAsyncTask(InfoDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Info... infos) {
            noteDao.insert(infos[0]);
            Log.d("DATABASE::", "doInBackground: inside asynctask");
            return null;
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Info,Void,Void>{

        private InfoDao noteDao;

        UpdateAsyncTask(InfoDao noteDao){
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Info... infos) {
            noteDao.update(infos[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Info,Void,Void>{

        private InfoDao noteDao;

        DeleteAsyncTask(InfoDao noteDao){
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Info... infos) {
            noteDao.delete(infos[0]);
            return null;
        }
    }

    private class UpdateFirstRowAsyncTask extends AsyncTask<Void,Void,Void> {
        private InfoDao noteDao;
        public UpdateFirstRowAsyncTask(InfoDao infoDao) {
            this.noteDao = infoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteFirstRow();
            return null;
        }
    }
}
