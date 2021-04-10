package com.harsewak.themoviedb.data

import com.google.gson.annotations.SerializedName

/**
 * Created by Harsewak Singh on 10/04/21.
 */
data class CollectionResponse (
    val id: Long,
    val name: String? = null,
    val overview: String? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val parts: List<Part>? = null
)

data class Part (
    val genreIDS: List<Long>? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null,
    val overview: String? = null,
    val id: Long,
    val title: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val popularity: Double? = null
)
