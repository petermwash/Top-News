package com.pemwa.topnews.view

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.pemwa.topnews.NewsApplication

/**
 * A Fragment extension function to check for network connectivity
 */
fun Fragment.isNetworkConnected(): Boolean =
    context?.let {
        NewsApplication.isNetworkConnected
    } ?: false
