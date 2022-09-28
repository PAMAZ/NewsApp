package it.paolomazza.newsapp.data.mapper

import it.paolomazza.newsapp.data.dto.NewsDetailDTO
import it.paolomazza.newsapp.data.entity.NewsDetailModel
import it.paolomazza.newsapp.utils.TimeUtils.timestampToDate

object NewsDetailConverter {

    fun NewsDetailDTO.toNewsDetailModel(): NewsDetailModel = NewsDetailModel(this.message.id,
                                                                             this.message.title,
                                                                             this.message.subtitle,
                                                                             this.message.body,
                                                                             this.message.timestamp?.timestampToDate())

}