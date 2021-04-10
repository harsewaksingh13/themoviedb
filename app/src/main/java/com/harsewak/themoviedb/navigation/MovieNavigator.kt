package com.harsewak.themoviedb.navigation

import androidx.appcompat.app.AppCompatActivity
import com.harsewak.themoviedb.base.startActivity
import com.harsewak.themoviedb.data.Movie
import com.harsewak.themoviedb.view.MovieActivity

/**
 * Created by Harsewak Singh on 10/04/21.
 */
interface MovieNavigator : Navigator {

    fun movieDetails(movie: Movie)
}

class MovieNavigation(override var activity: AppCompatActivity) : Navigation(activity),
    MovieNavigator {

    override fun movieDetails(movie: Movie) {
        activity.startActivity(MovieActivity::class.java, movie)
    }

}