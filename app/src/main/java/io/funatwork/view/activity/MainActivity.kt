package io.funatwork.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import io.funatwork.R
import io.funatwork.extensions.addFragment
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.view.fragment.HistoryFragment
import io.funatwork.view.fragment.MainGameFragment

class MainActivity : BaseActivity(), MainGameFragment.InitNewGame {

    val bottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
        supportActionBar?.title = getString(R.string.game_title)
        addFragment(R.id.fragmentContainer, MainGameFragment())
    }

    override fun onStart() {
        super.onStart()
        initTab()
    }

    fun initTab() {
        val menuNav = bottomNavigationView.menu
        menuNav.findItem(R.id.action_stats).isEnabled = false
        menuNav.findItem(R.id.action_history).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_play -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_play) {
                        supportActionBar?.title = getString(R.string.game_title)
                        addFragment(R.id.fragmentContainer, MainGameFragment())
                    }
                }
                R.id.action_history -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_history) {
                        supportActionBar?.title = getString(R.string.history_title)
                        addFragment(R.id.fragmentContainer, HistoryFragment())
                    }
                }
                R.id.action_stats -> {
                    if (bottomNavigationView.selectedItemId != R.id.action_stats) {

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


    fun generateTransition(redAttack: View, redDefense: View, blueAttack: View, blueDefense: View): ActivityOptionsCompat {
        val p1 = android.support.v4.util.Pair.create(redAttack, "redAttackPlayer")
        val p2 = android.support.v4.util.Pair.create(redDefense, "redDefensePlayer")
        val p3 = android.support.v4.util.Pair.create(blueAttack, "blueAttackPlayer")
        val p4 = android.support.v4.util.Pair.create(blueDefense, "blueDefensePlayer")
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3, p4)
    }
}