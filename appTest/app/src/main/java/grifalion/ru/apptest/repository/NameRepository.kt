package grifalion.ru.apptest.repository

import androidx.lifecycle.LiveData
import grifalion.ru.apptest.db.dao.NameDao
import grifalion.ru.apptest.model.NameModel



class NameRepository(val nameDao: NameDao) {

    suspend fun insert(nameModel: NameModel) = nameDao.insert(nameModel)

    suspend fun delete(nameModel: NameModel) = nameDao.delete(nameModel)

    fun allNames(): List<NameModel> = nameDao.getAllNames()

    fun deleteNamesByIds(idList: List<Int>) = nameDao.deleteNamesByIds(idList)


}