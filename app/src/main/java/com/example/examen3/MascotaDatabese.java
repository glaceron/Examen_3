package com.example.examen3;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Mascota.class, version = 4)
public abstract class MascotaDatabese extends RoomDatabase
{
    private static MascotaDatabese instance;

    public abstract MascotaDao mascotaDao();

    public static synchronized MascotaDatabese getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), MascotaDatabese.class,"mascota_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private MascotaDao mascotaDao;

        private PopulateDbAsyncTask(MascotaDatabese db)
        {
            mascotaDao = db.mascotaDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            mascotaDao.insert(new Mascota("adam","podenco","2","https://dam.org.es/images/adam.jpg"));
            mascotaDao.insert(new Mascota("ohana","mezcla","1","https://dam.org.es/images/ohana.jpg"));
            mascotaDao.insert(new Mascota("scaar","mezcla","1","https://dam.org.es/images/scaar.jpg"));
            mascotaDao.insert(new Mascota("yana","foxterrier","3","https://dam.org.es/images/yana.jpg"));
            return null;
        }
    }
}
