package com.example.torndirector.hilt

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.torndirector.room.CompanyDatabaseDao
import com.example.torndirector.room.Employee
import com.example.torndirector.room.EmployeeDatabaseDao
import com.example.torndirector.room.EmployeesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
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




