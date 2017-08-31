package io.funatwork.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ViewFlipper
import io.funatwork.R
import io.funatwork.core.cache.AccountCacheImpl
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.AccountDataRepository
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.domain.interactor.Signin
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
                        threadExecutor = fwtApplication.jobExecutor),
                signin = Signin(
                        accountRepository = AccountDataRepository(accountDataStoreFactory =
                        AccountDataStoreFactory(
                                connectionUtils = ConnectionUtils(activity.getConnectivityManager()),
                                accountCache = AccountCacheImpl(
                                        cacheDir = context.cacheDir,
                                        fileManager = FileManager(),
                                        serializer = Serializer()
                                ))),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor
                )
        )
    }

    private var signing: Button? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_account, container, false)
        signing = view?.findViewById(R.id.btn_signin)
        signing?.setOnClickListener {
            val login = view?.findViewById<EditText>(R.id.et_login)?.text?.toString() ?: ""
            val password = view?.findViewById<EditText>(R.id.et_password)?.text?.toString() ?: ""
            presenter.signin(login, password)
        }
        return view
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

    override fun renderNotConnected() {
        val currentView = view
        if (currentView != null) {
            val flipperView = currentView.findViewById<ViewFlipper>(R.id.vf_account_status)
            flipperView.displayedChild = 0
        }
    }

    override fun renderAccount(userAuth: UserAuthModel) {
        val currentView = view
        if (currentView != null) {
            val flipperView = currentView.findViewById<ViewFlipper>(R.id.vf_account_status)
            flipperView.displayedChild = 1
        }
    }
}