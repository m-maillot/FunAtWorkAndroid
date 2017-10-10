package io.funatwork.view

import io.funatwork.model.UserAuthModel
import io.funatwork.model.babyfoot.PlayerStatsModel

interface AccountView : LoadDataView {

    /**
     * User connected, received userAuth information
     */
    fun renderAccount(userAuth: UserAuthModel)

    /**
     * Render player stats
     */
    fun renderPlayerStats(playerStats: PlayerStatsModel)
}