package io.funatwork.view.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.HistoryPresenter
import io.funatwork.view.HistoryView
import io.funatwork.view.adapter.GameAdapter

class HistoryActivity : BaseActivity(), HistoryView {

    val recyclerGames by lazy {
        findViewById(R.id.rv_games) as RecyclerView
    }

    val presenter by lazy {
        HistoryPresenter(
                historyView = this,
                getGameList = GetGameList(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = getString(R.string.history_title)

        recyclerGames.setHasFixedSize(true)
        recyclerGames.layoutManager = LinearLayoutManager(this)

        bottomNavigationView?.selectedItemId = R.id.action_history

        presenter.initialize()
    }

    override fun renderGameList(games: List<GameModel>) {
        recyclerGames.adapter = GameAdapter(this, games, presenter)
    }
}
