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
import io.funatwork.model.babyfoot.TeamStatsModel

class TeamStatsAdapter(val context: Context, val teamStatsModels: List<TeamStatsModel>) : RecyclerView.Adapter<TeamStatsAdapter.ViewHolder>() {

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgPlayer1: ImageView = mView.findViewById(R.id.img_player1)
        val imgPlayer2: ImageView = mView.findViewById(R.id.img_player2)
        val tvPosition: TextView = mView.findViewById(R.id.tv_team_position)
        val tvTeamName: TextView = mView.findViewById(R.id.tv_team_name)
        val tvTeamStat: TextView = mView.findViewById(R.id.tv_team_stat_detail)
        val tvTeamElo: TextView = mView.findViewById(R.id.tv_team_elo_ranking)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val teamStats = teamStatsModels[position]
        Picasso.with(context).load(teamStats.player1.avatar).into(holder?.imgPlayer1)
        Picasso.with(context).load(teamStats.player2.avatar).into(holder?.imgPlayer2)
        holder?.tvPosition?.text = context.getString(R.string.stat_player_position, position.inc().toString())
        holder?.tvTeamName?.text = teamStats.player1.name + " & " + teamStats.player2.name
        val percentWin = teamStats.victory * 100 / teamStats.gamePlayed
        holder?.tvTeamStat?.text = "Played: ${teamStats.gamePlayed} | Win : $percentWin% | Goals: ${teamStats.goalAverage}" // context.getString(R.string.stat_player_detail, playerStat.gamePlayed.toString(), playerStat.victory.toString(), playerStat.goalAverage.toString())
        holder?.tvTeamElo?.text = (Math.round(teamStats.eloRanking * 100.0) / 100.0).toString()
    }

    override fun getItemCount(): Int = teamStatsModels.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.team_stat_item, parent, false))
}