package io.funatwork.view.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.domain.model.babyfoot.GameStatus
import io.funatwork.extensions.showHumanDateFromMillis
import io.funatwork.model.babyfoot.GameModel
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class GameAdapter(var gameItems: List<MultipleGameItem>) : BaseMultiItemQuickAdapter<MultipleGameItem, BaseViewHolder>(gameItems) {

    init {
        addItemType(MultipleGameItem.LIGHT, R.layout.game_item)
        addItemType(MultipleGameItem.FULL_PENDING, R.layout.game_pending_expand_item)
    }

    override fun convert(helper: BaseViewHolder, item: MultipleGameItem) {
        when (item.itemTypeEntity) {
            MultipleGameItem.LIGHT -> when (item.game.status) {
                GameStatus.PLANNED -> convertGamePlanned(helper, item.game)
                GameStatus.STARTED -> convertGameStarted(helper, item.game)
                GameStatus.OVER -> convertGameOver(helper, item.game)
                GameStatus.CANCELED -> convertGameCanceled(helper, item.game)
            }
            MultipleGameItem.FULL_PENDING -> convertFullGamePlanned(helper, item.game)
        }
    }

    private fun convertGamePlanned(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_score_red, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_game_date, item.plannedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_planned))
        loadAvatar(helper, item)
        helper.setVisible(R.id.btn_tournament_stat_game, true)
        helper.addOnClickListener(R.id.btn_tournament_stat_game)
    }

    private fun convertFullGamePlanned(helper: BaseViewHolder, item: GameModel) {
        convertGamePlanned(helper, item)
        helper.addOnClickListener(R.id.btn_start_game)
    }

    private fun convertGameStarted(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_started))
        loadAvatar(helper, item)
    }

    private fun convertGameOver(helper: BaseViewHolder, item: GameModel) {
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

    private fun convertGameCanceled(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, item.blueTeamGoal.toString())
        helper.setText(R.id.tv_score_red, item.redTeamGoal.toString())
        helper.setText(R.id.tv_game_date, item.startedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_canceled))
        loadAvatar(helper, item)
    }

    private fun loadAvatar(helper: BaseViewHolder, item: GameModel) {
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