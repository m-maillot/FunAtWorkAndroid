package io.funatwork.view

import io.funatwork.model.PlayerModel

interface PlayerListView: LoadDataView {

    fun renderPlayerList(playerModelList: List<PlayerModel>)

    fun viewPlayer(playerModel: PlayerModel)
}