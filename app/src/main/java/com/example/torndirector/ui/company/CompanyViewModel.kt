package com.example.torndirector.ui.company

import androidx.lifecycle.*
import com.example.torndirector.repositories.CompanyRepository
import com.example.torndirector.repositories.EmployeeRepository
import com.example.torndirector.repositories.SettingsRepository
import com.example.torndirector.repositories.StockRepository
import com.example.torndirector.room.Company
import com.example.torndirector.room.Employee
import com.example.torndirector.room.Settings
import com.example.torndirector.room.Stock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val stockRepository: StockRepository,
    //test
    private val employeesRepository: EmployeeRepository,
    private val settingsRepository: SettingsRepository
) :
    ViewModel() {
    val companyDetails: LiveData<Company> = companyRepository.companyDetails.asLiveData()
    val stockDetails: LiveData<Stock> = stockRepository.stock.asLiveData()

    //test
    val employeesLiveData: LiveData<List<Employee>> = employeesRepository.allEmployees.asLiveData()
    val settingsLiveData: LiveData<List<Settings>> =  settingsRepository.allSettings.asLiveData()


    fun insert(details: Company) = viewModelScope.launch {
        companyRepository.insert(details)
    }
}

class CompanyDetailsViewModelFactory(
    private val companyRepository: CompanyRepository,
    private val stockRepository: StockRepository,
    private val employeesRepository: EmployeeRepository,
    private val settingsRepository: SettingsRepository
    ) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompanyViewModel::class.java)) {
            return CompanyViewModel(
                companyRepository,
                stockRepository,
                //test
                employeesRepository,
                settingsRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}