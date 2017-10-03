package io.funatwork.view.activity

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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
import io.funatwork.view.SelectPlayersView
import io.funatwork.view.adapter.PlayerAdapter


class SelectPlayerActivity : BaseActivity(), SelectPlayersView {

    val recyclerPlayers by lazy {
        findViewById<RecyclerView>(R.id.rv_players)
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
        findViewById<CircleImageView>(R.id.img_player_red_attack)
    }
    val imgRedPlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_red_defense)
    }
    val imgBluePlayerAttack by lazy {
        findViewById<CircleImageView>(R.id.img_player_blue_attack)
    }
    val imgBluePlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_blue_defense)
    }

    val playerRedAttackId by lazy { intent.extras?.getInt("redAttackId", -1) ?: -1 }
    val playerRedDefenseId by lazy { intent.extras?.getInt("redDefenseId", -1) ?: -1 }
    val playerBlueAttackId by lazy { intent.extras?.getInt("blueAttackId", -1) ?: -1 }
    val playerBlueDefenseId by lazy { intent.extras?.getInt("blueDefenseId", -1) ?: -1 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_player)

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.title = getString(R.string.create_game_players_title)
        }

        recyclerPlayers.setHasFixedSize(true)
        recyclerPlayers.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)

        presenter.initialize()
        imgRedPlayerAttack.setOnClickListener { presenter.removePlayer(Team.RED, Position.ATTACK) }
        imgRedPlayerDefense.setOnClickListener { presenter.removePlayer(Team.RED, Position.DEFENSE) }
        imgBluePlayerAttack.setOnClickListener { presenter.removePlayer(Team.BLUE, Position.ATTACK) }
        imgBluePlayerDefense.setOnClickListener { presenter.removePlayer(Team.BLUE, Position.DEFENSE) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            else -> {

            }
        }
        return true
    }

    override fun renderPlayerList(playerModelList: List<PlayerModel>) {
        recyclerPlayers.adapter = PlayerAdapter(this, playerModelList, presenter)
        playerModelList.find { it.id == playerRedAttackId }?.let {
            presenter.setDefaultPlayerSelected(it, Team.RED, Position.ATTACK)
        }
        playerModelList.find { it.id == playerRedDefenseId }?.let {
            presenter.setDefaultPlayerSelected(it, Team.RED, Position.DEFENSE)
        }
        playerModelList.find { it.id == playerBlueAttackId }?.let {
            presenter.setDefaultPlayerSelected(it, Team.BLUE, Position.ATTACK)
        }
        playerModelList.find { it.id == playerBlueDefenseId }?.let {
            presenter.setDefaultPlayerSelected(it, Team.BLUE, Position.DEFENSE)
        }
    }

    override fun onSelectPlayer(player: PlayerModel, team: Team, position: Position) {
        if (team == Team.RED) {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).into(imgRedPlayerAttack)
            } else {
                Picasso.with(this).load(player.avatar).into(imgRedPlayerDefense)
            }
        } else {
            if (position == Position.ATTACK) {
                Picasso.with(this).load(player.avatar).into(imgBluePlayerAttack)
            } else {
                Picasso.with(this).load(player.avatar).into(imgBluePlayerDefense)
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
        val p1 = android.support.v4.util.Pair.create(imgRedPlayerAttack as View, "redAttackPlayer")
        val p2 = android.support.v4.util.Pair.create(imgRedPlayerDefense as View, "redDefensePlayer")
        val p3 = android.support.v4.util.Pair.create(imgBluePlayerAttack as View, "blueAttackPlayer")
        val p4 = android.support.v4.util.Pair.create(imgBluePlayerDefense as View, "blueDefensePlayer")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)

        navigator.navigateToStartGame(this, redTeam, blueTeam, options)
    }
}
