package com.harsewak.themoviedb.view

import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import com.harsewak.themoviedb.navigation.MovieNavigator
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase
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

    @Before
    public override fun setUp() {
        super.setUp()
        moviesPresenter = MoviesPresenterImpl(movieInteractor, movieNavigator)
    }

    public override fun tearDown() {}

    @Test
    fun testOnCreate() {
        moviesPresenter.onCreate(view)
        verify(view)
            .onPageDataChanged(movieInteractor.pageLimit, loading = true, lastPage = false)
    }

    @Test
    fun testFetchMovies() {
        moviesPresenter.fetchMovies(1)
        assertEquals(movieInteractor.nowPlayingMovies(1, {}, {}), any())
    }

    @Test
    fun testMovieDetails() {
        val movie = Movie(1, "Movie", "Movie overview", "", "")
        moviesPresenter.movieDetails(movie)
        verify(movieNavigator).movieDetails(movie)
    }
}