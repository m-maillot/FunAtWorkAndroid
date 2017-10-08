package io.funatwork.view.adapter.item

import com.chad.library.adapter.base.entity.MultiItemEntity
import io.funatwork.model.babyfoot.GameModel

sealed class GameItem : MultiItemEntity {
    companion object {
        val GAME = 1
        val HEADER = 2
    }
}

data class Game(val game: GameModel) : GameItem() {
    override fun getItemType() = GAME
}

data class Header(val roundIndex: Int, val roundIndexInversed: Int) : GameItem() {
    override fun getItemType() = HEADER
}