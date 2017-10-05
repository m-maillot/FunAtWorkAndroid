package io.funatwork.view.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.extensions.showHumanDateFromMillis
import io.funatwork.model.babyfoot.GameModel
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class GameAdapter(data: List<GameModel>) : BaseQuickAdapter<GameModel, BaseViewHolder>(R.layout.game_item, data) {

    override fun convert(helper: BaseViewHolder, item: GameModel) {
        when (item.status) {
            item.GAME_PLANNED -> convertGamePlanned(helper, item)
            item.GAME_STARTED -> convertGameStarted(helper, item)
            item.GAME_OVER -> convertGameOver(helper, item)
            item.GAME_CANCELED -> convertGameCanceled(helper, item)
        }
    }

    fun convertGamePlanned(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_score_red, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_game_date, item.plannedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_planned))
        loadAvatar(helper, item)
    }

    fun convertGameStarted(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_started))
        loadAvatar(helper, item)
    }

    fun convertGameOver(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_finished))
        if (item.blueTeamGoal > item.redTeamGoal) {
            Picasso.with(mContext).load(item.redTeam.attackPlayer.avatar)
                    .transform(GrayscaleTransformation())
                    .into(helper.getView<ImageView>(R.id.img_player_red_attack))
            Picasso.with(mContext).load(item.redTeam.defensePlayer.avatar)
                    .transform(GrayscaleTransformation())
                    .into(helper.getView<ImageView>(R.id.img_player_red_defense))
            Picasso.with(mContext).load(item.blueTeam.attackPlayer.avatar)
                    .into(helper.getView<ImageView>(R.id.img_player_blue_attack))
            Picasso.with(mContext).load(item.blueTeam.defensePlayer.avatar)
                    .into(helper.getView<ImageView>(R.id.img_player_blue_defense))
        } else {
            Picasso.with(mContext).load(item.redTeam.attackPlayer.avatar)
                    .into(helper.getView<ImageView>(R.id.img_player_red_attack))
            Picasso.with(mContext).load(item.redTeam.defensePlayer.avatar)
                    .into(helper.getView<ImageView>(R.id.img_player_red_defense))
            Picasso.with(mContext).load(item.blueTeam.attackPlayer.avatar)
                    .transform(GrayscaleTransformation())
                    .into(helper.getView<ImageView>(R.id.img_player_blue_attack))
            Picasso.with(mContext).load(item.blueTeam.defensePlayer.avatar)
                    .transform(GrayscaleTransformation())
                    .into(helper.getView<ImageView>(R.id.img_player_blue_defense))
        }
    }

    fun convertGameCanceled(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_canceled))
        loadAvatar(helper, item)
    }

    fun loadAvatar(helper: BaseViewHolder, item: GameModel) {
        if (item.redTeam.id >= 0) {
            Picasso.with(mContext).load(item.redTeam.attackPlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_red_attack))
            Picasso.with(mContext).load(item.redTeam.defensePlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_red_defense))
        }
        if (item.blueTeam.id >= 0) {
            Picasso.with(mContext).load(item.blueTeam.attackPlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_attack))
            Picasso.with(mContext).load(item.blueTeam.defensePlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_defense))
        }
    }
}