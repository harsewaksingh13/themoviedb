package com.harsewak.themoviedb.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import com.harsewak.themoviedb.R
import com.harsewak.themoviedb.base.*
import com.harsewak.themoviedb.data.Movie

/**
 * Created by Harsewak Singh on 10/04/21.
 */
class MovieCardView : CardView {

    private lateinit var posterImage: AppCompatImageView
    private lateinit var titleText: AppCompatTextView
    private lateinit var releaseDateText: AppCompatTextView

    init {
        setupView()
    }

    private fun setupView() {
        setLayoutParams()
        setWidthMatchParent()
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

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupView()
        resetCard()
    }

    private fun resetCard() {
        radius = 0f
        cardElevation = 0f
    }

    fun setupForHorizontal(): MovieCardView {
        titleText.setLines(2)
        layoutParams?.width = context.screenWidth - 200
        return this
    }
}