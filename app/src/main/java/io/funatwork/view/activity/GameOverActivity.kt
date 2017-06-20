package io.funatwork.view.activity

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.os.Bundle
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

    val restart by lazy {
        findViewById(R.id.btn_new_game) as Button
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

    val viewKonfetti by lazy {
        findViewById(R.id.viewKonfetti) as KonfettiView
    }

    val tvScoreRed by lazy {
        findViewById(R.id.tv_score_red) as TextView
    }
    val tvScoreBlue by lazy {
        findViewById(R.id.tv_score_blue) as TextView
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
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).into(imgBluePlayerAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).into(imgBluePlayerDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).into(imgRedPlayerAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).into(imgRedPlayerDefense)

        tvScoreBlue.text = game.blueTeamGoal.toString()
        tvScoreRed.text = game.redTeamGoal.toString()

        restart.setOnClickListener {
            navigator.navigateToMain(this, FLAG_ACTIVITY_CLEAR_TOP)
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
