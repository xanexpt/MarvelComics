package com.badjoras.marvel.api

import com.badjoras.marvel.models.ComicsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET(COMICS_ENDPOINT)
    fun getComics(@Query("offset") offset: Int, @Query("orderBy") orderBy: String): Observable<ComicsResponse>

    @GET(COMIC_DETAILS_ENDPOINT)
    fun getComicsDetails(
        @Path(
            value = "comic_id",
            encoded = true
        ) comicId: String, @Query("offset") offset: Int, @Query("orderBy") orderBy: String
    ): Observable<ComicsResponse>

    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        const val COMICS_ENDPOINT = "/v1/public/comics"
        const val COMIC_DETAILS_ENDPOINT = "/v1/public/comics/{comic_id}"
    }
}