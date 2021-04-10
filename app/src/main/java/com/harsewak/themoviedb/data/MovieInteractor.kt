package com.harsewak.themoviedb.data

import android.os.Parcelable
import com.harsewak.themoviedb.api.*
import kotlinx.parcelize.Parcelize


/**
 * Created by Harsewak Singh on 10/04/21.
 */
interface MovieInteractor {
    fun nowPlayingMovies(
        page: Int = 1,
        responseHandler: ResponseHandler<List<Movie>>,
        errorHandler: ErrorHandler
    ): RequestHandler

    fun movieDetails(
        id: Long, responseHandler: ResponseHandler<MovieDetail>,
        errorHandler: ErrorHandler
    ): RequestHandler

    fun collectionMovies(
        id: Long, responseHandler: ResponseHandler<MovieCollection>,
        errorHandler: ErrorHandler
    ): RequestHandler
}


class MovieInteraction constructor(private val service: ServiceManager) : MovieInteractor {

    private val assetsRootURL = "https://www.themoviedb.org/t/p/w600_and_h600_bestv2"

    override fun nowPlayingMovies(
        page: Int,
        responseHandler: ResponseHandler<List<Movie>>,
        errorHandler: ErrorHandler
    ): RequestHandler {
        return service.movie.nowPlaying(page, 10).execute({ response ->
            responseHandler(response.results.map {
                Movie(
                    it.id,
                    it.title,
                    it.overview,
                    "${assetsRootURL}${it.posterPath}",
                    it.releaseDate
                )
            })
        }, errorHandler)
    }

    override fun movieDetails(
        id: Long,
        responseHandler: ResponseHandler<MovieDetail>,
        errorHandler: ErrorHandler
    ): RequestHandler {
        return service.movie.movieDetails(id).execute({
            responseHandler(MovieDetail(it.homepage ?: "", it.belongsToCollection?.id))
        }, errorHandler)
    }

    override fun collectionMovies(
        id: Long,
        responseHandler: ResponseHandler<MovieCollection>,
        errorHandler: ErrorHandler
    ): RequestHandler {
        return service.movie.collection(id).execute({ response ->
            val movies = response.parts?.map {
                Movie(
                    it.id,
                    it.title ?: "",
                    it.overview ?: "",
                    "${assetsRootURL}${it.posterPath}",
                    it.releaseDate ?: ""
                )
            } ?: mutableListOf()
            responseHandler(MovieCollection(response.name ?: "", response.id, movies))
        }, errorHandler)
    }
}


@Parcelize
data class Movie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val releaseDate: String
) :
    Parcelable

data class MovieDetail(val homepage: String, val collectionId: Long?)

data class MovieCollection(val name: String, val id: Long, val movies: List<Movie>)