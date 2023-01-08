package grifalion.ru.apptest.repository

import grifalion.ru.apptest.api.RetrofitInstance
import grifalion.ru.apptest.model.NameModel
import retrofit2.Response

class AgeRepository {
    suspend fun getCustomPosts(name: String): Response<NameModel> {
        return RetrofitInstance.api.getCustomPosts(name)
    }
}