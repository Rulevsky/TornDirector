package com.example.torndirector.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)
    @Update
    suspend fun update(settings: Settings)
    @Query("DELETE FROM settings_table")
    suspend fun clear()
    @Query("SELECT * FROM settings_table WHERE settings_name LIKE :search")
    suspend fun  findSettingsByName(search: String): Settings

    @Query("SELECT * FROM settings_table ORDER BY settingsKey ASC")
    fun getAllSettings(): Flow<List<Settings>>
}