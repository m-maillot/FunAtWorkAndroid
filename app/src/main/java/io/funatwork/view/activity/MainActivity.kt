package io.funatwork.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair.create
import android.view.View
import io.funatwork.R
import io.funatwork.extensions.addFragment
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.view.fragment.AccountFragment
import io.funatwork.view.fragment.MainGameFragment
import io.funatwork.view.fragment.StandingsFragment
import io.funatwork.view.fragment.TournamentFragment

class MainActivity : BaseActivity(), MainGameFragment.InitNewGame, TournamentFragment.StartGame {

    private val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        supportActionBar?.hide()
        supportActionBar?.title = getString(R.string.game_title)
        val action = intent?.extras?.getInt("SECTION", R.id.action_play) ?: R.id.action_play
        loadFragment(action, true)
    }

    override fun onStart() {
        super.onStart()
        initTab()
    }

    private fun initTab() {
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            loadFragment(item.itemId, false)
            true
        }
    }

    private fun loadFragment(action: Int, forced: Boolean) {
        when (action) {
            R.id.action_play -> {
                if (forced || bottomNavigationView.selectedItemId != R.id.action_play) {
                    supportActionBar?.title = getString(R.string.game_title)
                    addFragment(R.id.fragmentContainer, MainGameFragment())
                }
            }
            R.id.action_standings -> {
                if (forced || bottomNavigationView.selectedItemId != R.id.action_standings) {
                    supportActionBar?.title = getString(R.string.standings_title)
                    addFragment(R.id.fragmentContainer, StandingsFragment())
                }
            }
            R.id.action_account -> {
                if (forced || bottomNavigationView.selectedItemId != R.id.action_account) {
                    supportActionBar?.title = getString(R.string.account_title)
                    addFragment(R.id.fragmentContainer, AccountFragment())
                }
            }
            R.id.action_tournament -> {
                if (forced || bottomNavigationView.selectedItemId != R.id.action_tournament) {
                    supportActionBar?.title = getString(R.string.tournament_title)
                    addFragment(R.id.fragmentContainer, TournamentFragment())
                }
            }
        }
    }

    override fun onNewGame() =
            navigator.navigateToCreateGame(this)

    override fun onEditGame(game: GameModel, redAttack: View, redDefense: View, blueAttack: View, blueDefense: View) =
            navigator.navigateToGame(this, game, generateTransition(redAttack, redDefense, blueAttack, blueDefense))

    override fun startGame(game: GameModel) =
            navigator.navigateToGame(this, game)

    private fun generateTransition(redAttack: View, redDefense: View, blueAttack: View, blueDefense: View): ActivityOptionsCompat {
        val p1 = create(redAttack, "redAttackPlayer")
        val p2 = create(redDefense, "redDefensePlayer")
        val p3 = create(blueAttack, "blueAttackPlayer")
        val p4 = create(blueDefense, "blueDefensePlayer")
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)
    }
}