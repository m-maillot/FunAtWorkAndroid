package io.funatwork.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.model.babyfoot.PlayerStatsModel

class PlayerStatsAdapter(val context: Context, val teamStatsModels: List<PlayerStatsModel>) : RecyclerView.Adapter<PlayerStatsAdapter.ViewHolder>() {

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgPlayer: ImageView = mView.findViewById(R.id.img_player)
        val tvPosition: TextView = mView.findViewById(R.id.tv_player_position)
        val tvPlayerName: TextView = mView.findViewById(R.id.tv_player_name)
        val tvPlayerElo: TextView = mView.findViewById(R.id.tv_player_elo_ranking)

    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val playerStat = teamStatsModels[position]
        Picasso.with(context).load(playerStat.player.avatar).into(holder?.imgPlayer)
        holder?.tvPosition?.text = context.getString(R.string.stat_player_position, playerStat.rank.toString())
        holder?.tvPlayerName?.text = "${playerStat.player.name} ${playerStat.player.surname.substring(0, 1)}."
        holder?.tvPlayerElo?.text = playerStat.eloRanking.toString()
    }

    override fun getItemCount(): Int = teamStatsModels.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.player_stat_item, parent, false))
}