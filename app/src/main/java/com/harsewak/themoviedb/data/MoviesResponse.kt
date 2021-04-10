package com.harsewak.themoviedb.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Harsewak Singh on 10/04/21.
 */
data class MoviesResponse(
    val dates: Dates,
    val page: Long,
    val results: List<Result>,
    val totalPages: Long,
    val totalResults: Long
)

data class Dates(
    val maximum: String,
    val minimum: String
)

data class Result(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    val genreIDS: List<Long>,
    val id: Long,
    @SerializedName("original_title") val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Long
)
