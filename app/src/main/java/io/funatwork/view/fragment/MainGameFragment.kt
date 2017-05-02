package io.funatwork.view.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.funatwork.R

class MainGameFragment : BaseFragment() {

    init {
        retainInstance = true
    }

    /**
     * Interface for listening user list events.
     */
    interface InitNewGame {
        fun onNewGame()
    }


    private var initGameListener: InitNewGame? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is InitNewGame) {
            this.initGameListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_main_game, container, false)
        view?.findViewById(R.id.btn_init_game)?.setOnClickListener {
            initGameListener?.onNewGame()
        }
        return view
    }


    override fun onDetach() {
        super.onDetach()
        initGameListener = null
    }
}
