package io.funatwork.view.activity

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.PlayerCacheImpl
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.PlayerDataRepository
import io.funatwork.core.repository.datasource.PlayerDataStoreFactory
import io.funatwork.domain.interactor.GetPlayerList
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.PlayerModel
import io.funatwork.model.Position
import io.funatwork.model.Team
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.presenter.SelectPlayersPresenter
import io.funatwork.utils.CircleTransformation
import io.funatwork.view.SelectPlayersView
import io.funatwork.view.adapter.PlayerAdapter


class CreateGameActivity : BaseActivity(), SelectPlayersView {

    val recyclerPlayers by lazy {
        findViewById(R.id.rv_players) as RecyclerView
    }

    val presenter by lazy {
        SelectPlayersPresenter(
                selectPlayersView = this,
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
                        postExecutionThread = fwtApplication.uiThread)
        )
    }

    val imgRedPlayerAttack by lazy {
        findViewById(R.id.img_player_red_attack) as ImageView
    }
    val imgRedPlayerDefense by lazy {
        findViewById(R.id.img_player_red_defense) as ImageView
    }
    val imgBluePlayerAttack by lazy {
        findViewById(R.id.img_player_blue_attack) as ImageView
    }
    val imgBluePlayerDefense by lazy {
        findViewById(R.id.img_player_blue_defense) as ImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_game)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.create_game_players_title)

        recyclerPlayers.setHasFixedSize(true)
        recyclerPlayers.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)

        presenter.initialize()
        imgRedPlayerAttack.setOnClickListener { presenter.removePlayer(Team.RED, Position.ATTACK) }
        imgRedPlayerDefense.setOnClickListener { presenter.removePlayer(Team.RED, Position.DEFENSE) }
        imgBluePlayerAttack.setOnClickListener { presenter.removePlayer(Team.BLUE, Position.ATTACK) }
        imgBluePlayerDefense.setOnClickListener { presenter.removePlayer(Team.BLUE, Position.DEFENSE) }
    }

    override fun renderPlayerList(playerModelList: List<PlayerModel>) {
        recyclerPlayers.adapter = PlayerAdapter(this, playerModelList, presenter)
    }

    override fun onSelectPlayer(player: PlayerModel, team: Team, position: Position) {
        if (team == Team.RED) {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerAttack)
            } else {
                Picasso.with(this).load(player.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerDefense)
            }
        } else {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerAttack)
            } else {
                Picasso.with(this).load(player.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerDefense)
            }
        }
    }

    override fun onRemovePlayer(player: PlayerModel, team: Team, position: Position) {
        if (team == Team.RED) {
            if (position == Position.ATTACK) {
                imgRedPlayerAttack.setImageResource(R.drawable.ic_user)
            } else {
                imgRedPlayerDefense.setImageResource(R.drawable.ic_user)
            }
        } else {
            if (position == Position.ATTACK) {
                imgBluePlayerAttack.setImageResource(R.drawable.ic_user)
            } else {
                imgBluePlayerDefense.setImageResource(R.drawable.ic_user)
            }
        }
    }

    override fun onReadyToStart(redTeam: TeamModel, blueTeam: TeamModel) {
        navigator.navigateToStartGame(this, redTeam, blueTeam)
    }
}
