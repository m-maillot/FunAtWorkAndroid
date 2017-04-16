package io.funatwork.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.funatwork.R
import io.funatwork.core.entity.PlayerEntity

/**
 * Created by mmaillot on 4/9/17.
 */
class PlayerAdapter(val pPlayerEntities: List<PlayerEntity>) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val avatar: ImageView = mView.findViewById(R.id.img_player_avatar) as ImageView
        val name: TextView = mView.findViewById(R.id.txt_player_name) as TextView
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val player = pPlayerEntities[position]
        // holder?.avatar?.
        holder?.name?.text = player.name
    }

    override fun getItemCount(): Int = pPlayerEntities.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.player_item, parent, false))
}