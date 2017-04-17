package io.funatwork.view.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.GameDataStoreFactory
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.GamePresenter
import io.funatwork.view.GameView
import xyz.hanks.library.SmallBang

class GameActivity : BaseActivity(), GameView {

    val presenter by lazy {
        GamePresenter(
                gameView = this,
                loadGame = LoadGame(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor),
                addGoal = AddGoal(
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

    val tvScoreBlue by lazy {
        findViewById(R.id.tv_score_blue) as TextView
    }
    val tvScoreRed by lazy {
        findViewById(R.id.tv_score_red) as TextView
    }

    val smallBang by lazy {
        SmallBang.attach2Window(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val gameId = intent.extras.getInt("GAME", -1)
        presenter.initialize(gameId)
    }

    override fun renderGame(game: GameModel) {
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).fit().into(imgPlayerBlueAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).fit().into(imgPlayerBlueDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).fit().into(imgPlayerRedAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).fit().into(imgPlayerRedDefense)


        imgPlayerBlueAttack.setOnClickListener {
            smallBang.bang(imgPlayerBlueAttack)
            presenter.addGoal(game, game.blueTeam.attackPlayer)
        }
        imgPlayerBlueDefense.setOnClickListener {
            smallBang.bang(imgPlayerBlueDefense)
            presenter.addGoal(game, game.blueTeam.defensePlayer)
        }
        imgPlayerRedAttack.setOnClickListener {
            smallBang.bang(imgPlayerRedAttack)
            presenter.addGoal(game, game.redTeam.attackPlayer)
        }
        imgPlayerRedDefense.setOnClickListener {
            smallBang.bang(imgPlayerRedDefense)
            presenter.addGoal(game, game.redTeam.defensePlayer)
        }
    }

    override fun renderGoal(game: GameModel) {
        tvScoreBlue.text = game.blueTeamGoal.toString()
        tvScoreRed.text = game.redTeamGoal.toString()
    }

    override fun renderGameFinished(game: GameModel) =
            navigator.navigateToGameOver(this, game)
}
