package io.funatwork.view.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import io.funatwork.R
import io.funatwork.SplashscreenActivity
import io.funatwork.navigation.Navigator

class MainActivity : BaseActivity() {

    val bottomNavigationView by lazy {
        findViewById(R.id.bottom_navigation) as BottomNavigationView?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)
    }

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
