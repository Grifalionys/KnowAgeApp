package grifalion.ru.apptest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import grifalion.ru.apptest.db.dao.NameDao
import grifalion.ru.apptest.model.NameModel


@Database(entities = [NameModel::class], version = 13)
abstract class NameDataBase: RoomDatabase() {
    abstract fun getNameDao(): NameDao

    companion object {

        private var dataBase: NameDataBase? = null

        @Synchronized
        fun getDataBase(context: Context): NameDataBase {

                return if (dataBase == null) {
                    dataBase = Room.databaseBuilder(
                        context.applicationContext,
                        NameDataBase::class.java,
                        "datanames"
                    ).build()
                    dataBase as NameDataBase
                } else {
                dataBase as NameDataBase
            }
        }
    }
}