package io.funatwork.view.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.domain.interactor.StopGame
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
                        threadExecutor = fwtApplication.jobExecutor),
                stopGame = StopGame(
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
        findViewById(R.id.img_player_red_attack) as CircleImageView
    }
    val imgRedPlayerDefense by lazy {
        findViewById(R.id.img_player_red_defense) as CircleImageView
    }
    val imgBluePlayerAttack by lazy {
        findViewById(R.id.img_player_blue_attack) as CircleImageView
    }
    val imgBluePlayerDefense by lazy {
        findViewById(R.id.img_player_blue_defense) as CircleImageView
    }

    val imgGoalRedAttack by lazy {
        findViewById(R.id.tv_red_attack_gamelle) as TextView
    }
    val imgGoalRedDefense by lazy {
        findViewById(R.id.tv_red_defense_gamelle) as TextView
    }
    val imgGoalBlueAttack by lazy {
        findViewById(R.id.tv_blue_attack_gamelle) as TextView
    }
    val imgGoalBlueDefense by lazy {
        findViewById(R.id.tv_blue_defense_gamelle) as TextView
    }

    val tvScoreBlue by lazy {
        findViewById(R.id.tv_score_blue) as TextView
    }
    val tvScoreRed by lazy {
        findViewById(R.id.tv_score_red) as TextView
    }

    val smallBang: SmallBang by lazy {
        SmallBang.attach2Window(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar?.title = getString(R.string.game_title)

        val gameId = intent.extras.getInt("GAME", -1)
        presenter.initialize(gameId)
    }

    override fun renderGame(game: GameModel) {
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).into(imgBluePlayerAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).into(imgBluePlayerDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).into(imgRedPlayerAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).into(imgRedPlayerDefense)

        imgBluePlayerAttack.setOnClickListener {
            smallBang.bang(imgBluePlayerAttack)
            presenter.addGoal(game, game.blueTeam.attackPlayer)
        }
        imgBluePlayerDefense.setOnClickListener {
            smallBang.bang(imgBluePlayerDefense)
            presenter.addGoal(game, game.blueTeam.defensePlayer)
        }
        imgRedPlayerAttack.setOnClickListener {
            smallBang.bang(imgRedPlayerAttack)
            presenter.addGoal(game, game.redTeam.attackPlayer)
        }
        imgRedPlayerDefense.setOnClickListener {
            smallBang.bang(imgRedPlayerDefense)
            presenter.addGoal(game, game.redTeam.defensePlayer)
        }

        imgGoalBlueAttack.setOnClickListener {
            smallBang.bang(imgGoalBlueAttack)
            presenter.addGamelle(game, game.blueTeam.attackPlayer)
        }
        imgGoalBlueDefense.setOnClickListener {
            smallBang.bang(imgGoalBlueDefense)
            presenter.addGamelle(game, game.blueTeam.defensePlayer)
        }
        imgGoalRedAttack.setOnClickListener {
            smallBang.bang(imgGoalRedAttack)
            presenter.addGamelle(game, game.redTeam.attackPlayer)
        }
        imgGoalRedDefense.setOnClickListener {
            smallBang.bang(imgGoalRedDefense)
            presenter.addGamelle(game, game.redTeam.defensePlayer)
        }
    }

    override fun renderGoal(game: GameModel) {
        tvScoreBlue.text = game.blueTeamGoal.toString()
        tvScoreRed.text = game.redTeamGoal.toString()
    }

    override fun renderGameFinished(game: GameModel) {
        val p1 = android.support.v4.util.Pair.create(imgRedPlayerAttack as View, "redAttackPlayer")
        val p2 = android.support.v4.util.Pair.create(imgRedPlayerDefense as View, "redDefensePlayer")
        val p3 = android.support.v4.util.Pair.create(imgBluePlayerAttack as View, "blueAttackPlayer")
        val p4 = android.support.v4.util.Pair.create(imgBluePlayerDefense as View, "blueDefensePlayer")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)
        navigator.navigateToGameOver(this, game, options)
    }


    override fun renderGameCanceled() {
        navigator.navigateToHome(this, Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    override fun onBackPressed() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText(getString(R.string.game_cancel_message))
                .setTitleText(getString(R.string.game_cancel_title))
                .setConfirmText(getString(R.string.game_cancel_yes))
                .setCancelText(getString(R.string.game_cancel_no))
                .setConfirmClickListener { dialog ->
                    presenter.cancelGame()
                    dialog.dismissWithAnimation()
                }
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .show()
    }
}
