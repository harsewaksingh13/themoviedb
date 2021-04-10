package com.harsewak.themoviedb.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harsewak.themoviedb.ApplicationComponent
import com.harsewak.themoviedb.dagger.ActivityComponent
import com.harsewak.themoviedb.dagger.ActivityModule
import com.harsewak.themoviedb.dagger.DaggerActivityComponent

/**
 * Created by Harsewak Singh on 10/04/21.
 */
@SuppressLint("Registered")
abstract class BaseActivity<P : Presenter>: AppCompatActivity(), View {

    var presenter: P? = null
    private lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .applicationComponent(applicationComponent())
            .build()
        presenter = presenter(component)
        presenter?.onCreate(this)
    }

    abstract fun presenter(component: ActivityComponent): P

    private fun applicationComponent(): ApplicationComponent {
        return application().applicationComponent
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter?.onPause()
    }

    override fun onError(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }

    override fun <T> startActivity(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    override fun showProgress() {

    }

    override fun dismissProgress() {

    }
}


open class BasePresenter<V : View> : Presenter {


    var view: V? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(view: View) {
        this.view = view as? V
    }

    override fun onResume() {

    }

    override fun onPause() {

    }


    open fun onError(throwable: Throwable) {
        onError(Error(throwable))
    }

    open fun onError(error: Error) {
        view?.onError(error.localizedMessage ?: "")
    }

    open fun onErrorText(error: String) {
        onError(Error(error))
    }


}
