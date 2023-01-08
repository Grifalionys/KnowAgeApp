package grifalion.ru.apptest.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import grifalion.ru.apptest.model.NameModel
import grifalion.ru.apptest.repository.AgeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AgeViewModel(private val repository: AgeRepository): ViewModel() {
    var myCustomPost: MutableLiveData<Response<NameModel>> = MutableLiveData()

    fun getCustomPosts(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getCustomPosts(name)
            myCustomPost.postValue(response)
        }
    }
}