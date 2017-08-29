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
import io.funatwork.extensions.showHumanDateFromSeconds
import io.funatwork.model.babyfoot.GameModel

class GameAdapter(val context: Context, val gameEntities: List<GameModel>, val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onGameItemClicked(game: GameModel)
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val imgAttackRed: ImageView = mView.findViewById(R.id.img_player_red_attack)
        val imgDefenseRed: ImageView = mView.findViewById(R.id.img_player_red_defense)
        val imgAttackBlue: ImageView = mView.findViewById(R.id.img_player_blue_attack)
        val imgDefenseBlue: ImageView = mView.findViewById(R.id.img_player_blue_defense)
        val tvRedScore: TextView = mView.findViewById(R.id.tv_score_red)
        val tvBlueScore: TextView = mView.findViewById(R.id.tv_score_blue)
        val tvDate: TextView = mView.findViewById(R.id.tv_game_date)
        val tvStatus: TextView = mView.findViewById(R.id.tv_game_state)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val game = gameEntities[position]
        Picasso.with(context).load(game.redTeam.attackPlayer.avatar).into(holder?.imgAttackRed)
        Picasso.with(context).load(game.redTeam.defensePlayer.avatar).into(holder?.imgDefenseRed)
        Picasso.with(context).load(game.blueTeam.attackPlayer.avatar).into(holder?.imgAttackBlue)
        Picasso.with(context).load(game.blueTeam.defensePlayer.avatar).into(holder?.imgDefenseBlue)
        holder?.tvRedScore?.text = game.redTeamGoal.toString()
        holder?.tvBlueScore?.text = game.blueTeamGoal.toString()
        holder?.tvDate?.text = game.beginTimestampInSeconds.showHumanDateFromSeconds()
        holder?.tvStatus?.text = if (game.status == 2) context.getString(R.string.game_state_finished) else context.getString(R.string.game_state_canceled)
        holder?.itemView?.setOnClickListener { onItemClickListener.onGameItemClicked(game) }
    }

    override fun getItemCount(): Int = gameEntities.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.game_item, parent, false))
}