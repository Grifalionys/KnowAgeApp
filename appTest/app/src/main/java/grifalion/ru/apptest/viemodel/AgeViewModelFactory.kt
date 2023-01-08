package grifalion.ru.apptest.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import grifalion.ru.apptest.repository.AgeRepository

class AgeViewModelFactory (
    private val repository: AgeRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AgeViewModel(repository) as T
        }
}