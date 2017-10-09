package io.funatwork.view.activity

import android.os.Bundle
import io.funatwork.core.cache.AccountCacheImpl
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.AccountDataRepository
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.domain.interactor.GetUserAuth
import io.funatwork.domain.model.UserAuth
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.UserAuthModel
import io.funatwork.presenter.SplashScreenPresenter
import io.funatwork.view.SplashScreenView

class SplashScreenActivity : BaseActivity(), SplashScreenView {

    val presenter by lazy {
        SplashScreenPresenter(
                splashScreenView = this,
                getUserAuth = GetUserAuth(
                        accountRepository = AccountDataRepository(accountDataStoreFactory =
                        AccountDataStoreFactory(
                                connectionUtils = ConnectionUtils(getConnectivityManager()),
                                accountCache = AccountCacheImpl(
                                        cacheDir = cacheDir,
                                        fileManager = FileManager(),
                                        serializer = Serializer()
                                ))),
                        postExecutionThread = fwtApplication.uiThread,
                        threadExecutor = fwtApplication.jobExecutor)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.checkLogin()
    }

    override fun goToLoginScreen() {
        navigator.navigateToLogin(this)
        finish()
    }

    override fun goToMainScreen(userAuth: UserAuthModel) {
        fwtApplication.connectedUser = userAuth
        navigator.navigateToMain(this)
        finish()
    }

}
