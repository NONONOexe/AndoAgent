package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Species;
import org.aiwolf.common.data.Talk;

/**
 * 各エージェントの疑い度を管理します。
 * @author keisuke
 */
public class ScoreManager {
	/**
	 * 疑い度のマップを示します。
	 */
	private Map<Agent, Integer> agentDoubtScoreMap;
	/**
	 * 中心視点となる自エージェントを示します。
	 */
	private Agent me;

	/**
	 * 指定されたリストの各エージェントに対して疑い度を管理するオブジェクトを構築します。
	 * @param agentList エージェントのリスト
	 * @param me 中心視点となる自エージェント
	 */
	public ScoreManager(List<Agent> agentList, Agent me) {
		this.agentDoubtScoreMap = new HashMap<>();
		for (Agent agent : agentList) {
			agentDoubtScoreMap.put(agent, DoubtScore.DEFAULT_SCORE.getScore());
		}
		this.me = me;
	}

	/**
	 * 疑い度のマップを返します。<br>
	 * 疑い度のマップとは、各エージェントをキーにして、どのぐらい自分にとって疑わしいエージェントかを数値化した疑い度をマッピングしたものです。
	 * @return 疑い度のマップ
	 */
	public Map<Agent, Integer> getAgentDoubtScoreMap() {
		return agentDoubtScoreMap;
	}

	/**
	 * 中心視点となっている自エージェントを返します。
	 * @return 中心視点となっている自エージェント
	 */
	public Agent getMe() {
		return me;
	}

	/**
	 * 疑い度のマップに直接点数をマッピングします。
	 * @param agent エージェント
	 * @param score 点数
	 */
	public void putScoreMap(Agent agent, int score) {
		agentDoubtScoreMap.put(agent, score);
	}

	/**
	 * 指定されたエージェントの疑い度を返します。
	 * @param agent エージェント
	 */
	public int getScore(Agent agent) {
		return agentDoubtScoreMap.get(agent);
	}

	/**
	 * 指定されたエージェントに疑い点数を追加します。
	 * @param agent エージェント
	 * @param score 疑い点数
	 */
	public void addScore(Agent agent, DoubtScore score) {
		putScoreMap(agent, getScore(agent) + score.getScore());
	}

	/**
	 * 指定された{@code Talk}を評価して疑い度のマップを更新します。
	 * @param talk 評価する{@code Talk}
	 */
	public void scoreTalk(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		switch (utterance.getTopic()) {
		case DIVINED:
			if (utterance.getTarget().equals(me)) {
				if (utterance.getResult().equals(Species.WEREWOLF)) {
					addScore(utterance.getTarget(), DoubtScore.DIVINE_ME_WOLF_SCORE);
				} else {
					addScore(utterance.getTarget(), DoubtScore.DIVINE_ME_HUMAN_SCORE);
				}
			} else {
				if (utterance.getResult().equals(Species.WEREWOLF)) {
					addScore(utterance.getTarget(), DoubtScore.DIVINE_WOLF_SCORE);
				} else {
					addScore(utterance.getTarget(), DoubtScore.DIVINE_HUMAN_SCORE);
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 最も疑わしいエージェントをリストで返します。
	 * @return 最も疑わしいエージェントのリスト
	 */
	public List<Agent> getMostSuspiciousAgentList() {
		List<Agent> mostSuspiciousAgentList = new ArrayList<>();
		int highestScore = Integer.MIN_VALUE;
		for (Agent agent : agentDoubtScoreMap.keySet()) {
			if (highestScore < getScore(agent)) {
				mostSuspiciousAgentList.clear();
				mostSuspiciousAgentList.add(agent);
				highestScore = getScore(agent);
			} else if (highestScore == getScore(agent)) {
				mostSuspiciousAgentList.add(agent);
			}
		}
		return mostSuspiciousAgentList;
	}

	/**
	 * 最も信頼できるエージェントをリストで返します。
	 * @return 最も信頼できるエージェントのリスト
	 */
	public List<Agent> getMostTrustedAgentList() {
		List<Agent> mostTrustedAgentList = new ArrayList<>();
		int lowestScore = Integer.MAX_VALUE;
		for (Agent agent : agentDoubtScoreMap.keySet()) {
			if (getScore(agent) < lowestScore) {
				mostTrustedAgentList.clear();
				mostTrustedAgentList.add(agent);
				lowestScore = getScore(agent);
			} else if (getScore(agent) == lowestScore) {
				mostTrustedAgentList.add(agent);
			}
		}
		return mostTrustedAgentList;
	}
}
