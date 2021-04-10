package com.harsewak.themoviedb.data

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


/**
 * Created by Harsewak Singh on 10/04/21.
 */

data class MovieResponse (
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection? = null,
    val budget: Long? = null,
    val genres: List<Genre>? = null,
    val homepage: String? = null,
    val id: Long? = null,
    val imdbID: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val productionCompanies: List<ProductionCompany>? = null,
    val productionCountries: List<ProductionCountry>? = null,
    val releaseDate: String? = null,
    val revenue: Long? = null,
    val runtime: Long? = null,
    val spokenLanguages: List<SpokenLanguage>? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Long? = null
)

data class BelongsToCollection (
    val id: Long? = null,
    val name: String? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null
)

data class Genre (
    val id: Long? = null,
    val name: String? = null
)

data class ProductionCompany (
    val id: Long? = null,
    val logoPath: String? = null,
    val name: String? = null,
    val originCountry: String? = null
)

data class ProductionCountry (
    val iso3166_1: String? = null,
    val name: String? = null
)

data class SpokenLanguage (
    val englishName: String? = null,
    val iso639_1: String? = null,
    val name: String? = null
)

