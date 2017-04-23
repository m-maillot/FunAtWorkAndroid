package io.funatwork.view

import io.funatwork.model.babyfoot.GameModel

interface HistoryView : LoadDataView {

    fun renderGameList(games: List<GameModel>)
}