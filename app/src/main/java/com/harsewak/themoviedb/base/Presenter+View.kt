package com.harsewak.themoviedb.base

/**
 * Created by Harsewak Singh on 10/04/21.
 */
interface Presenter {

    fun onCreate(view: View)
    fun onResume()
    fun onPause()

}

interface View {
    fun onError(error: String)
    fun <T> startActivity(clazz: Class<T>)
    fun showProgress()
    fun dismissProgress()
}