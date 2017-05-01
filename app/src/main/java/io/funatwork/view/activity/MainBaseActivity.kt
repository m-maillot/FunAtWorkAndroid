package io.funatwork.view.activity

import android.support.design.widget.BottomNavigationView
import io.funatwork.R
import io.funatwork.SplashscreenActivity
import io.funatwork.navigation.Navigator


/**
 * Created by mmaillot on 3/29/17.
 */
abstract class MainBaseActivity : BaseActivity() {

    val bottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView?
    }

    val navigator = Navigator()

    override fun onStart() {
        super.onStart()
        initTab()
    }

    fun initTab() {
        bottomNavigationView?.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_play -> {
                    if (this !is SplashscreenActivity) {
                        navigator.navigateToPlay(this)
                    }
                }
                R.id.action_history -> {
                    if (this !is HistoryActivity) {
                        navigator.navigateToHistory(this)
                    }
                }
                R.id.action_stats -> {
                }
            }
            false
        }
    }
}