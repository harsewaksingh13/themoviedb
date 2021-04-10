package com.harsewak.themoviedb.data

import android.os.Parcelable
import com.harsewak.themoviedb.api.*
import kotlinx.parcelize.Parcelize
import javax.inject.Inject


/**
 * Created by Harsewak Singh on 10/04/21.
 */
interface MovieInteractor {
    fun nowPlayingMovies(
        page: Int = 1,
        responseHandler: ResponseHandler<List<Movie>>,
        errorHandler: ErrorHandler
    ): RequestHandler
}


class MovieInteraction constructor(private val service: ServiceManager): MovieInteractor {

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
