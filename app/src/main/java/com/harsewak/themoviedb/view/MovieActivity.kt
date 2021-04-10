package com.harsewak.themoviedb.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.BaseActivity
import com.harsewak.themoviedb.base.BasePresenter
import com.harsewak.themoviedb.base.View
import com.harsewak.themoviedb.dagger.ActivityComponent
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.data.MovieInteractor
import javax.inject.Inject

class MovieActivity : BaseActivity<MoviePresenter>(), MovieView {

    lateinit var movieCard: MovieCardView
    lateinit var overviewText: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        movieCard = findViewById(R.id.movieCard)
        overviewText = findViewById(R.id.overviewText)
        presenter?.movie = intent.getParcelableExtra(Movie::class.java.simpleName)
    }

    override fun presenter(component: ActivityComponent): MoviePresenter {
        return component.moviePresenter
    }

    override fun displayMovie(movie: Movie) {
        movieCard.bind(movie)
        overviewText.text = movie.overview
    }
}

interface MovieView: View {
    fun displayMovie(movie: Movie)
}

class MoviePresenter @Inject constructor(val movieInteractor: MovieInteractor) : BasePresenter<MovieView>() {

    var movie: Movie? = null


    override fun onResume() {
        super.onResume()
        movie?.let {
            view?.displayMovie(it)
        }
    }
}