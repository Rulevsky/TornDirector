package com.example.torndirector.repositories

import androidx.annotation.WorkerThread
import com.example.torndirector.room.CompanyDatabaseDao
import com.example.torndirector.room.Company
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject


class CompanyRepository @Inject constructor(
    private val companyDatabaseDao: CompanyDatabaseDao
){
    val companyDetails: Flow<Company> = companyDatabaseDao.getCompanyDetails()

    @WorkerThread
    suspend fun insert(companyDetails: Company){
        companyDatabaseDao.insert(companyDetails)
    }

    @WorkerThread
    suspend fun update(companyDetails: Company) {
        companyDatabaseDao.update(companyDetails)
    }
}