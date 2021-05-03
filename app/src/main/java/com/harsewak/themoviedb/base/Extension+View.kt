package com.harsewak.themoviedb.base

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

/**
 * Created by Harsewak Singh on 03/05/21.
 */

fun View.setWidthMatchParent() {
    layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
}

fun View.setHeightMatchParent() {
    layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
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
