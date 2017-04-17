package io.funatwork.view.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.PlayerCacheImpl
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.PlayerDataRepository
import io.funatwork.core.repository.datasource.GameDataStoreFactory
import io.funatwork.core.repository.datasource.PlayerDataStoreFactory
import io.funatwork.domain.interactor.GetPlayerList
import io.funatwork.domain.interactor.StartGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.presenter.CreateGamePresenter
import io.funatwork.view.StartGameView
import io.funatwork.view.adapter.PlayerAdapter
import pl.bclogic.pulsator4droid.library.PulsatorLayout


class CreateGameActivity : BaseActivity(), StartGameView {

    val recyclerPlayers by lazy {
        findViewById(R.id.rv_players) as RecyclerView
    }

    val presenter by lazy {
        CreateGamePresenter(
                startGameView = this,
                getPlayerList = GetPlayerList(
                        playerRepository = PlayerDataRepository(
                                playerDataStoreFactory = PlayerDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager()),
                                        playerCache = PlayerCacheImpl(
                                                context = this,
                                                cacheDir = cacheDir,
                                                fileManager = FileManager(),
                                                threadExecutor = fwtApplication.jobExecutor,
                                                serializer = Serializer()
                                        ))
                        ),
                        threadExecutor = fwtApplication.jobExecutor,
                        postExecutionThread = fwtApplication.uiThread),
                startGame = StartGame(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor)
        )
    }

    val imgPlayerRedAttack by lazy {
        findViewById(R.id.img_player_red_attack) as ImageView
    }
    val imgPlayerRedDefense by lazy {
        findViewById(R.id.img_player_red_defense) as ImageView
    }
    val imgPlayerBlueAttack by lazy {
        findViewById(R.id.img_player_blue_attack) as ImageView
    }
    val imgPlayerBlueDefense by lazy {
        findViewById(R.id.img_player_blue_defense) as ImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        recyclerPlayers.setHasFixedSize(true)
        recyclerPlayers.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)

        presenter.initialize()
    }

    override fun renderPlayerList(playerModelList: List<PlayerModel>) {
        recyclerPlayers.adapter = PlayerAdapter(this, playerModelList, presenter)
    }

    override fun onSelectPlayer(player: PlayerModel, team: Team, position: Position) {
        if (team == Team.RED) {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).fit().into(imgPlayerRedAttack)
            } else {
                Picasso.with(this).load(player.avatar).fit().into(imgPlayerRedDefense)
            }
        } else {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).fit().into(imgPlayerBlueAttack)
            } else {
                Picasso.with(this).load(player.avatar).fit().into(imgPlayerBlueDefense)
            }
        }
    }

    override fun onReadyToStart(redTeam: TeamModel, blueTeam: TeamModel) {
        val btnStart = findViewById(R.id.btn_start_game) as ImageView
        val pulsar = findViewById(R.id.pulsar_start_game) as PulsatorLayout
        btnStart.visibility = View.VISIBLE
        pulsar.start()
        btnStart.setOnClickListener {
            presenter.startGame(redTeam, blueTeam)
        }
    }

    override fun onGameStarted(game: GameModel) {
        navigator.navigateToGame(this, game)
    }
}
