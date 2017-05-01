package io.funatwork.view.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.funatwork.R
import io.funatwork.view.StartGameView

class GameFragment : Fragment(), StartGameView {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_main_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        this.userDetailsPresenter.setView(this)
        if (savedInstanceState == null) {
            this.loadUserDetails()
        }
        */
    }
}
