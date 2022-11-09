package com.example.torndirector.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class EmployeesDatabase : RoomDatabase() {

    abstract fun employeeDatabaseDao(): EmployeeDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeesDatabase? = null
        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): EmployeesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EmployeesDatabase::class.java,
                        "employees_table"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(EmployeeDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

        private class EmployeeDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.employeeDatabaseDao())
                    }
                }
            }

            suspend fun populateDatabase(employeeDatabaseDao: EmployeeDatabaseDao) {
                employeeDatabaseDao.insert(Employee(1, "N/A", 0, 0))
            }
        }
    }
}