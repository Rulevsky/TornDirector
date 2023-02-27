package com.example.torndirector.repositories

import androidx.annotation.WorkerThread
import com.example.torndirector.room.Settings
import com.example.torndirector.room.SettingsDatabaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SettingsRepository @Inject constructor(
    private val settingsDatabaseDao: SettingsDatabaseDao
)
{
    val allSettings : Flow<List<Settings>> = settingsDatabaseDao.getAllSettings()

    @WorkerThread
    suspend fun insert(settings: Settings){
        settingsDatabaseDao.insert(settings)
    }
    @WorkerThread
    suspend fun update(settings: Settings){
        settingsDatabaseDao.update(settings)
    }

    @WorkerThread
    suspend fun findSettingsByName(search: String): Settings{
        return settingsDatabaseDao.findSettingsByName(search)
    }

    @WorkerThread
    suspend fun clear(){
        settingsDatabaseDao.clear()
    }
}