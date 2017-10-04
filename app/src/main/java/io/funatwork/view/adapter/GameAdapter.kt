package io.funatwork.view.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.extensions.showHumanDateFromMillis
import io.funatwork.model.babyfoot.GameModel

class GameAdapter(data: List<GameModel>) : BaseQuickAdapter<GameModel, BaseViewHolder>(R.layout.game_item, data) {

    override fun convert(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, if (item.status == 2) mContext.getString(R.string.game_state_finished) else mContext.getString(R.string.game_state_canceled))
        Picasso.with(mContext).load(item.redTeam.attackPlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_red_attack))
        Picasso.with(mContext).load(item.redTeam.defensePlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_red_defense))
        Picasso.with(mContext).load(item.blueTeam.attackPlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_attack))
        Picasso.with(mContext).load(item.blueTeam.defensePlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_defense))
    }
}