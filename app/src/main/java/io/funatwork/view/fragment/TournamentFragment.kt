package io.funatwork.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.TournamentDataRepository
import io.funatwork.core.repository.datasource.tournament.TournamentDataStoreFactory
import io.funatwork.domain.interactor.GetCurrentTournament
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.TournamentModel
import io.funatwork.presenter.TournamentPresenter
import io.funatwork.view.TournamentView
import io.funatwork.view.adapter.GameAdapter

class TournamentFragment : BaseFragment(), TournamentView {

    init {
        retainInstance = true
    }

    private var recyclerGames: RecyclerView? = null

    val presenter by lazy {
        TournamentPresenter(
                tournamentView = this,
                getCurrentTournament = GetCurrentTournament(
                        tournamentRepository = TournamentDataRepository(
                                tournamentDataStoreFactory = TournamentDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor)
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val view = inflater?.inflate(R.layout.fragment_tournament, container, false)
        recyclerGames = view?.findViewById(R.id.rv_tournament_games)
        recyclerGames?.setHasFixedSize(true)
        recyclerGames?.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.initialize()
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

    override fun renderCurrentTournament(tournament: TournamentModel) {
        val adapter = GameAdapter(tournament.rounds.flatMap { it.games })
        recyclerGames?.adapter = adapter
        adapter.addHeaderView((context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.head_game_list, recyclerGames as ViewGroup, false))
    }


}