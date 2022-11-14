package com.example.torndirector.room

import androidx.room.*
import com.example.torndirector.retrofit.model.CompanyDetails
import com.example.torndirector.retrofit.model.CompanyModel
import kotlinx.coroutines.flow.Flow


@Dao
interface CompanyDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(companyDetails: Company)
    @Update
    suspend fun update(companyDetails: Company)
    @Query("DELETE FROM company_table")
    suspend fun clear()

    @Query("SELECT * from company_table ORDER BY detailsKey ASC")
    fun getCompanyDetails(): Flow<Company>
}