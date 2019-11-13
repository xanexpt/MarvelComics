package com.badjoras.marvel.models

import com.google.gson.annotations.SerializedName

class Results (
    @SerializedName("id") var id: Int,
    @SerializedName("digitalId") var digitalId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("modified") var modified: String,
    @SerializedName("format") var format: String,
    @SerializedName("thumbnail") var thumbnail: Thumbnail?=null,
    @SerializedName("images") var images: List<Images>
)/* {
    fun hasValidThumbnail():Boolean {
        return (thumbnail!=null && thumbnail!!.extension!=null
                && thumbnail!!.path!=null)
    }
}*/