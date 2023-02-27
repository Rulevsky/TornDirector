package com.example.torndirector.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Settings::class], version = 1, exportSchema = false)
abstract class SettingsDatabase : RoomDatabase() {
    abstract fun settingsDatabaseDao() : SettingsDatabaseDao
}