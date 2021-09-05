package io.daniilxt.helper

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import timber.log.Timber

object GlideHelper {

    fun loadGif(
        context: Context,
        imageURL: String,
        imageViewContainer: ImageView,
        onErrorLoadCallBack: () -> Unit
    ) {
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 8f
        circularProgressDrawable.centerRadius = 40f
        circularProgressDrawable.setColorSchemeColors(Color.BLUE)
        circularProgressDrawable.start()

        Glide.with(context)
            .load(imageURL)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Timber.i("GLIDE ERROR ${e.toString()}")
                    circularProgressDrawable.stop()
                    onErrorLoadCallBack.invoke()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
            .placeholder(circularProgressDrawable)
            .into(imageViewContainer)
    }
}