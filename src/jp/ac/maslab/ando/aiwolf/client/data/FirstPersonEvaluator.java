package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.data.Vote;

/**
 * あるエージェントの一人称視点から全エージェントを評価します。
 * @author keisuke
 */
public class FirstPersonEvaluator {
	/**
	 * 各振る舞いに対する重みを示します。
	 */
	private Map<EvaluateStatus, Double> weightMap;
	/**
	 * 各エージェントの評価値です。
	 */
	private Map<Agent, Double> agentEvalMap;
	/**
	 * 視点となるエージェントです。
	 */
	private Agent me;
	/**
	 * 初期評価値です。
	 */
	public static double defaultEval = 5.0D;

	/**
	 * 一人称視点からの全エージェントを評価するオブジェクトを構築します。
	 * @param agentList 評価するエージェントのリスト
	 * @param me 視点となるエージェント
	 */
	public FirstPersonEvaluator(List<Agent> agentList, Agent me) {
		initializeWeightMap();
		agentEvalMap = new HashMap<>();
		for (Agent agent : agentList) {
			agentEvalMap.put(agent, defaultEval);
		}
		this.me = me;
	}

	/**
	 * 重みのマップを初期化します。
	 */
	private void initializeWeightMap() {
		this.weightMap = new HashMap<>();
		this.weightMap.put(EvaluateStatus.DIVINED_WOLF, 5.0D);
		this.weightMap.put(EvaluateStatus.DIVINED_HUMAN, -3.0D);
		this.weightMap.put(EvaluateStatus.DIVINE_ME_WOLF, 15.0D);
		this.weightMap.put(EvaluateStatus.DIVINE_ME_HUMAN, -3.0D);
		this.weightMap.put(EvaluateStatus.SAME_VOTE, -2.0D);
		this.weightMap.put(EvaluateStatus.VOTE_ME, 2.0D);
		this.weightMap.put(EvaluateStatus.VOTED, 1.0D);
		this.weightMap.put(EvaluateStatus.SEER_CO, -0.5D);
		this.weightMap.put(EvaluateStatus.MEDIUM_CO, -0.5D);
		this.weightMap.put(EvaluateStatus.VILLAGER_CO, 1.0D);
		this.weightMap.put(EvaluateStatus.POSSESSED_CO, 4.0D);
		this.weightMap.put(EvaluateStatus.WEREWOLF_CO, 15.0D);
		this.weightMap.put(EvaluateStatus.BODYGUARD_CO, 3.0D);
	}

	/**
	 * 投票を評価します。
	 * @param voteList 投票
	 */
	public void evaluateVote(List<Vote> voteList) {
		Vote myVote = getMyVote(voteList);
		for (Vote vote : voteList) {
			if (myVote != null && vote.getTarget().equals(myVote.getTarget())) {
				updateEvalMap(vote.getAgent(), weightMap.get(EvaluateStatus.SAME_VOTE));
			}
			if (vote.getTarget().equals(me)) {
				updateEvalMap(vote.getAgent(), weightMap.get(EvaluateStatus.VOTE_ME));
			}
			updateEvalMap(vote.getTarget(), weightMap.get(EvaluateStatus.VOTED));
		}
	}

	/**
	 * エージェントの評価値を更新します。
	 * @param agent エージェント
	 * @param variation 評価値の変化量
	 */
	private void updateEvalMap(Agent agent, Double variation) {
		agentEvalMap.put(agent, agentEvalMap.get(agent) + variation);
	}

	/**
	 * 自分の投票を返します。存在しない場合は<code>null</code>を返します。
	 * @param voteList 投票
	 * @return 自分の投票
	 */
	private Vote getMyVote(List<Vote> voteList) {
		for (Vote vote : voteList) {
			if (vote.getAgent().equals(me)) {
				return vote;
			}
		}
		return null;
	}

	/**
	 * 会話を評価します。
	 * @param talk 会話
	 */
	public void evaluateTalk(Talk talk) {
		Utterance utterance = new Utterance(talk.getContent());
		switch (utterance.getTopic()) {
		case DIVINED:
			evaluateDivined(new Divined(
					talk.getDay(), talk.getIdx(), talk.getAgent(), utterance.getTarget(), utterance.getResult()));
			break;
		case COMINGOUT:
			evaluateComingout(new Comingout(talk.getDay(), talk.getIdx(), utterance.getTarget(), utterance.getRole()));
			break;
		default:
			break;
		}
	}

	/**
	 * 占い結果を評価します。
	 * @param agent 発言したエージェント
	 * @param divined 占い結果の発言
	 */
	private void evaluateDivined(Divined divined) {
		if (divined.getTarget().equals(me)) {
			switch (divined.getResult()) {
			case WEREWOLF:
				updateEvalMap(divined.getAgent(), weightMap.get(EvaluateStatus.DIVINE_ME_WOLF));
				break;
			case HUMAN:
				updateEvalMap(divined.getAgent(), weightMap.get(EvaluateStatus.DIVINE_ME_HUMAN));
				break;
			}
		} else {
			switch (divined.getResult()) {
			case WEREWOLF:
				updateEvalMap(divined.getTarget(), weightMap.get(EvaluateStatus.DIVINED_WOLF));
				break;
			case HUMAN:
				updateEvalMap(divined.getTarget(), weightMap.get(EvaluateStatus.DIVINED_HUMAN));
				break;
			}
		}
	}

	/**
	 * COを評価します。
	 * @param agent 発言したエージェント
	 * @param comingout COの発言
	 */
	private void evaluateComingout(Comingout co) {
		switch (co.getRole()) {
		case BODYGUARD:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.BODYGUARD_CO));
			break;
		case MEDIUM:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.MEDIUM_CO));
			break;
		case POSSESSED:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.POSSESSED_CO));
			break;
		case SEER:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.SEER_CO));
			break;
		case VILLAGER:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.VILLAGER_CO));
			break;
		case WEREWOLF:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateStatus.WEREWOLF_CO));
			break;
		default:
			break;
		}
	}

	/**
	 * 指定されたエージェントの中で評価値が最も高く疑わしいエージェントを返します。
	 * @param エージェント
	 * @return 疑わしいエージェント
	 */
	public List<Agent> getDoubtfulAgentList(Collection<Agent> agentList) {
		List<Agent> doubtfulAgentList = new ArrayList<>();
		double max = Double.MIN_VALUE;
		for (Agent agent : agentList) {
			if (max < agentEvalMap.get(agent)) {
				doubtfulAgentList.clear();
				doubtfulAgentList.add(agent);
				max = agentEvalMap.get(agent);
			} else if (max == agentEvalMap.get(agent)) {
				doubtfulAgentList.add(agent);
			}
		}
		return doubtfulAgentList;
	}

	/**
	 * 指定されたエージェントの中で評価値が最も低く信用できるエージェントを返します。
	 * @param エージェント
	 * @return 信用できるエージェント
	 */
	public List<Agent> getConvictionAgentList(Collection<Agent> agentList) {
		List<Agent> convictionAgentList = new ArrayList<>();
		double min = Double.MAX_VALUE;
		for (Agent agent : agentList) {
			if (agentEvalMap.get(agent) < min) {
				convictionAgentList.clear();
				convictionAgentList.add(agent);
				min = agentEvalMap.get(agent);
			} else if (agentEvalMap.get(agent) < min) {
				convictionAgentList.add(agent);
			}
		}
		return convictionAgentList;
	}

	/**
	 * 評価値が最も高く疑わしいエージェントを返します。
	 * @return 疑わしいエージェント
	 */
	public List<Agent> getDoubtfulAgentList() {
		return getDoubtfulAgentList(agentEvalMap.keySet());
	}

	/**
	 * 評価値が最も低く信用できるエージェントを返します。
	 * @return 信用できるエージェント
	 */
	public List<Agent> getConvictionAgentList() {
		return getConvictionAgentList(agentEvalMap.keySet());
	}
}
