package com.harsewak.themoviedb.api

import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieResponse
import com.harsewak.themoviedb.data.MoviesResponse
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Harsewak Singh on 10/04/21.
 */

typealias ResponseHandler<T> = (T) -> Unit

typealias ErrorHandler = (Throwable) -> Unit

class RequestHandler(private val job: Job) {
    fun cancel() {
        job.cancel()
    }
}

typealias RequestExecutor<T> = Call<T>

fun <T> RequestExecutor<T>.execute(responseHandler: ResponseHandler<T>, errorHandler: ErrorHandler): RequestHandler {

    val job = CoroutineScope(Dispatchers.IO).launch {
        runCatching {
            val response = execute()
            val body = response.body()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    body?.let(responseHandler) ?: errorHandler(Throwable("Null response returned"))
                } else {
                    errorHandler(Throwable("Error: ${response.code()}"))
                }
            }
        }.onFailure {
            withContext(Dispatchers.Main) {
                errorHandler(it)
            }
        }
    }
    return RequestHandler(job)
}


interface MovieService {
    @GET("movie/now_playing")
    fun nowPlaying(@Query("page") page: Int, @Query("limit") limit: Int): RequestExecutor<MoviesResponse>

    @GET("movie/{id}")
    fun movie(@Path("id") id: String): RequestExecutor<MovieResponse>
}

interface ServiceManager {
    val movie: MovieService
}

class ServiceManagerImpl(private val retrofit: Retrofit) : ServiceManager {


    override val movie: MovieService
        get() = retrofit.create(MovieService::class.java)

}