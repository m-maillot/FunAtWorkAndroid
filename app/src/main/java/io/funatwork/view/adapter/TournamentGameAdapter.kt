package io.funatwork.view.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import io.funatwork.R
import io.funatwork.domain.model.babyfoot.GameStatus
import io.funatwork.extensions.showHumanDateFromMillis
import io.funatwork.model.babyfoot.GameModel
import io.funatwork.view.adapter.item.Game
import io.funatwork.view.adapter.item.GameItem
import io.funatwork.view.adapter.item.Header
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class TournamentGameAdapter(gameItems: List<GameItem>) : BaseMultiItemQuickAdapter<GameItem, BaseViewHolder>(gameItems) {

    init {
        addItemType(GameItem.GAME, R.layout.game_item)
        addItemType(GameItem.HEADER, R.layout.game_tournament_round_header)
    }

    override fun convert(helper: BaseViewHolder, item: GameItem) {
        when (item) {
            is Game -> convertGame(helper, item.game)
            is Header -> convertHeader(helper, item)
        }
    }

    private fun convertHeader(helper: BaseViewHolder, item: Header) {
        if (item.roundIndexInversed == 0) {
            helper.setText(R.id.tv_tournament_header_round, "Final")
        } else {
            helper.setText(R.id.tv_tournament_header_round,
                    mContext.getString(R.string.tournament_item_header_round, item.roundIndex.toString()))

        }
    }

    private fun convertGame(helper: BaseViewHolder, item: GameModel) {
        when (item.status) {
            GameStatus.PLANNED -> convertGamePlanned(helper, item)
            GameStatus.STARTED -> convertGameStarted(helper, item)
            GameStatus.OVER -> convertGameOver(helper, item)
            GameStatus.CANCELED -> convertGameCanceled(helper, item)
        }
    }

    private fun convertGamePlanned(helper: BaseViewHolder, item: GameModel) {
        helper.setText(R.id.tv_score_blue, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_score_red, mContext.getString(R.string.game_no_score))
        helper.setText(R.id.tv_game_date, item.plannedDate.millis.showHumanDateFromMillis())
        helper.setText(R.id.tv_game_state, mContext.getString(R.string.game_state_planned))
        loadAvatar(helper, item)
        if (item.redTeam.id >= 0 && item.blueTeam.id >= 0) {
            helper.setVisible(R.id.btn_tournament_stat_game, true)
            helper.addOnClickListener(R.id.btn_tournament_stat_game)
        } else {
            helper.setVisible(R.id.btn_tournament_stat_game, false)
        }
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
        } else {
            helper.setImageResource(R.id.img_player_red_attack, R.drawable.ic_user)
            helper.setImageResource(R.id.img_player_red_defense, R.drawable.ic_user)
        }
        if (item.blueTeam.id >= 0) {
            Picasso.with(mContext).load(item.blueTeam.attackPlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_attack))
            Picasso.with(mContext).load(item.blueTeam.defensePlayer.avatar).into(helper.getView<ImageView>(R.id.img_player_blue_defense))
        } else {
            helper.setImageResource(R.id.img_player_blue_attack, R.drawable.ic_user)
            helper.setImageResource(R.id.img_player_blue_defense, R.drawable.ic_user)
        }
    }
}