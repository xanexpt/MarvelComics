package com.badjoras.marvel.models

import com.google.gson.annotations.SerializedName

class Thumbnail(
    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null
)