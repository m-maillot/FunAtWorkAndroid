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
import io.funatwork.model.PlayerModel
import io.funatwork.utils.CircleTransformation

class PlayerAdapter(val context: Context, val playerEntities: List<PlayerModel>, val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onPlayerItemClicked(player: PlayerModel)
    }

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val avatar: ImageView = mView.findViewById(R.id.img_player_avatar) as ImageView
        val name: TextView = mView.findViewById(R.id.txt_player_name) as TextView
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val player = playerEntities[position]
        Picasso.with(context).load(player.avatar).into(holder?.avatar)
        holder?.name?.text = player.name
        holder?.itemView?.setOnClickListener { onItemClickListener.onPlayerItemClicked(player) }
    }

    override fun getItemCount(): Int = playerEntities.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.player_item, parent, false))
}