package jp.ac.maslab.ando.aiwolf.client.player.role;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.lib.TemplateTalkFactory;
import org.aiwolf.client.lib.Topic;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.player.base.SeerBase;
import jp.ac.maslab.ando.aiwolf.client.tool.util.AIWolfTools;

/**
 * 占い師の行動を定義するクラスです。
 * @author keisuke
 */
public final class Seer extends SeerBase {
	/**
	 * 人狼と疑っているエージェントの報告をしたかどうかのフラグです。
	 */
	private boolean hasTalkedDoubtful;
	/**
	 * 村人だと信じているエージェントの報告をしたかどうかのフラグです。
	 */
	private boolean hasTalkedConviction;

	/**
	 * 占い師の戦略を構築します。
	 */
	public Seer() {
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		// 既知のエージェントとその役職を設定
		getRoleForecast().estimateRole(getMe(), Role.SEER);
	}

	@Override
	public void dayStart() {
		try {
			super.dayStart();
			// 襲撃されたエージェントを白と判定
			if (getLatestDayGameInfo().getAttackedAgent() != null) {
				getRoleForecast().estimateWhite(getLatestDayGameInfo().getAttackedAgent());
			}

			// 占いCOが2人以上いたときに1人が襲撃されたことで残りのCOしたエージェントが黒だと判明したときの処理
			if (getCOInfo().isOverCapatityCORole(Role.MEDIUM)
					&& getCOInfo().getCOAgentList(Role.MEDIUM).contains(getLatestDayGameInfo().getAttackedAgent())) {
				List<Agent> blackAgentList = new ArrayList<>();
				blackAgentList.addAll(getCOInfo().getCOAgentList(Role.MEDIUM));
				blackAgentList.remove(getLatestDayGameInfo().getAttackedAgent());
				getRoleForecast().estimateBlack(blackAgentList);
				getRoleForecast().estimateRole(getLatestDayGameInfo().getAttackedAgent(), Role.MEDIUM);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public Agent divine() {
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
		return cadidates.isEmpty() ? getViabilityInfo().getAliveAgentList().get(0) : cadidates.get(0);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public String talk() {
		try {
			getVoteTargetManager().setVoteTarget(selectVoteTarget());

			if (!getTalkInfo().hasTalked(getDay(), getMe(), Topic.VOTE)
					|| getVoteTargetManager().hasChangedTarget()) {
				return TemplateTalkFactory.vote(getVoteTargetManager().getVoteTarget());
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
				for (Judge judge : getMyJudgeList()) {
					if (!getTalkInfo().hasTalkedJudge(judge)) {
						return TemplateTalkFactory.divined(judge.getTarget(), judge.getResult());
					}
				}
			}
			return Talk.OVER;
		} catch (Exception e) {
			e.printStackTrace();
			return Talk.SKIP;
		}
	}

	@Override
	public void update(GameInfo gameInfo) {
		try {
			super.update(gameInfo);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public Agent selectVoteTarget() {
		Agent voteTarget = getVoteTargetManager().getVoteTarget();
		if (!getRoleForecast().getBlackAgentList().isEmpty()
				&& !getRoleForecast().getBlackAgentList().contains(voteTarget)) {
			return AIWolfTools.getRandomAgent(getRoleForecast().getBlackAgentList());
		}
		if (!getEvaluator().getDoubtfulAgentList(getViabilityInfo().getAliveAgentList()).isEmpty()
				&& !getEvaluator().getDoubtfulAgentList(getViabilityInfo().getAliveAgentList()).contains(voteTarget)) {
			return AIWolfTools
					.getRandomAgent(getEvaluator().getDoubtfulAgentList(getViabilityInfo().getAliveAgentList()));
		}
		return voteTarget;
	}

	@Override
	public Agent vote() {
		return getVoteTargetManager().getVoteTarget();
	}
}