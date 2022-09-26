package it.paolomazza.newsapp.data.dto

import com.google.gson.annotations.SerializedName

data class GetNewsListDTO(
        @SerializedName("message")
        val message: NewsListDTO?
) {

    data class NewsListDTO(
            @SerializedName("results")
            val result: List<NewsDTO>?
    )

    data class NewsDTO(
            @SerializedName("id")
            val id: Int,
            @SerializedName("title")
            val title: String?,
            @SerializedName("subtitle")
            val subtitle: String?,
            @SerializedName("timestamp")
            val timestamp: Long?
    )
}
