package grifalion.ru.apptest.api


import grifalion.ru.apptest.model.NameModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NameApi {
    @GET("https://api.agify.io/")
    suspend fun getCustomPosts(
        @Query("name") name: String
    ): Response<NameModel>

}