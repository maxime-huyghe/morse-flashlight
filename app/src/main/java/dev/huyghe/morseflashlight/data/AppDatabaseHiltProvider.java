package dev.huyghe.morseflashlight.data;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppDatabaseHiltProvider {
    @Singleton
    @Provides
    AppDatabase provideAppDatabase(
            @ApplicationContext Context applicationContext
    ) {
        return Room.databaseBuilder(
                applicationContext,
                AppDatabase.class,
                "Morse_2023-01-07.4"
        ).build();
    }

    @Singleton
    @Provides
    MessageDAO provideMessageDAO(AppDatabase appDatabase) {
        return appDatabase.messageDAO();
    }

    @Singleton
    @Provides
    CategoryDAO provideCategoryDAO(AppDatabase appDatabase) {
        return appDatabase.categoryDAO();
    }
}
