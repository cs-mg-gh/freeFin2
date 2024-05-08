package com.example.freefin2.Database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.freefin2.Database.entities.Bills;
import com.example.freefin2.Database.entities.FreeFinUser;
import com.example.freefin2.Database.entities.Goals;
import com.example.freefin2.Database.entities.Notifications;
import com.example.freefin2.Database.typeConverters.LocalDateTypeConverter;
import com.example.freefin2.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities={FreeFinUser.class, Bills.class, Notifications.class, Goals.class}, version = 3,exportSchema = false)
public abstract class FreeFinDatabase extends RoomDatabase {
    public static final String FreeFinTable = "FreeFinTable";
    public static final String BillsTable ="BillsTable";
    public static final String NotificationsTable ="NotificationsTable";

    private static final String DATABASE_NAME ="FreeFinDatabase";
    public static final String GoalsTable ="GoalsTable";
    private static volatile FreeFinDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS =4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static synchronized FreeFinDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FreeFinDatabase.class, "FreeFinDatabase")
                    .fallbackToDestructiveMigration()
                    .addCallback(addDefaultValues)
                    .build();
        }
        return INSTANCE;
    }
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i("FreeFin", "Database is created!");
            databaseWriteExecutor.execute(() -> {
                FreeFinDao dao = INSTANCE.freefinDAO();
                dao.deleteAll();
                FreeFinUser admin = new FreeFinUser("admin", "admin1");
                admin.setAdmin(true);
                dao.insertUser(admin);

                FreeFinUser testUser1 = new FreeFinUser("testuser1", "testuser1");
                dao.insertUser(testUser1);

                Log.i("FreeFin", "Default users inserted.");
            });
        }
    };

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


    public abstract FreeFinDao freefinDAO();

    public abstract BillsDAO billsDAO();

    public abstract NotificationsDAO notificationsDAO();
    public abstract GoalsDAO goalsDao();

}
