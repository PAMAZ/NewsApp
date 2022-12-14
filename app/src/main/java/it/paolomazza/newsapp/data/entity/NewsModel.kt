package it.paolomazza.newsapp.data.entity

import java.io.Serializable
import java.util.*

data class NewsModel(val id: Int,
                     val title: String?,
                     val subtitle: String?,
                     val timestamp: String?):Serializable