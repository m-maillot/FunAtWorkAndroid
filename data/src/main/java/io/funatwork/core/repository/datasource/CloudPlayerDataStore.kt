package io.funatwork.core.repository.datasource

import io.funatwork.core.net.player.PlayerRestApi

class CloudPlayerDataStore(private val playerRestApi: PlayerRestApi) : PlayerDataStore {

    override fun playerEntityList() =
            playerRestApi.playerEntityList()
}