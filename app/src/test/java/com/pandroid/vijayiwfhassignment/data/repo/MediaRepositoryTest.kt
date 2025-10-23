package com.pandroid.vijayiwfhassignment.data.repo

import com.pandroid.vijayiwfhassignment.data.api.ApiService
import com.pandroid.vijayiwfhassignment.data.model.MediaResponse
import com.pandroid.vijayiwfhassignment.data.model.Title
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class MediaRepositoryTest {

    private lateinit var apiService: ApiService
    private lateinit var repository: MediaRepository

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        repository = MediaRepository(apiService)
    }

    @Test
    fun `fetchMoviesAndShows should return combined data`() {
        val movies = listOf(Title(1, "tt001", "Movie1", 1, "movie", "movie", 2020))
        val shows = listOf(Title(2, "tt002", "Show1", 2, "tv", "tv_series", 2021))

        val movieResponse = MediaResponse(1, movies, 1, 1)
        val tvResponse = MediaResponse(1, shows, 1, 1)

        `when`(apiService.getMovies(anyString(), anyString())).thenReturn(Single.just(movieResponse))
        `when`(apiService.getTvShows(anyString(), anyString())).thenReturn(Single.just(tvResponse))

        repository.fetchMoviesAndShows()
            .test()
            .assertNoErrors()
            .assertValue { it.first == movies && it.second == shows }
    }

    @Test
    fun `getMediaDetails should call apiService`() {
        `when`(apiService.getMediaDetails(anyInt(), anyString()))
            .thenReturn(Single.never())

        repository.getMediaDetails(123)
        verify(apiService).getMediaDetails(eq(123), anyString())
    }
}
