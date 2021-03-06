package io.funatwork.view.fragment


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.GameDataRepository
import io.funatwork.core.repository.datasource.game.GameDataStoreFactory
import io.funatwork.domain.interactor.GetGameList
import io.funatwork.domain.interactor.LoadCurrentGame
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.GameListPresenter
import io.funatwork.view.GameListView
import io.funatwork.view.adapter.GameAdapter
import io.funatwork.view.adapter.MultipleGameItem

class MainGameFragment : BaseFragment(), GameListView {

    init {
        retainInstance = true
    }

    private var recyclerGames: RecyclerView? = null

    val presenter by lazy {
        GameListPresenter(
                gameListView = this,
                getGameList = GetGameList(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor),
                loadCurrentGame = LoadCurrentGame(
                        gameRepository = GameDataRepository(
                                gameDataStoreFactory = GameDataStoreFactory(
                                        connectionUtils = ConnectionUtils(activity.getConnectivityManager())
                                )
                        ),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor
                )
        )
    }

    /**
     * Interface for listening user list events.
     */
    interface InitNewGame {
        fun onNewGame()
        fun onEditGame(game: GameModel, redAttack: View, redDefense: View, blueAttack: View, blueDefense: View)
    }


    private var initGameListener: InitNewGame? = null

    override fun onAttachToContext(context: Context?) {
        if (context is InitNewGame) {
            this.initGameListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val view = inflater?.inflate(R.layout.fragment_matchs, container, false)
        recyclerGames = view?.findViewById(R.id.rv_games)
        recyclerGames?.setHasFixedSize(true)
        recyclerGames?.layoutManager = LinearLayoutManager(activity)
        val adapter = GameAdapter(emptyList())
        recyclerGames?.adapter = adapter
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

    override fun renderCurrentGame(game: GameModel) {
        val currentView = view
        val flipperView = currentView.findViewById<ViewFlipper>(R.id.vf_current_match_header)
        val imgRedAttack = currentView.findViewById<ImageView>(R.id.img_player_red_attack)
        val imgRedDefense = currentView.findViewById<ImageView>(R.id.img_player_red_defense)
        val imgBlueAttack = currentView.findViewById<ImageView>(R.id.img_player_blue_attack)
        val imgBlueDefense = currentView.findViewById<ImageView>(R.id.img_player_blue_defense)
        val tvRedAttack = currentView.findViewById<TextView>(R.id.tv_player_red_attack_name)
        val tvRedDefense = currentView.findViewById<TextView>(R.id.tv_player_red_defense_name)
        val tvBlueAttack = currentView.findViewById<TextView>(R.id.tv_player_blue_attack_name)
        val tvBlueDefense = currentView.findViewById<TextView>(R.id.tv_player_blue_defense_name)
        val tvScoreRed = currentView.findViewById<TextView>(R.id.tv_score_red)
        val tvScoreBlue = currentView.findViewById<TextView>(R.id.tv_score_blue)
        Picasso.with(activity).load(game.redTeam.attackPlayer.avatar).into(imgRedAttack)
        Picasso.with(activity).load(game.redTeam.defensePlayer.avatar).into(imgRedDefense)
        Picasso.with(activity).load(game.blueTeam.attackPlayer.avatar).into(imgBlueAttack)
        Picasso.with(activity).load(game.blueTeam.defensePlayer.avatar).into(imgBlueDefense)
        tvScoreRed.text = game.redTeamGoal.toString()
        tvScoreBlue.text = game.blueTeamGoal.toString()
        tvRedAttack.text = game.redTeam.attackPlayer.name.take(10)
        tvRedDefense.text = game.redTeam.defensePlayer.name.take(10)
        tvBlueAttack.text = game.blueTeam.attackPlayer.name.take(10)
        tvBlueDefense.text = game.blueTeam.defensePlayer.name.take(10)

        val editBtn = currentView.findViewById<ImageView>(R.id.img_edit_game)
        if (userAuth.player.id == game.creator.id) {
            editBtn.setOnClickListener {
                initGameListener?.onEditGame(game, imgRedAttack, imgRedDefense, imgBlueAttack, imgBlueDefense)
            }
        } else {
            editBtn.visibility = View.GONE
        }
        flipperView.displayedChild = 1
    }

    override fun renderGameFinishedList(games: List<GameModel>) {
        if (games.isEmpty()) {
            (recyclerGames?.adapter as? GameAdapter)?.setEmptyView(R.layout.list_games_empty, recyclerGames?.parent as ViewGroup)
        } else {
            (recyclerGames?.adapter as? GameAdapter)?.let {
                it.setNewData(games.map { MultipleGameItem(game = it) })
                it.addHeaderView(LayoutInflater.from(context).inflate(R.layout.head_game_list, recyclerGames as ViewGroup, false))
            }
        }
    }

    override fun showLoadingCurrentGame() {
        // TODO Make a beautiful loading view ?
    }

    override fun hideLoadingCurrentGame() {
        // TODO Make a beautiful loading view ?
    }

    override fun showErrorCurrentGames(title: String, message: String) {
        val currentView = view
        val flipperView = currentView.findViewById<ViewFlipper>(R.id.vf_current_match_header)
        val btnStart = currentView.findViewById<Button>(R.id.btn_start_new_game)
        btnStart.setOnClickListener {
            initGameListener?.onNewGame()
        }
        flipperView.displayedChild = 0
    }

    override fun showLoadingGames() {
        (recyclerGames?.adapter as? GameAdapter)?.setEmptyView(R.layout.list_games_loading, recyclerGames?.parent as ViewGroup)
    }

    override fun hideLoadingGames() {
        // DO nothing, handle by renderGameFinishedList
    }

    override fun showErrorGames(title: String, message: String) {
        val errorView = LayoutInflater.from(context).inflate(R.layout.list_games_error, recyclerGames?.parent as ViewGroup, false)
        val errorMsg = errorView.findViewById<TextView>(R.id.tv_list_game_error_msg)
        errorMsg.text = "$title: $message"
        (recyclerGames?.adapter as? GameAdapter)?.emptyView = errorView
    }

    override fun onDetach() {
        super.onDetach()
        initGameListener = null
    }
}
