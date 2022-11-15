package com.example.torndirector.ui.company

import androidx.lifecycle.*
import com.example.torndirector.repositories.CompanyRepository
import com.example.torndirector.repositories.StockRepository
import com.example.torndirector.room.Company
import com.example.torndirector.room.Stock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CompanyViewModel @Inject constructor(
    private val repository: CompanyRepository,
    private val stockRepository: StockRepository
) :
    ViewModel() {
    val companyDetails: LiveData<Company> = repository.companyDetails.asLiveData()
    val stockDetails: LiveData<Stock> = stockRepository.stock.asLiveData()
    fun insert(details: Company) = viewModelScope.launch {
        repository.insert(details)
    }
}

class CompanyDetailsViewModelFactory(private val repository: CompanyRepository, private val stockRepository: StockRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompanyViewModel::class.java)) {
            return CompanyViewModel(repository, stockRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}