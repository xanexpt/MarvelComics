package com.badjoras.marvel.models

import com.google.gson.annotations.SerializedName

class ComicsResponse(
    @SerializedName("code") var code: Int,
    @SerializedName("status") var status: String,
    @SerializedName("data") var data: Data
)