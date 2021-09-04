package io.daniilxt.common.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.statusBarHeight(): Int {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return if (resourceId > 0) resources.getDimensionPixelSize(resourceId)
    else 0
}

fun Fragment.addBackPressedCallback(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        })
}
