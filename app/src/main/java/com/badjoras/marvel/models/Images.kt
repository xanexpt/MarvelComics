package com.badjoras.marvel.models

import com.google.gson.annotations.SerializedName

class Images(
    @SerializedName("path") var path: String,
    @SerializedName("extension") var extension: String
)