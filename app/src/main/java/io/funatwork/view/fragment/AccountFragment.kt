package io.funatwork.view.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.funatwork.R
import io.funatwork.core.cache.AccountCacheImpl
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.AccountDataRepository
import io.funatwork.core.repository.StatsDataRepository
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.core.repository.datasource.stats.StatsDataStoreFactory
import io.funatwork.domain.interactor.GetPlayerStats
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.UserAuthModel
import io.funatwork.model.babyfoot.PlayerStatsModel
import io.funatwork.presenter.AccountPresenter
import io.funatwork.view.AccountView

class AccountFragment : BaseFragment(), AccountView {

    init {
        retainInstance = true
    }

    val presenter by lazy {
        AccountPresenter(
                accountView = this,
                getUserAuth = GetUserAuth(
                        accountRepository = AccountDataRepository(accountDataStoreFactory =
                        AccountDataStoreFactory(
                                connectionUtils = ConnectionUtils(activity.getConnectivityManager()),
                                accountCache = AccountCacheImpl(
                                        cacheDir = context.cacheDir,
                                        fileManager = FileManager(),
                                        serializer = Serializer()
                                ))),
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

    private var imgPlayerAvatar: CircleImageView? = null
    private var tvPlayerName: TextView? = null
    private var tvPlayerRank: TextView? = null
    private var tvPlayerRankExp: TextView? = null
    private var tvPlayerElo: TextView? = null

    private var tvPlayerGames: TextView? = null
    private var tvPlayerGoals: TextView? = null
    private var tvPlayerWins: TextView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val view = inflater?.inflate(R.layout.fragment_account, container, false)
        imgPlayerAvatar = view?.findViewById(R.id.img_account_player_avatar)
        tvPlayerName = view?.findViewById(R.id.tv_account_player_name)
        tvPlayerRank = view?.findViewById(R.id.tv_account_player_rank)
        tvPlayerRankExp = view?.findViewById(R.id.tv_account_player_rank_exponent)
        tvPlayerElo = view?.findViewById(R.id.tv_account_player_elo)
        tvPlayerGames = view?.findViewById(R.id.tv_account_player_games)
        tvPlayerGoals = view?.findViewById(R.id.tv_account_player_goals)
        tvPlayerWins = view?.findViewById(R.id.tv_account_player_wins)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.load()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.destroy()
    }

    override fun renderAccount(userAuth: UserAuthModel) {
        Picasso.with(context).load(userAuth.player.avatar).into(imgPlayerAvatar)
        tvPlayerName?.text = "${userAuth.player.name} ${userAuth.player.surname}"
        presenter.loadStats(userAuth.player.id)
    }

    override fun renderPlayerStats(playerStats: PlayerStatsModel) {
        if (playerStats.rank > 0) {
            tvPlayerRank?.text = playerStats.rank.toString()
            tvPlayerRankExp?.text = getExp(playerStats.rank)
        }
        tvPlayerElo?.text = playerStats.eloRanking.toString()
        tvPlayerGames?.text = playerStats.gamePlayed.toString()
        tvPlayerGoals?.text = playerStats.goals.toString()
        if (playerStats.gamePlayed > 0) {
            if (playerStats.gamePlayed - playerStats.loose > 0) {
                val winGames = playerStats.gamePlayed - playerStats.loose
                tvPlayerWins?.text = "${winGames * 100 / playerStats.gamePlayed}%"
            } else {
                tvPlayerWins?.text = "100%"
            }
        } else {
            tvPlayerWins?.text = "-%"
        }
    }

    private fun getExp(number: Int) =
            when (number) {
                in 4..20 -> "th"
                else -> when (number % 10) {
                    1 -> "st"
                    2 -> "nd"
                    3 -> "rd"
                    else -> "th"
                }
            }
}