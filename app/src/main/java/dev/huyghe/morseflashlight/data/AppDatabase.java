package dev.huyghe.morseflashlight.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

/**
 * Standard Room boilerplate for defining database objects.
 *
 * @see androidx.room.RoomDatabase
 */
@Singleton
@Database(entities = {Message.class, Category.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(4);

    public abstract MessageDAO messageDAO();

    public abstract CategoryDAO categoryDAO();
}
