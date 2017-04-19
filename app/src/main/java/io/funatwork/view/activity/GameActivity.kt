package io.funatwork.view.activity

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.GamePresenter
import io.funatwork.utils.CircleTransformation
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

    val imgGoalRedAttack by lazy {
        findViewById(R.id.img_red_attack_goal) as ImageView
    }
    val imgGoalRedDefense by lazy {
        findViewById(R.id.img_red_defense_goal) as ImageView
    }
    val imgGoalBlueAttack by lazy {
        findViewById(R.id.img_blue_attack_goal) as ImageView
    }
    val imgGoalBlueDefense by lazy {
        findViewById(R.id.img_blue_defense_goal) as ImageView
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

        supportActionBar?.title = getString(R.string.game_title)

        val gameId = intent.extras.getInt("GAME", -1)
        presenter.initialize(gameId)
    }

    override fun renderGame(game: GameModel) {
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerDefense)


        imgGoalBlueAttack.setOnClickListener {
            smallBang.bang(imgGoalBlueAttack)
            presenter.addGoal(game, game.blueTeam.attackPlayer)
        }
        imgGoalBlueDefense.setOnClickListener {
            smallBang.bang(imgGoalBlueDefense)
            presenter.addGoal(game, game.blueTeam.defensePlayer)
        }
        imgGoalRedAttack.setOnClickListener {
            smallBang.bang(imgGoalRedAttack)
            presenter.addGoal(game, game.redTeam.attackPlayer)
        }
        imgGoalRedDefense.setOnClickListener {
            smallBang.bang(imgGoalRedDefense)
            presenter.addGoal(game, game.redTeam.defensePlayer)
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
        finish()
    }


    override fun onBackPressed() {
        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText(getString(R.string.game_cancel_message))
                .setTitleText(getString(R.string.game_cancel_title))
                .setConfirmText(getString(R.string.game_cancel_yes))
                .setCancelText(getString(R.string.game_cancel_no))
                .setConfirmClickListener {
                    presenter.cancelGame()
                }
                .setCancelClickListener(SweetAlertDialog::dismissWithAnimation)
                .show()
    }
}
