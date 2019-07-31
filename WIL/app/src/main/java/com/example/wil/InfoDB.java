package com.example.wil;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = Info.class,version = 9, exportSchema = false)
public abstract class InfoDB extends RoomDatabase {

    public abstract InfoDao infoDao();

    private static InfoDB instance;

    public static synchronized InfoDB getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context,InfoDB.class,"Info_DATABASE")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private InfoDao noteDao;
        private PopulateDbAsyncTask(InfoDB noteDatabase){
            this.noteDao = noteDatabase.infoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
