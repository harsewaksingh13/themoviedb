package com.harsewak.themoviedb.navigation

import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Harsewak Singh on 10/04/21.
 */
interface Navigator {
    var activity: AppCompatActivity
    fun finishActivity()
}

open class Navigation(override var activity: AppCompatActivity) : Navigator {

    override fun finishActivity() {
        activity.finish()
    }

}