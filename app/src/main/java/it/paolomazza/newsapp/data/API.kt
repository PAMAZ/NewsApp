package it.paolomazza.newsapp.data

import it.paolomazza.newsapp.data.dto.GetNewsListDTO
import it.paolomazza.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface API {

    @GET("news")
    @Headers("x-api-key: ${Constants.API_KEY}")
    suspend fun getNews(
            @Query("offset") offset: Int,
            @Query("limit") limit: Int = Constants.DEFAULT_NEWS_LIMIT
    ): GetNewsListDTO

}