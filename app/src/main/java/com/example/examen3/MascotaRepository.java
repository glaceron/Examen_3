package com.example.examen3;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MascotaRepository
{
    private MascotaDao mascotaDao;
    private LiveData<List<Mascota>> allReservas;

    public MascotaRepository(Application application)
    {
        MascotaDatabese databese = MascotaDatabese.getInstance(application);
        mascotaDao = databese.mascotaDao();
        allReservas = mascotaDao.getAllMascotas();
    }

    public void insert(Mascota mascota)
    {
        new InsertMascotaAsyncTask(mascotaDao).execute(mascota);
    }

    public void update(Mascota mascota)
    {
        new UpdateMascotaAsyncTask(mascotaDao).execute(mascota);
    }
    public void delete(Mascota mascota)
    {
        new DeleteMascotaAsyncTask(mascotaDao).execute(mascota);
    }

    public void deleteAllMascotas()
    {
        new DeleteAllMascotasAsyncTask(mascotaDao).execute();
    }

    public LiveData<List<Mascota>> getAllMascotas()
    {
        return allReservas;
    }

    private static class UpdateMascotaAsyncTask extends AsyncTask<Mascota,Void,Void>
    {
        private MascotaDao mascotaDao;

        private UpdateMascotaAsyncTask(MascotaDao mascotaDao)
        {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas)
        {
            mascotaDao.update(mascotas[0]);
            return null;
        }
    }

    private static class DeleteMascotaAsyncTask extends AsyncTask<Mascota,Void,Void>
    {
        private MascotaDao mascotaDao;

        private DeleteMascotaAsyncTask(MascotaDao mascotaDao)
        {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas)
        {
            mascotaDao.delete(mascotas[0]);
            return null;
        }
    }

    private static class InsertMascotaAsyncTask extends AsyncTask<Mascota,Void,Void>
    {
        private MascotaDao mascotaDao;

        private InsertMascotaAsyncTask(MascotaDao mascotaDao)
        {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Mascota... mascotas)
        {
            mascotaDao.insert(mascotas[0]);
            return null;
        }
    }

    private static class DeleteAllMascotasAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private MascotaDao mascotaDao;

        private DeleteAllMascotasAsyncTask(MascotaDao mascotaDao)
        {
            this.mascotaDao = mascotaDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            mascotaDao.deleteAllMascotas();
            return null;
        }
    }
}
