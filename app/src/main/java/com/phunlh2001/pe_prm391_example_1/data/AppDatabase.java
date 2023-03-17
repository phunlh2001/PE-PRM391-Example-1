package com.phunlh2001.pe_prm391_example_1.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1) // 2
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

//    static Migration migration_from_1_to_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // ALTER TABLE <table_name> ADD COLUMN <column_name> <datatype>
//            database.execSQL("ALTER TABLE users ADD COLUMN year TEXT");
//        }
//    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,"PE-Example")
                    .allowMainThreadQueries()
//                    .addMigrations(migration_from_1_to_2)
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDAO userDAO();
}
