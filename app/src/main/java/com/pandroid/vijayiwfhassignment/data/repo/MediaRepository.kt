package com.pandroid.vijayiwfhassignment.data.repo

import com.pandroid.vijayiwfhassignment.data.api.ApiService
import io.reactivex.rxjava3.core.Single
import com.pandroid.vijayiwfhassignment.BuildConfig

class MediaRepository(private val apiService: ApiService) {

    fun fetchMoviesAndShows() =
        Single.zip(
            apiService.getMovies(BuildConfig.API_KEY),
            apiService.getTvShows(BuildConfig.API_KEY)
        ) { movieResponse, tvResponse ->
            Pair(movieResponse.titles, tvResponse.titles)
        }
}
