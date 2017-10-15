package io.funatwork.view.activity

import android.os.Bundle
import android.support.constraint.Group
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.StartGame
import io.funatwork.domain.model.babyfoot.GameMode.SCORE
import io.funatwork.domain.model.babyfoot.GameMode.TIME
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TeamModel
import io.funatwork.presenter.StartGamePresenter
import io.funatwork.view.StartGameView
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

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

    private val imgScoreLimit by lazy {
        findViewById<CircleImageView>(R.id.img_game_score_limit)
    }
    private val imgTimeLimit by lazy {
        findViewById<CircleImageView>(R.id.img_game_time_limit)
    }

    private val groupLimit by lazy {
        findViewById<Group>(R.id.group_game_limit_values)
    }
    private val imgLimitFive by lazy {
        findViewById<CircleImageView>(R.id.img_game_limit_5)
    }
    private val imgLimitSeven by lazy {
        findViewById<CircleImageView>(R.id.img_game_limit_7)
    }
    private val imgLimitTen by lazy {
        findViewById<CircleImageView>(R.id.img_game_limit_10)
    }

    private val btnKickOff by lazy {
        findViewById<Button>(R.id.btn_kickoff)
    }

    private var mode = SCORE
    private var modeValueLimit = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.start_game_title)

        val redTeam = intent.extras.getSerializable("RED_TEAM") as TeamModel
        val blueTeam = intent.extras.getSerializable("BLUE_TEAM") as TeamModel

        Picasso.with(this).load(redTeam.attackPlayer.avatar).into(imgRedPlayerAttack)
        (findViewById<TextView>(R.id.tv_player_red_attack)).text = redTeam.attackPlayer.name
        Picasso.with(this).load(redTeam.defensePlayer.avatar).into(imgRedPlayerDefense)
        (findViewById<TextView>(R.id.tv_player_red_defense)).text = redTeam.defensePlayer.name

        Picasso.with(this).load(blueTeam.attackPlayer.avatar).into(imgBluePlayerAttack)
        (findViewById<TextView>(R.id.tv_player_blue_attack)).text = blueTeam.attackPlayer.name
        Picasso.with(this).load(blueTeam.defensePlayer.avatar).into(imgBluePlayerDefense)
        (findViewById<TextView>(R.id.tv_player_blue_defense)).text = blueTeam.defensePlayer.name

        btnKickOff.isEnabled = false
        btnKickOff.setOnClickListener {
            presenter.startGame(redTeam, blueTeam, mode, modeValueLimit)
        }

        imgScoreLimit.setOnClickListener {
            mode = SCORE
            imgTimeLimit.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgScoreLimit.borderColor = ContextCompat.getColor(this, R.color.colorAccent)
            groupLimit.visibility = View.VISIBLE
        }
        imgTimeLimit.setOnClickListener {
            mode = TIME
            imgScoreLimit.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgTimeLimit.borderColor = ContextCompat.getColor(this, R.color.colorAccent)
            groupLimit.visibility = View.VISIBLE
        }

        imgLimitFive.setOnClickListener {
            modeValueLimit = 5
            imgLimitFive.borderColor = ContextCompat.getColor(this, R.color.colorAccent)
            imgLimitSeven.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgLimitTen.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            btnKickOff.isEnabled = true
        }

        imgLimitSeven.setOnClickListener {
            modeValueLimit = 7
            imgLimitFive.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgLimitSeven.borderColor = ContextCompat.getColor(this, R.color.colorAccent)
            imgLimitTen.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            btnKickOff.isEnabled = true
        }

        imgLimitTen.setOnClickListener {
            modeValueLimit = 10
            imgLimitFive.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgLimitSeven.borderColor = ContextCompat.getColor(this, R.color.colorBackground)
            imgLimitTen.borderColor = ContextCompat.getColor(this, R.color.colorAccent)
            btnKickOff.isEnabled = true
        }
    }

    override fun onGameStarted(game: GameModel) {
        val p1 = android.support.v4.util.Pair.create(imgRedPlayerAttack as View, "redAttackPlayer")
        val p2 = android.support.v4.util.Pair.create(imgRedPlayerDefense as View, "redDefensePlayer")
        val p3 = android.support.v4.util.Pair.create(imgBluePlayerAttack as View, "blueAttackPlayer")
        val p4 = android.support.v4.util.Pair.create(imgBluePlayerDefense as View, "blueDefensePlayer")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)

        navigator.navigateToGame(this, game, options)
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
}
