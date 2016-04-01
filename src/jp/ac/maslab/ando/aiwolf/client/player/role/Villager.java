package jp.ac.maslab.ando.aiwolf.client.player.role;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.player.base.VillagerBase;

/**
 * 村人の行動を定義するクラスです。
 * @author ando
 */
public final class Villager extends VillagerBase {
	/**
	 * 投票先が変更されたかどうかのフラグです。
	 */
	private boolean hasChangedTarget;
	/**
	 * 人狼と疑っているエージェントの報告をしたかどうかのフラグです。
	 */
	private boolean hasTalkedDoubtful;
	/**
	 * 村人だと信じているエージェントの報告をしたかどうかのフラグです。
	 */
	private boolean hasTalkedConviction;
	/**
	 * 投票先のエージェントです。
	 */
	private Agent voteTarget;

	/**
	 * 村人の戦略を構築します。
	 */
	public Villager() {
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		// 既知のエージェントとその役職を設定
		getRoleForecast().estimateRole(getMe(), Role.VILLAGER);

	}

	@Override
	public void dayStart() {
		try {
			super.dayStart();
			// 襲撃されたエージェントを白と判定します。
			if (getLatestDayGameInfo().getAttackedAgent() != null) {
				getRoleForecast().estimateWhite(getLatestDayGameInfo().getAttackedAgent());
			}
			// 霊能COしたエージェントが2人以上いる状態で1人が襲撃された場合です
			if (getLatestDayGameInfo().getAttackedAgent() != null
					&& getCOInfo().isOverCapatityCORole(Role.MEDIUM)
					&& getCOInfo().getCOAgentList(Role.MEDIUM).contains(getLatestDayGameInfo().getAttackedAgent())) {
				List<Agent> blackAgentList = new ArrayList<>();
				blackAgentList.addAll(getCOInfo().getCOAgentList(role));
				blackAgentList.remove(getLatestDayGameInfo().getAttackedAgent());
				getRoleForecast().estimateBlack(blackAgentList);
				getRoleForecast().estimateRole(getLatestDayGameInfo().getAttackedAgent(), role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public String talk() {
		if (hasChangedTarget) {
			hasChangedTarget = false;
			return TemplateTalkFactory.vote(voteTarget);
		}

		if (hasTalkedDoubtful == false) {
			hasTalkedDoubtful = true;
			return TemplateTalkFactory.estimate(getEvaluator().getDoubtfulAgentList().get(0), Role.WEREWOLF);
		}

		if (hasTalkedConviction == false) {
			hasTalkedConviction = true;
			return TemplateTalkFactory.estimate(getEvaluator().getConvictionAgentList().get(0), Role.VILLAGER);
		}

		return Talk.SKIP;
	}

	@Override
	public void update(GameInfo gameInfo) {
		super.update(gameInfo);
		if (getCOInfo().isOverCapatityCORole(Role.MEDIUM)
				&& !getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)).isEmpty()
				&& getRoleForecast().getGrayAgentList()
						.containsAll(getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)))
				&& voteTarget != getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)).get(0)) {
			hasChangedTarget = true;
			voteTarget = getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)).get(0);
		}

		if (!getRoleForecast().getBlackAgentList().isEmpty()
				&& voteTarget != getRoleForecast().getBlackAgentList().get(0)) {
			hasChangedTarget = true;
			voteTarget = getRoleForecast().getBlackAgentList().get(0);
		}

		if (voteTarget != getEvaluator().getDoubtfulAgentList().get(0)) {
			hasChangedTarget = true;
			voteTarget = getEvaluator().getDoubtfulAgentList().get(0);
		}
	}

	@Override
	public Agent vote() {
		return voteTarget;
	}
}
