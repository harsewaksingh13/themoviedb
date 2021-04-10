package com.harsewak.themoviedb.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.widget.AppCompatTextView
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.BaseActivity
import com.harsewak.themoviedb.base.BasePresenter
import com.harsewak.themoviedb.base.View
import com.harsewak.themoviedb.dagger.ActivityComponent
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import com.harsewak.view.BaseRecycledAdapter
import com.harsewak.view.SmartRecyclerView
import javax.inject.Inject

class MovieActivity : BaseActivity<MoviePresenter>(), MovieView {

    lateinit var movieCard: MovieCardView
    lateinit var overviewText: AppCompatTextView
    private lateinit var moviesRecyclerView: SmartRecyclerView<Movie, MovieCardView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movieCard = findViewById(R.id.movieCard)
        overviewText = findViewById(R.id.overviewText)
        moviesRecyclerView = findViewById(R.id.recyclerView)
        presenter?.movie = intent.getParcelableExtra(Movie::class.java.simpleName)
    }

    override fun presenter(component: ActivityComponent): MoviePresenter {
        return component.moviePresenter
    }

    override fun displayMovie(movie: Movie) {
        movieCard.bind(movie)
        overviewText.text = movie.overview
    }

    override fun displayMovies(movies: List<Movie>) {
        moviesRecyclerView.setOnItemClickListener(object :
            BaseRecycledAdapter.OnItemClickListener<Movie> {
            override fun onItemClick(t: Movie) {

            }
        })
        moviesRecyclerView.smartBinder(object :
            SmartRecyclerView.SmartViewBinder<Movie, MovieCardView> {
            override fun bind(view: MovieCardView, t: Movie) {
                view.bind(t)
            }

            override fun itemView(context: Context): MovieCardView {
                return MovieCardView(context).setupForHorizontal()
            }
        }).horizontally(movies.toMutableList())
    }
}

interface MovieView : View {
    fun displayMovie(movie: Movie)
    fun displayMovies(movies: List<Movie>)
}

class MoviePresenter @Inject constructor(private val movieInteractor: MovieInteractor) :
    BasePresenter<MovieView>() {

    var movie: Movie? = null

    override fun onResume() {
        super.onResume()
        movie?.let {
            view?.displayMovie(it)
            fetchMovie(it.id)
        }
    }

    private fun fetchMovie(id: Long) {
        movieInteractor.movieDetails(id,
            { detail ->
                detail.collectionId?.let {
                    fetchCollection(it)
                }
            }, { onError(it) })
    }

    private fun fetchCollection(id: Long) {
        movieInteractor.collectionMovies(id,
            {
               view?.displayMovies(it.movies)
            }, { onError(it) })
    }
}