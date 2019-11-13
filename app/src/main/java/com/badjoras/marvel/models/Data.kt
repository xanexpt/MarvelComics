package com.badjoras.marvel.models

import com.google.gson.annotations.SerializedName

class Data(
    @SerializedName("code") var code: Int,
    @SerializedName("total") var total: Int,
    @SerializedName("count") var count: Int,
    @SerializedName("offset") var offset: Int,
    @SerializedName("results") var results: List<Results>
)