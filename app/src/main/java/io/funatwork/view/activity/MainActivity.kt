package io.funatwork.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import io.funatwork.R
import io.funatwork.extensions.addFragment
import io.funatwork.view.fragment.HistoryFragment
import io.funatwork.view.fragment.MainGameFragment

class MainActivity : BaseActivity(), MainGameFragment.InitNewGame {

    val bottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView?
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
        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_play -> {
                    if (bottomNavigationView?.selectedItemId != R.id.action_play) {
                        supportActionBar?.title = getString(R.string.game_title)
                        addFragment(R.id.fragmentContainer, MainGameFragment())
                    }
                }
                R.id.action_history -> {
                    if (bottomNavigationView?.selectedItemId != R.id.action_history) {
                        supportActionBar?.title = getString(R.string.history_title)
                        addFragment(R.id.fragmentContainer, HistoryFragment())
                    }
                }
                R.id.action_stats -> {
                    if (bottomNavigationView?.selectedItemId != R.id.action_stats) {

                    }
                }
            }
            true
        }
    }

    override fun onNewGame() =
            navigator.navigateToCreateGame(this)
}
