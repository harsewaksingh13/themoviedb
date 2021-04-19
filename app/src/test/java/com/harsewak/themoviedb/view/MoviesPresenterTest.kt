package com.harsewak.themoviedb.view

import com.harsewak.themoviedb.api.RequestHandler
import com.harsewak.themoviedb.api.ResponseHandler
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import com.harsewak.themoviedb.navigation.MovieNavigator
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import junit.framework.TestCase
import kotlinx.coroutines.Job
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito


/**
 * Created by Harsewak Singh on 10/04/21.
 */

class MoviesPresenterTest : TestCase() {


    private var movieInteractor: MovieInteractor = Mockito.mock(MovieInteractor::class.java)
    private var movieNavigator: MovieNavigator = Mockito.mock(MovieNavigator::class.java)
    private var view: MoviesView = Mockito.mock(MoviesView::class.java)
    private lateinit var moviesPresenter: MoviesPresenter
    private val pageCaptor = argumentCaptor<Int>()
    private val responseCaptor = argumentCaptor<ResponseHandler<List<Movie>>>()
    private val errorCaptor = argumentCaptor<ResponseHandler<Throwable>>()
    private val movies = arrayListOf<Movie>()

    @Before
    public override fun setUp() {
        super.setUp()
        moviesPresenter = MoviesPresenterImpl(movieInteractor, movieNavigator)

        whenever(movieInteractor.nowPlayingMovies(pageCaptor.capture(), responseCaptor.capture(),errorCaptor.capture())).thenAnswer {
            responseCaptor.firstValue(movies)
            RequestHandler(Job())
        }
        moviesPresenter.onCreate(view)
    }

    public override fun tearDown() {}

    @Test
    fun testOnCreate() {
        verify(view)
            .onPageDataChanged(movieInteractor.pageLimit, loading = true, lastPage = false)
    }

    @Test
    fun testFetchMovies() {
        verify(movieInteractor).nowPlayingMovies(pageCaptor.capture(), responseCaptor.capture(),errorCaptor.capture())
        verify(view).displayMovies(movies)
        verify(view)
            .onPageDataChanged(movieInteractor.pageLimit, loading = false, lastPage = true)
    }

    @Test
    fun testMovieDetails() {
        val movie = Movie(1, "Movie", "Movie overview", "", "")
        moviesPresenter.movieDetails(movie)
        verify(movieNavigator).movieDetails(movie)
    }
}