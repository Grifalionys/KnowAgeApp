package grifalion.ru.apptest.api

import grifalion.ru.apptest.BASE_URL


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: NameApi by lazy {
        retrofit.create(NameApi::class.java)
    }
}