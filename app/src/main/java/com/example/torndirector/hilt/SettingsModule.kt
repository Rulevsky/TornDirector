package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Room
import com.example.torndirector.room.SettingsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {
    @Singleton
    @Provides
    fun provideSettingsDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SettingsDatabase::class.java,
        "settings_table"
    ).build()

    @Singleton
    @Provides
    fun provideSettingsDao(db: SettingsDatabase) = db.settingsDatabaseDao()
}