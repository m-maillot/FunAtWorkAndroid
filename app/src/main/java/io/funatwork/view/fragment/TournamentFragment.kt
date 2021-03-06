package io.funatwork.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.TournamentDataRepository
import io.funatwork.core.repository.datasource.tournament.TournamentDataStoreFactory
import io.funatwork.domain.interactor.GetCurrentTournament
import io.funatwork.domain.interactor.StartTournamentGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.model.babyfoot.TournamentModel
import io.funatwork.presenter.TournamentPresenter
import io.funatwork.view.TournamentView
import io.funatwork.view.adapter.TournamentGameAdapter
import io.funatwork.view.adapter.item.Game
import io.funatwork.view.adapter.item.GameItem
import io.funatwork.view.adapter.item.Header


class TournamentFragment : BaseFragment(), TournamentView {

    init {
        retainInstance = true
    }

    private var recyclerGames: RecyclerView? = null
    private var tvTounamentTitle: TextView? = null


    val adapter = TournamentGameAdapter(emptyList())

    /**
     * Interface for listening when start a game
     */
    interface StartGame {
        fun startGame(game: GameModel)
    }

    private var startGameListener: StartGame? = null

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
                        threadExecutor = fwtApplication.jobExecutor),
                startTournamentGame = StartTournamentGame(
                        tournamentRepository = TournamentDataRepository(
                                tournamentDataStoreFactory = TournamentDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor
                )
        )
    }

    override fun onAttachToContext(context: Context?) {
        if (context is StartGame) {
            this.startGameListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val view = inflater?.inflate(R.layout.fragment_tournament, container, false)
        tvTounamentTitle = view?.findViewById(R.id.tv_tournament_title)
        recyclerGames = view?.findViewById(R.id.rv_tournament_games)
        recyclerGames?.setHasFixedSize(true)
        recyclerGames?.layoutManager = LinearLayoutManager(activity)
        recyclerGames?.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.initialize()
            adapter.openLoadAnimation()
        }
    }

    override fun onDetach() {
        super.onDetach()
        startGameListener = null
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
        tvTounamentTitle?.text = tournament.name
        (recyclerGames?.adapter as? TournamentGameAdapter)?.let {
            it.setNewData(createTournament(tournament))
            it.setOnItemChildClickListener { _, _, position ->
                val item = it.data[position] as Game
                presenter.startGame(item.game)
            }
        }
    }

    override fun startGame(game: GameModel) {
        startGameListener?.startGame(game)
    }

    private fun createTournament(tournament: TournamentModel): List<GameItem> {
        val element = mutableListOf<GameItem>()
        var currentRound = -1
        tournament.rounds.forEach({
            if (currentRound != it.index) {
                element.add(Header(it.index, tournament.rounds.size - it.index))
                currentRound = it.index
            }
            element.addAll(it.games.map { Game(it) })
        })
        return element.toList()
    }

    override fun showLoadingTournament() {
        adapter.setEmptyView(R.layout.list_games_loading, recyclerGames?.parent as ViewGroup)
    }

    override fun showErrorLoadingTournament() {
        val errorView = LayoutInflater.from(context).inflate(R.layout.list_games_error_tournament, recyclerGames?.parent as ViewGroup, false)
        adapter.emptyView = errorView
    }
}