package com.pandroid.vijayiwfhassignment.data.api

import com.pandroid.vijayiwfhassignment.data.model.MediaResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list-titles")
    fun getMovies(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "movie"
    ): Single<MediaResponse>

    @GET("list-titles")
    fun getTvShows(
        @Query("apiKey") apiKey: String,
        @Query("types") types: String = "tv_series"
    ): Single<MediaResponse>
}
