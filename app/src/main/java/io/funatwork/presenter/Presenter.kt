package io.funatwork.presenter

interface Presenter {

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * onResume() method.
     */
    fun resume()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * onPause() method.
     */
    fun pause()

    /**
     * Method that control the lifecycle of the view. It should be called in the view's
     * onDestroy() method.
     */
    fun destroy()
}