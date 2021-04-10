package com.harsewak.themoviedb.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.inflate
import com.harsewak.themoviedb.base.load
import com.harsewak.themoviedb.base.setMarginDp
import com.harsewak.themoviedb.base.setWidthLayoutParams
import com.harsewak.themoviedb.data.Movie

/**
 * Created by Harsewak Singh on 10/04/21.
 */
class MovieCardView: CardView {

    lateinit var posterImage: AppCompatImageView
    lateinit var titleText: AppCompatTextView
    lateinit var releaseDateText: AppCompatTextView

    init {
        setupView()
    }

    private fun setupView() {
        setWidthLayoutParams()
        setMarginDp(4)
        val view = context.inflate(R.layout.view_movie_card, this)
        posterImage = view.findViewById(R.id.posterImage)
        titleText = view.findViewById(R.id.titleText)
        releaseDateText = view.findViewById(R.id.releaseDateText)
        radius = 20f
    }

    fun bind(t: Movie) {
        titleText.text = t.title
        posterImage.load(t.posterUrl)
        releaseDateText.text = t.releaseDate
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setupView()
        radius = 0f
        cardElevation = 0f
    }


}