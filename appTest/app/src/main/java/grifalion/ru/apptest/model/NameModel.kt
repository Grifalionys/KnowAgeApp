package grifalion.ru.apptest.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "names")
data class NameModel(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String = "",

    var age: Int? = null,

    val isCheckBoxVisible: Boolean,

    val isCheckedBox: Boolean

)
