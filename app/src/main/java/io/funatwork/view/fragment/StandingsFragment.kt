package io.funatwork.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.StatsDataRepository
import io.funatwork.core.repository.datasource.stats.StatsDataStoreFactory
import io.funatwork.domain.interactor.GetPlayerStats
import io.funatwork.domain.interactor.GetTeamStats
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.PlayerStatsModel
import io.funatwork.model.babyfoot.TeamStatsModel
import io.funatwork.presenter.StandingsPresenter
import io.funatwork.view.StandingsView
import io.funatwork.view.adapter.PlayerStatsAdapter
import io.funatwork.view.adapter.TeamStatsAdapter

class StandingsFragment : BaseFragment(), StandingsView {

    init {
        retainInstance = true
    }

    private var recyclerGames: RecyclerView? = null

    val presenter by lazy {
        StandingsPresenter(
                standingsView = this,
                getTeamStats = GetTeamStats(
                        statsRepository = StatsDataRepository(
                                statsDataStoreFactory = StatsDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor),
                getPlayerStats = GetPlayerStats(
                        statsRepository = StatsDataRepository(
                                statsDataStoreFactory = StatsDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor
                )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_standings, container, false)
        recyclerGames = view?.findViewById<RecyclerView>(R.id.rv_standings)
        recyclerGames?.setHasFixedSize(true)
        recyclerGames?.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.loadPlayerStats()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recyclerGames?.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.destroy()
    }


    override fun onDetach() {
        super.onDetach()
    }

    override fun renderTeamStats(teamStatsList: List<TeamStatsModel>) {
        recyclerGames?.adapter = TeamStatsAdapter(activity, teamStatsList)
    }

    override fun renderPlayerStats(playerStatsList: List<PlayerStatsModel>) {
        recyclerGames?.adapter = PlayerStatsAdapter(activity, playerStatsList)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.standings_player_team_selector, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_player -> this.presenter.loadPlayerStats()
            R.id.action_team -> this.presenter.loadTeamStats()
        }
        return super.onOptionsItemSelected(item)
    }
}