package io.funatwork.view.activity

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.model.babyfoot.GameModel
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class GameOverActivity : BaseActivity() {

    private val btnRestart by lazy {
        findViewById<Button>(R.id.btn_new_game)
    }

    private val btnDone by lazy {
        findViewById<Button>(R.id.btn_done)
    }

    private val imgWinnerPlayerAttack by lazy {
        findViewById<CircleImageView>(R.id.img_player_winner_attack)
    }
    private val imgWinnerPlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_winner_defense)
    }
    private val imgLooserPlayerAttack by lazy {
        findViewById<CircleImageView>(R.id.img_player_looser_attack)
    }
    private val imgLooserPlayerDefense by lazy {
        findViewById<CircleImageView>(R.id.img_player_looser_defense)
    }

    private val viewKonfetti by lazy {
        findViewById<KonfettiView>(R.id.viewKonfetti)
    }

    private val tvScoreWinner by lazy {
        findViewById<TextView>(R.id.tv_score_winner)
    }
    private val tvScoreLooser by lazy {
        findViewById<TextView>(R.id.tv_score_looser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)


        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.title = getString(R.string.game_over_title)
        }

        val game = intent.extras.getSerializable("GAME") as GameModel

        val winnerColorTeam = if (game.isRedTeamWin()) getColor(this, R.color.colorRedTeam5) else getColor(this, R.color.colorBlueTeam5)
        val looserColorTeam = if (game.isRedTeamWin()) getColor(this, R.color.colorBlueTeam5) else getColor(this, R.color.colorRedTeam5)
        imgWinnerPlayerAttack.borderColor = winnerColorTeam
        imgWinnerPlayerDefense.borderColor = winnerColorTeam
        imgLooserPlayerAttack.borderColor = looserColorTeam
        imgLooserPlayerDefense.borderColor = looserColorTeam
        Picasso.with(this).load(game.winnerTeam.attackPlayer.avatar).into(imgWinnerPlayerAttack)
        Picasso.with(this).load(game.winnerTeam.defensePlayer.avatar).into(imgWinnerPlayerDefense)
        Picasso.with(this).load(game.looserTeam.attackPlayer.avatar).into(imgLooserPlayerAttack)
        Picasso.with(this).load(game.looserTeam.defensePlayer.avatar).into(imgLooserPlayerDefense)

        tvScoreWinner.text = game.winnerScore.toString()
        tvScoreLooser.text = game.looserScore.toString()

        if (game.tournamentId >= 0) {
            btnRestart.visibility = View.GONE
            btnDone.setOnClickListener {
                navigator.navigateToTournament(this, FLAG_ACTIVITY_CLEAR_TOP)
            }
        } else {
            btnRestart.setOnClickListener {
                navigator.navigateToMain(this, FLAG_ACTIVITY_CLEAR_TOP)
            }

            btnDone.setOnClickListener {
                navigator.navigateToCreateGame(this, FLAG_ACTIVITY_CLEAR_TOP, game.redTeam, game.blueTeam)
            }
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        viewKonfetti.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(5f, 10f)
                .setFadeOutEnabled(true)
                .setTimeToLive(5000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(Size(12))
                .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                .stream(300, 5000L)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.navigateToHome(this, FLAG_ACTIVITY_CLEAR_TOP)
    }
}
