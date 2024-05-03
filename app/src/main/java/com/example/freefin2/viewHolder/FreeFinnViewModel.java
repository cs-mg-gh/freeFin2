package com.example.freefin2.viewHolder;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.freefin2.Database.FreeFinLogRepo;
import com.example.freefin2.Database.entities.FreeFinUser;

import java.util.List;

public class FreeFinnViewModel extends AndroidViewModel {
    private FreeFinLogRepo repository;

    private final LiveData<List<FreeFinUser>> allUsersbyId;

    public FreeFinnViewModel (Application application, int userId){
        super(application);
        repository = FreeFinLogRepo.getRepository(application);
        allUsersbyId = repository.getAllUsersbyId(userId);
    }
    public LiveData<List<FreeFinUser>> getAllUsersbyId(){
        return allUsersbyId;
    }


}
