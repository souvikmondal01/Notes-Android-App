package com.kivous.notes.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kivous.notes.R


object Extensions {
    fun Activity.isDarkMode() =
        this.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

    fun ImageView.glideCircle(context: Context, url: Any?) {
        url?.let {
            Glide.with(context).load(it.toString()).placeholder(R.drawable.account_circle)
                .circleCrop().into(this)
        }
    }

    fun View.gone() {
        visibility = View.GONE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun Fragment.setImageViewTint(imageView: ImageView, attr: Int) {
        val typedValue = TypedValue()
        val theme = requireContext().theme
        theme.resolveAttribute(attr, typedValue, true)
        val colorOnSurface = typedValue.data
        val colorStateList = android.content.res.ColorStateList.valueOf(colorOnSurface)
        ImageViewCompat.setImageTintList(imageView, colorStateList)
    }

}