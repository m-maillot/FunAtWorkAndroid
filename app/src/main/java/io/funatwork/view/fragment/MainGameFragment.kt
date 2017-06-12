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
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.presenter.GameListPresenter
import io.funatwork.view.GameListView
import io.funatwork.view.adapter.GameAdapter

class MainGameFragment : BaseFragment(), GameListView, GameAdapter.OnItemClickListener {

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
                        threadExecutor = fwtApplication.jobExecutor)
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
        recyclerGames = view?.findViewById(R.id.rv_games) as RecyclerView
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

    override fun renderCurrentGame(game: GameModel?) {
        val currentView = view
        if (currentView != null) {
            val flipperView = currentView.findViewById(R.id.vf_current_match_header) as ViewFlipper
            if (game != null) {
                val imgRedAttack = currentView.findViewById(R.id.img_player_red_attack) as ImageView
                val imgRedDefense = currentView.findViewById(R.id.img_player_red_defense) as ImageView
                val imgBlueAttack = currentView.findViewById(R.id.img_player_blue_attack) as ImageView
                val imgBlueDefense = currentView.findViewById(R.id.img_player_blue_defense) as ImageView
                val tvRedAttack = currentView.findViewById(R.id.tv_player_red_attack_name) as TextView
                val tvRedDefense = currentView.findViewById(R.id.tv_player_red_defense_name) as TextView
                val tvBlueAttack = currentView.findViewById(R.id.tv_player_blue_attack_name) as TextView
                val tvBlueDefense = currentView.findViewById(R.id.tv_player_blue_defense_name) as TextView
                val tvScoreRed = currentView.findViewById(R.id.tv_score_red) as TextView
                val tvScoreBlue = currentView.findViewById(R.id.tv_score_blue) as TextView
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

                val editBtn = currentView.findViewById(R.id.img_edit_game) as ImageView
                editBtn.setOnClickListener {
                    initGameListener?.onEditGame(game, imgRedAttack, imgRedDefense, imgBlueAttack, imgBlueDefense)
                }
                flipperView.displayedChild = 0
            } else {
                val btnStart = currentView.findViewById(R.id.btn_start_new_game) as Button
                btnStart.setOnClickListener {
                    initGameListener?.onNewGame()
                }
                flipperView.displayedChild = 1
            }
        }
    }

    override fun renderGameFinishedList(games: List<GameModel>) {
        recyclerGames?.adapter = GameAdapter(activity, games, this)
    }

    override fun onDetach() {
        super.onDetach()
        initGameListener = null
    }

    override fun onGameItemClicked(game: GameModel) {
        // TODO not implemented
    }
}
