package io.funatwork.view

import io.funatwork.model.UserAuthModel

interface AccountView : LoadDataView {

    /**
     * User connected, received userAuth information
     */
    fun renderAccount(userAuth: UserAuthModel)
}