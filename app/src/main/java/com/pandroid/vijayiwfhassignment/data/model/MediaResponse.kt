package com.pandroid.vijayiwfhassignment.data.model

data class MediaResponse(
    val page: Int,
    val titles: List<Title>,
    val total_pages: Int,
    val total_results: Int
)

data class Title(
    val id: Int,
    val imdb_id: String,
    val title: String,
    val tmdb_id: Int,
    val tmdb_type: String,
    val type: String,
    val year: Int
)

data class MediaDetails(
    val backdrop: Any,
    val critic_score: Int,
    val end_year: Any,
    val genre_names: List<String>,
    val genres: List<Int>,
    val id: Int,
    val imdb_id: String,
    val network_names: Any,
    val networks: Any,
    val original_language: String,
    val original_title: String,
    val plot_overview: String,
    val popularity_percentile: Double,
    val poster: String,
    val posterLarge: String,
    val posterMedium: String,
    val release_date: String,
    val relevance_percentile: Double,
    val runtime_minutes: Int,
    val similar_titles: List<Int>,
    val title: String,
    val tmdb_id: Int,
    val tmdb_type: String,
    val trailer: String,
    val trailer_thumbnail: String,
    val type: String,
    val us_rating: String,
    val user_rating: Double,
    val year: Int
)