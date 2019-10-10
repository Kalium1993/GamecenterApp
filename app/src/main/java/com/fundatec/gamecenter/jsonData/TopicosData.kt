package com.fundatec.gamecenter.jsonData
import com.google.gson.annotations.SerializedName


data class TopicosData(
    @SerializedName("titulo")
    val titulo: String
) {
    @SerializedName("comunidade")
    var comunidade: String? = null
    @SerializedName("_id")
    var id: String? = null
    constructor() : this("")
}