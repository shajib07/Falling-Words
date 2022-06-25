package com.atahar.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WordJson(
    @SerializedName("text_eng") @Expose var text_eng: String,
    @SerializedName("text_spa") @Expose var text_spa: String
)
