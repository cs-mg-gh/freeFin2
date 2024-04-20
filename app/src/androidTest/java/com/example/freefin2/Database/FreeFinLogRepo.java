package com.example.freefin2.Database;

import android.app.Application;

import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import android.util.Log;
public class FreeFinLogRepo {
    private FreeFinDao freefinDAO;
    private ArrayList<FreeFinUser> allLogs;
    public FreeFinLogRepo(Application application){
        FreeFinDatabase db = FreeFinDatabase.getDatabase(application);
        this.freefinDAO = db.freefinDAO();
        this.allLogs= this.freefinDAO.getAllRecords();
    }
    public ArrayList<FreeFinUser> getAllLogs(){
        Future<ArrayList<FreeFinUser>> future= FreeFinDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<FreeFinUser>>() {
                    @Override
                    public ArrayList<FreeFinUser> call() throws Exception {
                        return freefinDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG,"Problem in executor!");
        }
    return null;
    }

    public void insertFreeFinLog(FreeFinUser freefin){
        FreeFinDatabase.databaseWriteExecutor.execute(()->
        {
            freefinDAO.insert(freefin);
        });
    }
}
