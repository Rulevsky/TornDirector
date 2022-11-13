package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Room
import com.example.torndirector.room.EmployeesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Singleton
    @Provides
    fun provideMyDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        EmployeesDatabase::class.java,
        "employees_table"
    ).build()
    @Singleton
    @Provides
    fun provideMyDao(db: EmployeesDatabase) = db.employeeDatabaseDao()
}