package io.funatwork.view

import io.funatwork.model.UserAuthModel

interface AccountView : LoadDataView {

    /**
     * User not connected, should connect first
     */
    fun renderNotConnected()

    /**
     * User connected, received userAuth information
     */
    fun renderAccount(userAuth: UserAuthModel)
}