package io.funatwork.presenter

import io.funatwork.domain.interactor.DefaultObserver
import io.funatwork.domain.interactor.GetPlayerList
import io.funatwork.domain.interactor.StartGame
import io.funatwork.domain.interactor.params.CreateGameParam
import io.funatwork.domain.interactor.params.NoParams
import io.funatwork.domain.model.Player
import io.funatwork.domain.model.babyfoot.Game
import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.model.babyfoot.toBo
import io.funatwork.model.babyfoot.toModel
import io.funatwork.model.toModel
import io.funatwork.view.StartGameView
import io.funatwork.view.adapter.PlayerAdapter

class CreateGamePresenter(val startGameView: StartGameView, val getPlayerList: GetPlayerList, val startGame: StartGame) : Presenter, PlayerAdapter.OnItemClickListener {

    var playerRedAttack: PlayerModel = PlayerModel(-1, "unknown", "")
    var playerRedDefense: PlayerModel = PlayerModel(-1, "unknown", "")
    var playerBlueAttack: PlayerModel = PlayerModel(-1, "unknown", "")
    var playerBlueDefense: PlayerModel = PlayerModel(-1, "unknown", "")

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    /**
     * Initializes the presenter by start retrieving the players list.
     */
    fun initialize() {
        this.loadPlayerList()
    }

    fun startGame(redTeam: TeamModel, blueTeam: TeamModel) {
        startGameView.showLoading()
        startGame.execute(StartGameObserver(startGameView),
                CreateGameParam(redTeam.toBo(), blueTeam.toBo()))
    }

    /**
     * Loads all users.
     */
    private fun loadPlayerList() {
        startGameView.showLoading()
        getPlayerList.execute(UserListObserver(startGameView), NoParams())
    }

    override fun onPlayerItemClicked(player: PlayerModel) {
        if (playerRedAttack.id == -1) {
            playerRedAttack = player
            startGameView.onSelectPlayer(player, Team.RED, Position.ATTACK)
        } else if (playerRedDefense.id == -1) {
            playerRedDefense = player
            startGameView.onSelectPlayer(player, Team.RED, Position.DEFENSE)
        } else if (playerBlueAttack.id == -1) {
            playerBlueAttack = player
            startGameView.onSelectPlayer(player, Team.BLUE, Position.ATTACK)
        } else if (playerBlueDefense.id == -1) {
            playerBlueDefense = player
            startGameView.onSelectPlayer(player, Team.BLUE, Position.DEFENSE)
        }
        if (playerBlueAttack.id != -1 && playerRedDefense.id != -1
                && playerRedAttack.id != -1 && playerBlueDefense.id != -1) {
            startGameView.onReadyToStart(
                    TeamModel(attackPlayer = playerRedAttack, defensePlayer = playerRedDefense),
                    TeamModel(attackPlayer = playerBlueAttack, defensePlayer = playerBlueDefense))
        }
    }

    private class UserListObserver(val startGameView: StartGameView) : DefaultObserver<List<Player>>() {

        override fun onComplete() {
            startGameView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            startGameView.hideLoading()
            startGameView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(players: List<Player>) {
            startGameView.renderPlayerList(players.map(Player::toModel))
        }
    }

    private class StartGameObserver(val startGameView: StartGameView) : DefaultObserver<Game>() {

        override fun onComplete() {
            startGameView.hideLoading()
        }

        override fun onError(e: Throwable?) {
            startGameView.hideLoading()
            startGameView.showError(title = "Error happen", message = e?.message ?: "Unknown Error")
        }

        override fun onNext(game: Game) {
            startGameView.onGameStarted(game.toModel())
        }
    }
}