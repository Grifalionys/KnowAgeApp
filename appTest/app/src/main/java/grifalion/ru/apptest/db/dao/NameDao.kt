package grifalion.ru.apptest.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import grifalion.ru.apptest.model.NameModel

@Dao
interface NameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nameModel: NameModel)

    @Delete
    suspend fun delete(nameModel: NameModel)

    @Query("SELECT * FROM names")
    fun getAllNames(): List<NameModel>

    @Query("delete from names where id in (:idList)")
    fun deleteNamesByIds(idList: List<Int>)

}