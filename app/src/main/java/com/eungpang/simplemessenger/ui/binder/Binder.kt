package com.eungpang.simplemessenger.ui.binder

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object Binder {
    @JvmStatic
    @BindingAdapter("loadAvatar")
    fun ImageView.loadAvatar(url: String?) {
        // TODO: validate url, and if it's invalid or failed to load, show default one
        if (url == null) {
            val defaultAvatarResourceId = resources.getIdentifier(
                "ic_launcher_foreground",
                "drawable",
                context.packageName
            )
            Glide.with(context).load(defaultAvatarResourceId).into(this)
            return
        }

        Glide.with(context).load(url).into(this)
    }
}