package it.paolomazza.newsapp.data.entity

import java.io.Serializable

data class NewsDetailModel(val id: Int,
                           val title: String?,
                           val subtitle: String?,
                           val body: String?,
                           val timestamp: String?):Serializable