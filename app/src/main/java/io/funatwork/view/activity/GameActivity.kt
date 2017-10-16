package io.funatwork.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation
import android.support.v4.util.Pair.create
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.AddGoal
import io.funatwork.domain.interactor.LoadGame
import io.funatwork.domain.interactor.StopGame
import io.funatwork.domain.model.babyfoot.GameMode
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.GamePresenter
import io.funatwork.view.GameView
import xyz.hanks.library.SmallBang
import java.util.concurrent.TimeUnit


class GameActivity : BaseActivity(), GameView {

    private val presenter by lazy {
        GamePresenter(
                gameView = this,
                loadGame = LoadGame(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.sequentialJobExecutor),
                addGoal = AddGoal(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.sequentialJobExecutor),
                stopGame = StopGame(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.sequentialJobExecutor)
        )
    }

    private val imgRedPlayerAttack by lazy {
        findViewById<CircleImageView>(R.id.img_player_red_attack)
    }
    private val imgRedPlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_red_defense)
    }
    private val imgBluePlayerAttack by lazy {
        findViewById<CircleImageView>(R.id.img_player_blue_attack)
    }
    private val imgBluePlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_blue_defense)
    }

    private val imgGoalRedAttack by lazy {
        findViewById<ImageView>(R.id.img_red_player_attack_gamelle)
    }
    private val imgGoalRedDefense by lazy {
        findViewById<ImageView>(R.id.img_red_player_defense_gamelle)
    }
    private val imgGoalBlueAttack by lazy {
        findViewById<ImageView>(R.id.img_blue_player_attack_gamelle)
    }
    private val imgGoalBlueDefense by lazy {
        findViewById<ImageView>(R.id.img_blue_player_defense_gamelle)
    }

    private val tvScoreBlue by lazy {
        findViewById<TextView>(R.id.tv_score_blue)
    }

    private val tvScoreRed by lazy {
        findViewById<TextView>(R.id.tv_score_red)
    }

    private val imgGoal by lazy {
        findViewById<ImageView>(R.id.img_goal)
    }

    private val backgroundGoal by lazy {
        findViewById<LinearLayout>(R.id.background_goal)
    }

    private val tvCountDown by lazy {
        findViewById<TextView>(R.id.tv_game_countdown)
    }

    private val btnGameOver by lazy {
        findViewById<Button>(R.id.btn_game_over)
    }

    private val smallBang: SmallBang by lazy {
        SmallBang.attach2Window(this)
    }

    private val zoomInAnimation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.zoomin)
    }

    private var gameTimer: GameTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        supportActionBar?.title = getString(R.string.game_title)

        val gameId = intent.extras.getInt("GAME", -1)
        presenter.initialize(gameId, this)

        imgGoal.animation = zoomInAnimation
    }

    override fun renderGame(game: GameModel) {
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).into(imgBluePlayerAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).into(imgBluePlayerDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).into(imgRedPlayerAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).into(imgRedPlayerDefense)

        imgBluePlayerAttack.setOnClickListener {
            presenter.addGoal(game, game.blueTeam.attackPlayer)
        }
        imgBluePlayerDefense.setOnClickListener {
            presenter.addGoal(game, game.blueTeam.defensePlayer)
        }
        imgRedPlayerAttack.setOnClickListener {
            presenter.addGoal(game, game.redTeam.attackPlayer)
        }
        imgRedPlayerDefense.setOnClickListener {
            presenter.addGoal(game, game.redTeam.defensePlayer)
        }

        imgGoalBlueAttack.setOnClickListener {
            presenter.addGamelle(game, game.blueTeam.attackPlayer)
        }
        imgGoalBlueDefense.setOnClickListener {
            presenter.addGamelle(game, game.blueTeam.defensePlayer)
        }
        imgGoalRedAttack.setOnClickListener {
            presenter.addGamelle(game, game.redTeam.attackPlayer)
        }
        imgGoalRedDefense.setOnClickListener {
            presenter.addGamelle(game, game.redTeam.defensePlayer)
        }
        if (game.mode == GameMode.TIME) {
            gameTimer = GameTimer(game = game,
                    btnGameOver = btnGameOver,
                    tvCountDown = tvCountDown,
                    presenter = presenter)
            gameTimer?.start()
        }
    }

    override fun renderGoal(blueScore: Int, redScore: Int) {
        imgGoal.clearAnimation()
        backgroundGoal.visibility = GONE
        imgGoal.visibility = GONE
        if (tvScoreBlue.text != blueScore.toString()) {
            tvScoreBlue.text = blueScore.toString()
            smallBang.bang(tvScoreBlue)
        }
        if (tvScoreRed.text != redScore.toString()) {
            tvScoreRed.text = redScore.toString()
            smallBang.bang(tvScoreRed)
        }
    }

    override fun renderGameFinished(game: GameModel) {
        gameTimer?.cancel()
        val animations = animateWinner(game) + animateLooser(game)
        val options = makeSceneTransitionAnimation(this, animations[0], animations[1], animations[2], animations[3])
        navigator.navigateToGameOver(this, game, options)
    }

    private fun animateWinner(game: GameModel): List<android.support.v4.util.Pair<View, String>> =
            if (game.isRedTeamWin()) {
                listOf(create(imgRedPlayerAttack as View, "winnerAttackPlayer"),
                        create(imgRedPlayerDefense as View, "winnerDefensePlayer"))
            } else {
                listOf(create(imgBluePlayerAttack as View, "winnerAttackPlayer"),
                        create(imgBluePlayerDefense as View, "winnerDefensePlayer"))
            }

    private fun animateLooser(game: GameModel): List<android.support.v4.util.Pair<View, String>> =
            if (!game.isRedTeamWin()) {
                listOf(create(imgRedPlayerAttack as View, "looserAttackPlayer"),
                        create(imgRedPlayerDefense as View, "looserDefensePlayer"))
            } else {
                listOf(create(imgBluePlayerAttack as View, "looserAttackPlayer"),
                        create(imgBluePlayerDefense as View, "looserDefensePlayer"))
            }


    override fun renderGameCanceled() {
        navigator.navigateToHome(this, Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.game_cancel_message))
                .setTitle(getString(R.string.game_cancel_title))
                .setPositiveButton(getString(R.string.game_cancel_yes), { dialog, _ ->
                    presenter.cancelGame()
                    dialog.dismiss()
                }).setNegativeButton(getString(R.string.game_cancel_no), { dialog, _ ->
            dialog.cancel()
        }).show()
    }

    override fun showNewGoalProcessing() {
        backgroundGoal.visibility = VISIBLE
        imgGoal.visibility = VISIBLE
        imgGoal.startAnimation(zoomInAnimation)
    }

    override fun dismissNewGoalProcessing() {
        backgroundGoal.visibility = GONE
        imgGoal.visibility = GONE
    }

    private class GameTimer(private val game: GameModel,
                            private val presenter: GamePresenter,
                            private val tvCountDown: TextView,
                            private val btnGameOver: Button) : CountDownTimer(game.modeLimitValue * 60L * 1000L, 1000) {
        override fun onFinish() {
            tvCountDown.text = ""
            btnGameOver.visibility = View.VISIBLE
            btnGameOver.setOnClickListener {
                presenter.timesUp(game)
            }
        }

        override fun onTick(millisUntilFinished: Long) {
            tvCountDown.text = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes(millisUntilFinished)))
        }

    }
}
