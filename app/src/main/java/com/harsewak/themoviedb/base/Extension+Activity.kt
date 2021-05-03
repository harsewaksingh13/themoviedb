package com.harsewak.themoviedb.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.harsewak.themoviedb.MovieApplication

fun getApplication(context: Context): MovieApplication {
    return context.applicationContext as MovieApplication
}

/**
 * Created by Harsewak Singh on 10/04/21.
 */
fun Activity.application(): MovieApplication = getApplication(applicationContext)

fun Activity.startActivity(classToGo: Class<*>, parcelable: Parcelable? = null) {
    startActivity(classToGo, parcelable?.javaClass?.simpleName, parcelable)
}

fun Activity.startActivity(classToGo: Class<*>?, key: String?, parcelable: Parcelable?) {
    val intent = Intent(this, classToGo)
    key?.let {
        intent.putExtra(it, parcelable)
    }
    startActivity(intent)
}
