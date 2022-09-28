package it.paolomazza.newsapp.data.dto

import com.google.gson.annotations.SerializedName

data class NewsDetailDTO(
        @SerializedName("message")
        val message: Message
) {
    data class Message(@SerializedName("id")
                       val id: Int,
                       @SerializedName("title")
                       val title: String?,
                       @SerializedName("subtitle")
                       val subtitle: String?,
                       @SerializedName("body")
                       val body: String?,
                       @SerializedName("timestamp")
                       val timestamp: Long?)
}