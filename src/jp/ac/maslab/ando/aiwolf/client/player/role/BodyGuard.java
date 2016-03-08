package jp.ac.maslab.ando.aiwolf.client.player.role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.player.base.BodyguardBase;

/**
 * 狩人の行動を定義するクラスです。
 * @author keisuke
 */
public final class Bodyguard extends BodyguardBase {
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
	 * 狩人の戦略を構築します。
	 */
	public Bodyguard() {
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		// 既知のエージェントとその役職を設定
		getRoleForecast().estimateRole(getMe(), Role.BODYGUARD);
	}

	@Override
	public void dayStart() {
		super.dayStart();
		hasChangedTarget = true;
		hasTalkedDoubtful = false;
		hasTalkedConviction = false;
		voteTarget = null;

		if (getLatestDayGameInfo().getAttackedAgent() != null) {
			// 襲撃されたエージェントを白と判定
			getRoleForecast().estimateWhite(getLatestDayGameInfo().getAttackedAgent());

			// 占いまたは霊能COが2人以上いたときに1人が襲撃されたことで残りのCOしたエージェントが黒だと判明したときの処理
			for (Role role : Arrays.asList(Role.SEER, Role.MEDIUM)) {
				if (getCOInfo().isOverCapatityCORole(role)
						&& getCOInfo().getCOAgentList(role).contains(getLatestDayGameInfo().getAttackedAgent())) {
					List<Agent> blackAgentList = new ArrayList<>();
					blackAgentList.addAll(getCOInfo().getCOAgentList(role));
					blackAgentList.remove(getLatestDayGameInfo().getAttackedAgent());
					getRoleForecast().estimateBlack(blackAgentList);
					getRoleForecast().estimateRole(getLatestDayGameInfo().getAttackedAgent(), role);
				}
			}
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Agent guard() {
		if (!getEvaluator().getConvictionAgentList(
				getViabilityInfo().getAliveAgentList(
						getCOInfo().getCOAgentList(Role.SEER)))
				.isEmpty()) {
			return getEvaluator().getConvictionAgentList(
					getViabilityInfo().getAliveAgentList(
							getCOInfo().getCOAgentList(Role.SEER)))
					.get(0);
		}
		return getEvaluator().getConvictionAgentList(
				getViabilityInfo().getAliveAgentList()).get(0);
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
				&& getRoleForecast().getGrayAgentList()
						.containsAll(getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)))
				&& !getViabilityInfo().getAliveAgentList(getCOInfo().getCOAgentList(Role.MEDIUM)).isEmpty()
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
