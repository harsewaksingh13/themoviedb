package com.harsewak.themoviedb

import android.app.Application
import android.content.Context

/**
 * Created by Harsewak Singh on 10/04/21.
 */
class MovieApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }
}

