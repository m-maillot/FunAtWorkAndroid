package io.funatwork.view.activity

import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.GameDataStoreFactory
import io.funatwork.domain.interactor.StartGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.presenter.StartGamePresenter
import io.funatwork.utils.CircleTransformation
import io.funatwork.view.StartGameView

class StartGameActivity : BaseActivity(), StartGameView {

    val presenter by lazy {
        StartGamePresenter(
                startGameView = this,
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

    val imgKickOff by lazy {
        findViewById(R.id.img_kickoff) as ImageView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.start_game_title)

        val redTeam = intent.extras.getSerializable("RED_TEAM") as TeamModel
        val blueTeam = intent.extras.getSerializable("BLUE_TEAM") as TeamModel

        Picasso.with(this).load(redTeam.attackPlayer.avatar).transform(CircleTransformation()).fit().into(imgRedPlayerAttack)
        Picasso.with(this).load(redTeam.defensePlayer.avatar).transform(CircleTransformation()).fit().into(imgRedPlayerDefense)

        Picasso.with(this).load(blueTeam.attackPlayer.avatar).transform(CircleTransformation()).fit().into(imgBluePlayerAttack)
        Picasso.with(this).load(blueTeam.defensePlayer.avatar).transform(CircleTransformation()).fit().into(imgBluePlayerDefense)

        imgKickOff.setOnClickListener {
            presenter.startGame(redTeam, blueTeam)
        }
    }

    override fun onGameStarted(game: GameModel) {
        navigator.navigateToGame(this, game)
        finish()
    }
}
