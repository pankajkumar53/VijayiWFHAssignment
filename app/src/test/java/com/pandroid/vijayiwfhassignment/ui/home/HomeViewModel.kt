package com.pandroid.vijayiwfhassignment.ui.home

import app.cash.turbine.test
import com.pandroid.vijayiwfhassignment.core.State
import com.pandroid.vijayiwfhassignment.data.model.MediaDetails
import com.pandroid.vijayiwfhassignment.data.model.Title
import com.pandroid.vijayiwfhassignment.data.repo.MediaRepository
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class HomeViewModelTest {

    private lateinit var repository: MediaRepository

    @Before
    fun setup() {
        repository = mock(MediaRepository::class.java)

        // Force RxJava to run synchronously
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    @Test
    fun `loadData emits Success with mock data`() = runTest {
        // Arrange
        val movies = listOf(Title(1, "tt001", "Movie A", 1, "movie", "movie", 2020))
        val shows = listOf(Title(2, "tt002", "Show B", 2, "tv", "tv_series", 2021))
        val mockPair = Pair(movies, shows)

        `when`(repository.fetchMoviesAndShows()).thenReturn(Single.just(mockPair))

        // Act
        val viewModel = HomeViewModel(repository)

        // Assert - Trampoline scheduler executes synchronously, so state is already Success
        viewModel.homeState.test {
            val state = awaitItem()
            assertTrue("Expected Success but got $state", state is State.Success<*>)

            val data = (state as State.Success).data
            assertEquals(mockPair, data)

            cancelAndIgnoreRemainingEvents()
        }

        verify(repository, times(1)).fetchMoviesAndShows()
    }

    @Test
    fun `loadData emits Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "Network error"
        val exception = RuntimeException(errorMessage)
        `when`(repository.fetchMoviesAndShows()).thenReturn(Single.error(exception))

        // Act
        val viewModel = HomeViewModel(repository)

        // Assert - Trampoline scheduler executes synchronously, so state is already Error
        viewModel.homeState.test {
            val state = awaitItem()
            assertTrue("Expected Error but got $state", state is State.Error)
            assertEquals(errorMessage, (state as State.Error).message)

            cancelAndIgnoreRemainingEvents()
        }

        verify(repository, times(1)).fetchMoviesAndShows()
    }

    @Test
    fun `getMediaDetails emits Success with mock details`() = runTest {
        // Arrange
        val mockDetail = MediaDetails(
            backdrop = "",
            critic_score = 80,
            end_year = 2027,
            genre_names = listOf("Action"),
            genres = listOf(1),
            id = 1,
            imdb_id = "tt001",
            network_names = "",
            networks = "",
            original_language = "en",
            original_title = "Original",
            plot_overview = "Nice Movie",
            popularity_percentile = 0.9,
            poster = "",
            posterLarge = "",
            posterMedium = "",
            release_date = "2020-01-01",
            relevance_percentile = 0.9,
            runtime_minutes = 120,
            similar_titles = emptyList(),
            title = "Movie",
            tmdb_id = 1,
            tmdb_type = "movie",
            trailer = "",
            trailer_thumbnail = "",
            type = "movie",
            us_rating = "PG",
            user_rating = 8.5,
            year = 2020
        )

        `when`(repository.fetchMoviesAndShows()).thenReturn(Single.just(Pair(emptyList(), emptyList())))
        `when`(repository.getMediaDetails(1)).thenReturn(Single.just(mockDetail))

        // Act
        val viewModel = HomeViewModel(repository)
        viewModel.getMediaDetails(1)

        // Assert - Trampoline scheduler executes synchronously, so state is already Success
        viewModel.detailState.test {
            val state = awaitItem()
            assertTrue("Expected Success but got $state", state is State.Success<*>)

            val data = (state as State.Success).data
            assertEquals(mockDetail, data)

            cancelAndIgnoreRemainingEvents()
        }

        verify(repository, times(1)).getMediaDetails(1)
    }

    @Test
    fun `getMediaDetails emits Error when repository fails`() = runTest {
        // Arrange
        val errorMessage = "Failed to fetch details"
        val exception = RuntimeException(errorMessage)

        `when`(repository.fetchMoviesAndShows()).thenReturn(Single.just(Pair(emptyList(), emptyList())))
        `when`(repository.getMediaDetails(1)).thenReturn(Single.error(exception))

        // Act
        val viewModel = HomeViewModel(repository)
        viewModel.getMediaDetails(1)

        // Assert - Trampoline scheduler executes synchronously, so state is already Error
        viewModel.detailState.test {
            val state = awaitItem()
            assertTrue("Expected Error but got $state", state is State.Error)
            assertEquals(errorMessage, (state as State.Error).message)

            cancelAndIgnoreRemainingEvents()
        }

        verify(repository, times(1)).getMediaDetails(1)
    }
}