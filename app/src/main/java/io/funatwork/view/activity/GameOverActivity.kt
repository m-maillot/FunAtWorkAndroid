package io.funatwork.view.activity

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.utils.CircleTransformation
import xyz.hanks.library.SmallBang

class GameOverActivity : BaseActivity() {

    val restart by lazy {
        findViewById(R.id.btn_new_game) as Button
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

    val imgTrophy by lazy {
        findViewById(R.id.img_trophy) as ImageView
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

        supportActionBar?.title = getString(R.string.game_over_title)

        val game = intent.extras.getSerializable("GAME") as GameModel
        Picasso.with(this).load(game.blueTeam.attackPlayer.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerAttack)
        Picasso.with(this).load(game.blueTeam.defensePlayer.avatar).fit().transform(CircleTransformation()).into(imgBluePlayerDefense)
        Picasso.with(this).load(game.redTeam.attackPlayer.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerAttack)
        Picasso.with(this).load(game.redTeam.defensePlayer.avatar).fit().transform(CircleTransformation()).into(imgRedPlayerDefense)

        tvScoreBlue.text = game.blueTeamGoal.toString()
        tvScoreRed.text = game.redTeamGoal.toString()

        restart.setOnClickListener {
            navigator.navigateToCreateGame(this, FLAG_ACTIVITY_CLEAR_TOP)
        }
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        val smallBang = SmallBang.attach2Window(this)
        smallBang.bang(imgTrophy)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigator.navigateToHome(this, FLAG_ACTIVITY_CLEAR_TOP)
    }
}
