package io.funatwork.view.adapter

import com.chad.library.adapter.base.entity.MultiItemEntity
import io.funatwork.domain.model.babyfoot.GameStatus
import io.funatwork.model.babyfoot.GameModel

class MultipleGameItem(val game: GameModel, private val selected: Boolean = false, val itemTypeEntity: Int = viewType(game)) : MultiItemEntity {

    companion object Type {
        val LIGHT = 1
        val FULL_PENDING = 2
        val FULL_STARTED = 3
        val FULL_FINISHED = 4
        val FULL_CANCELED = 5

        fun viewType(game: GameModel) =
                when (game.status) {
                    GameStatus.STARTED -> LIGHT
                    GameStatus.PLANNED -> FULL_PENDING
                    GameStatus.CANCELED -> LIGHT
                    GameStatus.OVER -> LIGHT
                    else -> LIGHT
                }
    }

    override fun getItemType() =
            LIGHT
}