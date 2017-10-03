package io.funatwork.view.fragment

import android.os.Bundle
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
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.UserAuthModel
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
                        threadExecutor = fwtApplication.jobExecutor)
        )
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
            presenter.load()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.presenter.destroy()
    }


    override fun onDetach() {
        super.onDetach()
    }

    override fun renderAccount(userAuth: UserAuthModel) {
        val currentView = view
        if (currentView != null) {
            val avatar = currentView.findViewById<CircleImageView>(R.id.img_account_player_avatar)
            val name = currentView.findViewById<TextView>(R.id.tv_account_player_name)
            Picasso.with(context).load(userAuth.player.avatar).into(avatar)
            name.text = "${userAuth.player.name} ${userAuth.player.surname}"
        }
    }
}