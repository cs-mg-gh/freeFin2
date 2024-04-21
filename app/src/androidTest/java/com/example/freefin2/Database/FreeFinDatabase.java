package com.example.freefin2.Database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={FreeFinUser.class}, version = 1,exportSchema = false)
public abstract class FreeFinDatabase extends RoomDatabase {
    private static final String DATABASE_NAME ="FreeFin_database";
    public static final String FreeFinTable = "FreeFinTable";
    private static volatile FreeFinDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS =4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FreeFinDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (FreeFinDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder
                            (context.getApplicationContext(),
                            FreeFinDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG,"Database is created!");
        }
    };
    public abstract FreeFinDao freefinDAO();
}
