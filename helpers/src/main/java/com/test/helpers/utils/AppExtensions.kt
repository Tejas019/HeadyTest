package com.test.helpers.utils

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.setLoading(isLoading: Boolean) {
    this.visibility = if (isLoading) View.VISIBLE else View.GONE
}