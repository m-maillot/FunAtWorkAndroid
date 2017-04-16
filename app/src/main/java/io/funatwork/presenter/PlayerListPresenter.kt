package io.funatwork.presenter

import io.funatwork.domain.interactor.GetPlayerList
import io.funatwork.view.PlayerListView

class PlayerListPresenter(val getPlayerList: GetPlayerList, val playerListView: PlayerListView) : Presenter {

    override fun resume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun destroy() {
        getPlayerList.dispose();
        this.viewListView = null;
    }
}