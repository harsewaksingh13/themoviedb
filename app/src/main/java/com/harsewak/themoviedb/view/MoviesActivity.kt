package com.harsewak.themoviedb.view

import android.content.Context
import android.os.Bundle
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.BaseActivity
import com.harsewak.themoviedb.base.BasePresenter
import com.harsewak.themoviedb.base.View
import com.harsewak.themoviedb.dagger.ActivityComponent
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import com.harsewak.themoviedb.navigation.MovieNavigator
import com.harsewak.view.BaseRecycledAdapter
import com.harsewak.view.BasicRecyclerView
import com.harsewak.view.SmartRecyclerView
import javax.inject.Inject

class MoviesActivity : BaseActivity<MoviesPresenter>(), MoviesView {

    private lateinit var moviesRecyclerView: SmartRecyclerView<Movie, MovieCardView>

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_movies)
        moviesRecyclerView = findViewById(R.id.recyclerView)
        moviesRecyclerView.setOnItemClickListener(object :
            BaseRecycledAdapter.OnItemClickListener<Movie> {
            override fun onItemClick(t: Movie) {
                presenter?.movieDetails(t)
            }
        })
        moviesRecyclerView.setPageListener(object: BasicRecyclerView.OnPageChangeListener{
            override fun onPageChanged(page: Int) {
                presenter?.fetchMovies(page)
            }
        })
        val movies: MutableList<Movie> = mutableListOf()
        moviesRecyclerView.smartBinder(object :
            SmartRecyclerView.SmartViewBinder<Movie, MovieCardView> {
            override fun bind(view: MovieCardView, t: Movie) {
                view.bind(t)
            }

            override fun itemView(context: Context): MovieCardView {
                return MovieCardView(context)
            }
        }).grid(movies, 2)
        super.onCreate(savedInstanceState)
    }

    override fun presenter(component: ActivityComponent): MoviesPresenter {
        return component.moviesPresenter
    }

    override fun displayMovies(movies: List<Movie>) {
        moviesRecyclerView.items.addAll(movies)
        moviesRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onPageDataChanged(pageLimit: Int, loading: Boolean, lastPage: Boolean) {
        moviesRecyclerView.onPageDataChanged(pageLimit, loading, lastPage)
    }
}

interface MoviesView : View {
    fun displayMovies(movies: List<Movie>)
    fun onPageDataChanged(pageLimit: Int, loading: Boolean, lastPage: Boolean)
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

    fun fetchMovies(page: Int) {
        notify(isLoading = true, isLastPage = false)
        movieInteractor.nowPlayingMovies(page, {
            view?.displayMovies(it)
            notify(isLoading = false, isLastPage = it.isEmpty())
        }, {
            onError(it)
        })
    }

    private fun notify(isLoading: Boolean, isLastPage: Boolean) {
        view?.onPageDataChanged(movieInteractor.pageLimit, isLoading, isLastPage)
    }

    private fun fetchMovies() {
        fetchMovies(1)
    }

    fun movieDetails(t: Movie) {
        movieNavigator.movieDetails(t)
    }
}

