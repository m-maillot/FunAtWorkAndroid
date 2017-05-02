package io.funatwork.view.fragment

import android.app.Fragment
import io.funatwork.FwtApplication
import io.funatwork.view.LoadDataView
import io.funatwork.view.activity.BaseActivity

abstract class BaseFragment : Fragment(), LoadDataView {

    val fwtApplication by lazy {
        activity.application as FwtApplication
    }

    val baseActivity by lazy {
        activity as BaseActivity
    }

    override fun showLoading() =
            baseActivity.showLoading()

    override fun hideLoading() =
            baseActivity.hideLoading()

    override fun showError(title: String, message: String) =
            baseActivity.showError(title, message)
}