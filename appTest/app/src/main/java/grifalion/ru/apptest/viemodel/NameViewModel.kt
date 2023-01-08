package grifalion.ru.apptest.viemodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import grifalion.ru.apptest.db.NameDataBase
import grifalion.ru.apptest.model.NameModel
import grifalion.ru.apptest.repository.NameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NameViewModel(application: Application): AndroidViewModel(application){
    lateinit var REPOSITORY: NameRepository
    private val context = application
    private val namesData = MutableLiveData<List<NameModel>>()
    internal val getNames: LiveData<List<NameModel>> = namesData

    fun initDatabase(){
        val nameDao = NameDataBase.getDataBase(context).getNameDao()
        REPOSITORY = NameRepository(nameDao)

    }
    fun getAllNames(resetToDefault: Boolean = false){
        viewModelScope.launch(Dispatchers.IO) {
            val names = REPOSITORY
                .allNames()
                .let {
                    if(resetToDefault){
                        it.map { model ->
                            model.copy(isCheckBoxVisible = false, isCheckedBox = false)
                        }
                    }else{
                        it
                    }
                }
            namesData.postValue(names)
        }
    }

    private fun deleteNamesByIds(idList: List<Int>){
        REPOSITORY.deleteNamesByIds(idList)
    }
    fun deleteNamesByList(names: List<NameModel>){
        viewModelScope.launch(Dispatchers.IO) {
            val ids = names
                .filter { it.isCheckedBox }
                .map { it.id }
            deleteNamesByIds(ids)
        }

    }


    fun insert(nameModel: NameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.insert(nameModel)
        }
    }
        fun delete(nameModel: NameModel) {
            viewModelScope.launch(Dispatchers.IO) {
                REPOSITORY.delete(nameModel)
            }
        }

    }
