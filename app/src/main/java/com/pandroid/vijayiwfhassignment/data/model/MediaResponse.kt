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
