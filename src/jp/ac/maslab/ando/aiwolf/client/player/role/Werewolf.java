package jp.ac.maslab.ando.aiwolf.client.player.role;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.player.base.WerewolfSide;

/**
 * 人狼の行動を定義するクラスです。
 * @author keisuke
 */
public final class Werewolf extends WerewolfSide {
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
	 * 人狼の戦略を構築します。
	 */
	public Werewolf() {
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		setFakeRole(Role.SEER);
		// 既知のエージェントとその役職を設定
		getRoleForecast().estimateRole(getMe(), Role.WEREWOLF);
	}

	@Override
	public void dayStart() {
		super.dayStart();
		// 襲撃されたエージェントを白と判定
		getRoleForecast().estimateWhite(getLatestDayGameInfo().getAttackedAgent());

		// 占いCOが2人以上いたときに1人が襲撃されたことで残りのCOしたエージェントが黒だと判明したときの処理
		if (getCOInfo().isOverCapatityCORole(Role.MEDIUM)
				&& getCOInfo().getCOAgentList(Role.MEDIUM).contains(getLatestDayGameInfo().getAttackedAgent())) {
			List<Agent> blackAgentList = new ArrayList<>();
			blackAgentList.addAll(getCOInfo().getCOAgentList(Role.MEDIUM));
			blackAgentList.remove(getLatestDayGameInfo().getAttackedAgent());
			getRoleForecast().estimateBlack(blackAgentList);
			getRoleForecast().estimateRole(getLatestDayGameInfo().getAttackedAgent(), Role.MEDIUM);
		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public Agent attack() {
		return null;
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

		if (!getCOInfo().hasTalkedCO(getMe())) {
			return TemplateTalkFactory.comingout(getMe(), Role.SEER);
		} else {
			for (Judge judge : getFakeJudgeList()) {
				if (!getTalkInfo().hasTalkedJudge(judge)) {
					return TemplateTalkFactory.inquested(judge.getTarget(), judge.getResult());
				}
			}
		}

		return Talk.SKIP;
	}

	@Override
	public void update(GameInfo gameInfo) {
		super.update(gameInfo);
		if (!getRoleForecast().getBlackAgentList().isEmpty()
				&& voteTarget != getRoleForecast().getBlackAgentList().get(0)) {
			hasChangedTarget = true;
			voteTarget = getRoleForecast().getBlackAgentList().get(0);
		}

		if (voteTarget != getEvaluator().getDoubtfulAgentList().get(0)) {
			hasChangedTarget = true;
			voteTarget = getEvaluator().getDoubtfulAgentList().get(0);
		}

		setFakeDivineTarget(fakeDivine());
	}

	private Agent fakeDivine() {
		for (Agent agent : getEvaluator().getDoubtfulAgentList(getViabilityInfo().getAliveAgentList())) {
			if (!isJuggedAgent(agent)) {
				return agent;
			}
		}

		for (Agent agent : getTalkInfo().getMostNoisyAgentList(getViabilityInfo().getAliveAgentList())) {
			if (!isJuggedAgent(agent)) {
				return agent;
			}
		}
		List<Agent> cadidates = new ArrayList<>();
		for (Agent agent : getViabilityInfo().getAliveAgentList()) {
			if (!isJuggedAgent(agent)) {
				cadidates.add(agent);
			}
		}
		if (!cadidates.isEmpty()) {
			return cadidates.get(0);
		}
		return null;
	}

	@Override
	public Agent vote() {
		return voteTarget;
	}

	@Override
	public String whisper() {
		return null;
	}
}
