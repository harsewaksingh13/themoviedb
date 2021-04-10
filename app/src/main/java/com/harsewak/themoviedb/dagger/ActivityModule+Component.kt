package com.harsewak.themoviedb.dagger

import androidx.appcompat.app.AppCompatActivity
import com.harsewak.themoviedb.ApplicationComponent
import com.harsewak.themoviedb.navigation.MovieNavigation
import com.harsewak.themoviedb.navigation.MovieNavigator
import com.harsewak.themoviedb.view.MoviePresenter
import com.harsewak.themoviedb.view.MoviesPresenter
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

/**
 * Created by Harsewak Singh on 10/04/21.
 */
@Module
class ActivityModule(private val activity: AppCompatActivity) {
    @Provides
    fun activity(): AppCompatActivity = activity

    @Provides
    @ForActivity
    fun providesMovieNavigator(): MovieNavigator {
        return MovieNavigation(activity)
    }
}

@ForActivity
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    var moviesPresenter: MoviesPresenter

    var moviePresenter: MoviePresenter
}

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ForActivity