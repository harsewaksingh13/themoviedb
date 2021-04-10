package com.harsewak.themoviedb.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.harsewak.themoviedb.MovieApplication
import com.harsewak.themoviedb.R

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

fun Context.inflate(@LayoutRes layoutId: Int, root: ViewGroup? = null): View {
    return LayoutInflater.from(this).inflate(R.layout.view_movie_card, root)
}

fun ImageView.load(url: String?, @DrawableRes placeholder: Int? = null) {

    url?.let {
        Glide.with(context).load(it).into(this)
    } ?: run {
        placeholder?.let {
            setImageResource(placeholder)
        }
    }
}

fun View.setWidthMatchParent() {
    if (layoutParams is FrameLayout) {
        val layoutParams = layoutParams as FrameLayout.LayoutParams
        layoutParams.width = FrameLayout.LayoutParams.MATCH_PARENT
        setLayoutParams(layoutParams)
    }
}

fun View.setWidthLayoutParams() {
    val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT)
    setLayoutParams(layoutParams)
}


fun View.setMargin(id: Int) {
    val margin = context.resources.getDimensionPixelSize(id)
    setMargin(margin, margin, margin, margin)
}

fun View.toPixels(dp: Float): Float {
    val dm = context.resources.displayMetrics
    val sFactor = dm.densityDpi.toFloat() / 160.0f
    return sFactor * dp
}

fun View.toPixels(dp: Int): Float {
    return toPixels(dp.toFloat())
}

fun View.setMarginDp(dp: Int) {
    val margin = toPixels(dp.toFloat()).toInt()
    setMargin(margin, margin, margin, margin)
}

fun View.setMargin(left: Int, top: Int, right: Int, bottom: Int) {
    when (layoutParams) {
        is LinearLayout.LayoutParams -> {
            (layoutParams as LinearLayout.LayoutParams).setMargins(left, top, right, bottom)
        }
        is RelativeLayout.LayoutParams -> {
            (layoutParams as RelativeLayout.LayoutParams).setMargins(left, top, right, bottom)
        }
        is FrameLayout.LayoutParams -> {
            (layoutParams as FrameLayout.LayoutParams).setMargins(left, top, right, bottom)
        }
    }
}