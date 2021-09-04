package io.daniilxt.common.extensions

import android.app.Activity
import android.os.Build
import androidx.annotation.ColorRes

@Suppress("DEPRECATION")
fun Activity.setStatusBarColor(@ColorRes color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = resources.getColor(color, theme)
    } else {
        window.statusBarColor = resources.getColor(color)
    }
}