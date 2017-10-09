package io.funatwork.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.StatsDataRepository
import io.funatwork.core.repository.datasource.stats.StatsDataStoreFactory
import io.funatwork.domain.interactor.GetPlayersStats
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
    private var imgFirst: CircleImageView? = null
    private var tvFirstName: TextView? = null
    private var tvFirstScore: TextView? = null
    private var imgSecond: CircleImageView? = null
    private var tvSecondName: TextView? = null
    private var tvSecondScore: TextView? = null
    private var imgThird: CircleImageView? = null
    private var tvThirdName: TextView? = null
    private var tvThirdScore: TextView? = null

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
                getPlayersStats = GetPlayersStats(
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
        imgFirst = view?.findViewById(R.id.img_player_first)
        imgSecond = view?.findViewById(R.id.img_player_second)
        imgThird = view?.findViewById(R.id.img_player_third)
        tvFirstName = view?.findViewById(R.id.tv_name_first)
        tvSecondName = view?.findViewById(R.id.tv_name_second)
        tvThirdName = view?.findViewById(R.id.tv_name_third)
        tvFirstScore = view?.findViewById(R.id.tv_score_first)
        tvSecondScore = view?.findViewById(R.id.tv_score_second)
        tvThirdScore = view?.findViewById(R.id.tv_score_third)
        recyclerGames = view?.findViewById(R.id.rv_standings)
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
        val first = playerStatsList.getOrNull(0)
        val second = playerStatsList.getOrNull(1)
        val third = playerStatsList.getOrNull(2)
        first?.let {
            tvFirstName?.text = getString(R.string.standings_first_name, "${it.player.name} ${it.player.surname.substring(0,1)}.")
            tvFirstScore?.text = (Math.round(it.eloRanking * 100.0) / 100.0).toString()
            Picasso.with(context).load(it.player.avatar).into(imgFirst)
        }
        second?.let {
            tvSecondName?.text = getString(R.string.standings_second_name, "${it.player.name} ${it.player.surname.substring(0,1)}.")
            tvSecondScore?.text = (Math.round(it.eloRanking * 100.0) / 100.0).toString()
            Picasso.with(context).load(it.player.avatar).into(imgSecond)
        }
        third?.let {
            tvThirdName?.text = getString(R.string.standings_third_name, "${it.player.name} ${it.player.surname.substring(0,1)}.")
            tvThirdScore?.text = (Math.round(it.eloRanking * 100.0) / 100.0).toString()
            Picasso.with(context).load(it.player.avatar).into(imgThird)
        }
        recyclerGames?.adapter = PlayerStatsAdapter(activity, playerStatsList.subList(3, playerStatsList.size))
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