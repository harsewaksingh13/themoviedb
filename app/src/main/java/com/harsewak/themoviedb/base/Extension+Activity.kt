package com.harsewak.themoviedb.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.harsewak.themoviedb.MovieApplication
import com.harsewak.themoviedb.R
import kotlin.math.roundToInt

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
        placeholder?.let {
            Glide.with(context).load(url).placeholder(it).into(this)
        } ?: run {
            Glide.with(context).load(url).into(this)
        }
    } ?: run {
        placeholder?.let {
            setImageResource(placeholder)
        }
    }
}

fun View.setWidthMatchParent() {
    layoutParams?.width = MATCH_PARENT
}

fun View.setHeightMatchParent() {
    layoutParams?.height = MATCH_PARENT
}

fun View.setLayoutParams() {
    val layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.WRAP_CONTENT,
        FrameLayout.LayoutParams.WRAP_CONTENT
    )
    setLayoutParams(layoutParams)
}


fun View.setMargin(id: Int) {
    val margin = context.resources.getDimensionPixelSize(id)
    setMargin(margin, margin, margin, margin)
}


/**
 * Converts any given number from pixels (px) into density independent pixels (dp).
 */
val Number.px2dp: Float
    get() = this.toFloat() / Resources.getSystem().displayMetrics.density

/**
 * Converts any given number from density independent pixels (dp) into pixels (px).
 */
val Number.dp2px: Int
    get() = (this.toFloat() * Resources.getSystem().displayMetrics.density).roundToInt()

val Context.screenWidth
    get() = Resources.getSystem().displayMetrics.widthPixels

fun View.setMarginDp(dp: Int) {
    val margin = dp.dp2px
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

val String?.safe: String
    get() {
        return this ?: ""
    }
