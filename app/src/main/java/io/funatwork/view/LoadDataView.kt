package io.funatwork.view

import android.content.Context

/**
 * Interface representing a View that will use to load data.
 */
interface LoadDataView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    fun showLoading()

    /**
     * Hide a loading view.
     */
    fun hideLoading()

    /**
     * Show an error message
     * @param title A string representing an error highlight
     * @param message A string representing an error.
     */
    fun showError(title: String, message: String)

    /**
     * Get a [android.content.Context].
     */
    fun context(): Context
}
