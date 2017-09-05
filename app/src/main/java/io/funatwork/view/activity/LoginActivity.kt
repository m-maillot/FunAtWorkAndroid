package io.funatwork.view.activity

import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import io.funatwork.R
import io.funatwork.core.cache.AccountCacheImpl
import io.funatwork.core.cache.FileManager
import io.funatwork.core.cache.serializer.Serializer
import io.funatwork.core.net.ConnectionUtils
import io.funatwork.core.repository.AccountDataRepository
import io.funatwork.core.repository.datasource.login.AccountDataStoreFactory
import io.funatwork.domain.interactor.Signin
import io.funatwork.extensions.getConnectivityManager
import io.funatwork.model.UserAuthModel
import io.funatwork.presenter.LoginPresenter
import io.funatwork.view.LoginView

class LoginActivity : BaseActivity(), LoginView {

    val presenter by lazy {
        LoginPresenter(
                loginView = this,
                signin = Signin(
                        accountRepository = AccountDataRepository(
                                accountDataStoreFactory = AccountDataStoreFactory(
                                        connectionUtils = ConnectionUtils(this.getConnectivityManager()),
                                        accountCache = AccountCacheImpl(
                                                cacheDir = cacheDir,
                                                fileManager = FileManager(),
                                                serializer = Serializer()
                                        ))
                        ),
                        threadExecutor = fwtApplication.jobExecutor,
                        postExecutionThread = fwtApplication.uiThread)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btn_signin).setOnClickListener({
            val login = findViewById<EditText>(R.id.et_login).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            presenter.signin(login, password)
        })
    }

    override fun onLoginFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoginSuccessful(userAuthModel: UserAuthModel) {
        fwtApplication.userAuth = userAuthModel
        navigator.navigateToHome(this, FLAG_ACTIVITY_CLEAR_TOP)
    }

}
