package com.harsewak.themoviedb.view

import android.content.Context
import android.os.Bundle
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.BaseActivity
import com.harsewak.themoviedb.base.BasePresenter
import com.harsewak.themoviedb.base.View
import com.harsewak.themoviedb.base.load
import com.harsewak.themoviedb.dagger.ActivityComponent
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import com.harsewak.themoviedb.navigation.MovieNavigator
import com.harsewak.view.BaseRecycledAdapter
import com.harsewak.view.SmartRecyclerView
import javax.inject.Inject

class MoviesActivity : BaseActivity<MoviesPresenter>(), MoviesView {

    private lateinit var moviesRecyclerView: SmartRecyclerView<Movie, MovieCardView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        moviesRecyclerView = findViewById(R.id.recyclerView)
    }

    override fun presenter(component: ActivityComponent): MoviesPresenter {
        return component.moviesPresenter
    }

    override fun displayMovies(movies: List<Movie>) {
        moviesRecyclerView.setOnItemClickListener(object :
            BaseRecycledAdapter.OnItemClickListener<Movie> {
            override fun onItemClick(t: Movie) {
                presenter?.movieDetails(t)
            }
        })
        moviesRecyclerView.smartBinder(object :
            SmartRecyclerView.SmartViewBinder<Movie, MovieCardView> {
            override fun bind(view: MovieCardView, t: Movie) {
                view.bind(t)
            }

            override fun itemView(context: Context): MovieCardView {
                return MovieCardView(context)
            }
        }).grid(movies.toMutableList(), 2)
    }
}

interface MoviesView : View {
    fun displayMovies(movies: List<Movie>)
}

class MoviesPresenter @Inject constructor(
    private val movieInteractor: MovieInteractor,
    private val movieNavigator: MovieNavigator
) :
    BasePresenter<MoviesView>() {

    override fun onCreate(view: View) {
        super.onCreate(view)
        fetchMovies()
    }

    private fun fetchMovies() {
        movieInteractor.nowPlayingMovies(1, {
            view?.displayMovies(it)
        }, {
            onError(it)
        })
    }

    fun movieDetails(t: Movie) {
        movieNavigator.movieDetails(t)
    }
}

