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
    private  ArrayList<FreeFinUser> allUsers;
    private static FreeFinLogRepo repository;
    private FreeFinLogRepo(Application application){
        FreeFinDatabase db = FreeFinDatabase.getDatabase(application);
        this.freefinDAO = db.freefinDAO();
        this.allUsers= (ArrayList<FreeFinUser>) this.freefinDAO.getAllUsers();
    }
    public static FreeFinLogRepo getRepository(Application application){
        if(repository!=null){
            return repository;
        }
        Future<FreeFinLogRepo> future = FreeFinDatabase.databaseWriteExecutor.submit(
                        new Callable<FreeFinLogRepo>(){

                            @Override
                            public FreeFinLogRepo call() throws Exception {
                                return new FreeFinLogRepo(application);
                            }
                        }
        );
        try{
            return future.get();
        }catch(InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG,"Problem getting Repo, Thread error.");
        }
        return null;
    }

    public static void setRepository(FreeFinLogRepo repository) {
        FreeFinLogRepo.repository = repository;
    }

    public ArrayList<FreeFinUser> getAllLogs(){
        Future<ArrayList<FreeFinUser>> future= FreeFinDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<FreeFinUser>>() {
                    @Override
                    public ArrayList<FreeFinUser> call() throws Exception {
                        return (ArrayList<FreeFinUser>) freefinDAO.getAllUsers();
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

    public void insertUser(FreeFinUser... user){
        FreeFinDatabase.databaseWriteExecutor.execute(()->
        {
            freefinDAO.insert(user);
        });
    }

    public ArrayList<FreeFinUser> getAllUsers() {
        return allUsers;
    }

    public void setAllUsers(ArrayList<FreeFinUser> allUsers) {
        this.allUsers = allUsers;
    }
}
