package com.example.torndirector.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "settings_table")
data class Settings(
    @PrimaryKey(autoGenerate = false)
    val settingsKey: Int,

    @ColumnInfo(name = "settings_name")
    val settingsName: String,

    @ColumnInfo(name = "settings_value")
    val settingsValue: String
    )
{
}