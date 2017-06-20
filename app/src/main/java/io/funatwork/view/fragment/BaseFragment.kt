package io.funatwork.view.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Build
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

    override fun getContext(): Context =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                super.getContext() else super.getActivity()

    // Here for old device support
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity as Context)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onAttachToContext(context)
    }

    open fun onAttachToContext(context: Context?) {

    }
}