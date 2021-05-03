package com.harsewak.themoviedb.base

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * Created by Harsewak Singh on 29/04/21.
 */
val Context.screenWidth
    get() = Resources.getSystem().displayMetrics.widthPixels

fun Context.inflate(@LayoutRes layoutId: Int, root: ViewGroup? = null): View {
    return LayoutInflater.from(this).inflate(layoutId, root)
}
