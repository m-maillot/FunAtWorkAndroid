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

class MainActivity : BaseActivity(), MainGameFragment.InitNewGame {

    private val bottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottom_navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        supportActionBar?.hide()
        supportActionBar?.title = getString(R.string.game_title)
        addFragment(R.id.fragmentContainer, MainGameFragment())
    }

    override fun onStart() {
        super.onStart()
        initTab()
    }

    private fun initTab() {
        val menuNav = bottomNavigationView.menu
        menuNav.findItem(R.id.action_account).isEnabled = true
        menuNav.findItem(R.id.action_standings).isEnabled = true
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_play -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_play) {
                        supportActionBar?.hide()
                        supportActionBar?.title = getString(R.string.game_title)
                        addFragment(R.id.fragmentContainer, MainGameFragment())
                    }
                }
                R.id.action_standings -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_standings) {
                        supportActionBar?.show()
                        supportActionBar?.title = getString(R.string.standings_title)
                        addFragment(R.id.fragmentContainer, StandingsFragment())
                    }
                }
                R.id.action_account -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_account) {
                        supportActionBar?.show()
                        supportActionBar?.title = getString(R.string.account_title)
                        addFragment(R.id.fragmentContainer, AccountFragment())
                    }
                }
                R.id.action_tournament -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_tournament) {
                        supportActionBar?.show()
                        supportActionBar?.title = getString(R.string.tournament_title)
                        addFragment(R.id.fragmentContainer, TournamentFragment())
                    }
                }
            }
            true
        }
    }

    override fun onNewGame() =
            navigator.navigateToCreateGame(this)

    override fun onEditGame(game: GameModel, redAttack: View, redDefense: View, blueAttack: View, blueDefense: View) =
            navigator.navigateToGame(this, game, generateTransition(redAttack, redDefense, blueAttack, blueDefense))


    private fun generateTransition(redAttack: View, redDefense: View, blueAttack: View, blueDefense: View): ActivityOptionsCompat {
        val p1 = create(redAttack, "redAttackPlayer")
        val p2 = create(redDefense, "redDefensePlayer")
        val p3 = create(blueAttack, "blueAttackPlayer")
        val p4 = create(blueDefense, "blueDefensePlayer")
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)
    }
}