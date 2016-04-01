package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Species;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.data.Vote;

import jp.ac.maslab.ando.aiwolf.client.player.base.TimeManager;
import jp.ac.maslab.ando.aiwolf.client.tool.util.AIWolfTools;

/**
 * あるエージェントの一人称視点から全エージェントを評価します。
 * @author ando
 */
public class FirstPersonEvaluator {
	/**
	 * 各振る舞いに対する重みを示します。
	 */
	private Map<EvaluateIndex, Double> weightMap;
	/**
	 * 各エージェントの評価値です。
	 */
	private Map<Agent, Double> agentEvalMap;
	/**
	 * 視点となるエージェントです。
	 */
	private Agent me;
	/**
	 * 時間を管理するオブジェクトです。
	 */
	private TimeManager timeManager;
	/**
	 * 初期評価値です。
	 */
	public static double defaultEval = 5.0D;

	/**
	 * 一人称視点からの全エージェントを評価するオブジェクトを構築します。
	 * @param agentList 評価するエージェントのリスト
	 * @param me 視点となるエージェント
	 * @param timeManager 時間を管理するオブジェクト
	 */
	public FirstPersonEvaluator(List<Agent> agentList, Agent me, TimeManager timeManager) {
		initializeWeightMap();
		agentEvalMap = new HashMap<>();
		for (Agent agent : agentList) {
			if (agent.equals(me)) {
				continue;
			}
			agentEvalMap.put(agent, defaultEval);
		}
		this.me = me;
		this.timeManager = timeManager;
	}

	/**
	 * 重みのマップを初期化します。
	 */
	private void initializeWeightMap() {
		this.weightMap = new HashMap<>();
		this.weightMap.put(EvaluateIndex.DIVINED_WOLF, 5.0D);
		this.weightMap.put(EvaluateIndex.DIVINED_HUMAN, -3.0D);
		this.weightMap.put(EvaluateIndex.DIVINE_ME_WOLF, 15.0D);
		this.weightMap.put(EvaluateIndex.DIVINE_ME_HUMAN, -3.0D);
		this.weightMap.put(EvaluateIndex.SAME_VOTE, -2.0D);
		this.weightMap.put(EvaluateIndex.VOTE_ME, 2.0D);
		this.weightMap.put(EvaluateIndex.VOTED, 1.0D);
		this.weightMap.put(EvaluateIndex.SEER_CO, -0.5D);
		this.weightMap.put(EvaluateIndex.MEDIUM_CO, -0.5D);
		this.weightMap.put(EvaluateIndex.VILLAGER_CO, 1.0D);
		this.weightMap.put(EvaluateIndex.POSSESSED_CO, 4.0D);
		this.weightMap.put(EvaluateIndex.WEREWOLF_CO, 15.0D);
		this.weightMap.put(EvaluateIndex.BODYGUARD_CO, 3.0D);
		this.weightMap.put(EvaluateIndex.AGREED_OPINION, 0.5D);
		this.weightMap.put(EvaluateIndex.DISAGREED_OPINION, -0.5D);
	}

	/**
	 * 投票を評価します。
	 * @param voteList 投票のリスト
	 */
	public void evaluateVote(List<Vote> voteList) {
		Vote myVote = searchMyVote(voteList);
		for (Vote vote : voteList) {
			this.evaluateVote(vote, myVote);
		}
	}

	/**
	 * 指定された投票のリストの中から、自分の投票を返します。存在しない場合は<code>null</code>を返します。
	 * @param voteList 投票のリスト
	 * @return 自分の投票
	 */
	private Vote searchMyVote(List<Vote> voteList) {
		for (Vote vote : voteList) {
			if (vote.getAgent().equals(me)) {
				return vote;
			}
		}
		return null;
	}

	/**
	 * 投票を評価します。
	 * @param vote 投票
	 * @param myVote 自分の投票
	 */
	private void evaluateVote(Vote vote, Vote myVote) {
		// 投票先が自分の場合です。
		if (vote.getTarget().equals(me)) {
			this.updateEvalMap(vote.getAgent(), this.getEvalVoteMe());
			return;
		}
		// 投票先が自分と同じ場合です。
		if (myVote != null && vote.getTarget().equals(myVote.getTarget())) {
			this.updateEvalMap(vote.getAgent(), this.getEvalSameVote());
		}
		// 投票されたエージェントにマイナス評価値を加えます。
		this.updateEvalMap(vote.getTarget(), this.getEvalVoted());
	}

	/**
	 * エージェントの評価値を更新します。
	 * @param agent エージェント
	 * @param variation 評価値の変化量
	 */
	private void updateEvalMap(Agent agent, Double variation) {
		if (agent.equals(me)) {
			return;
		}
		agentEvalMap.put(agent, agentEvalMap.get(agent) + variation);
	}

	/**
	 * 自分に投票したエージェントに対する評価値を返します。<br>
	 * 評価値は次のように計算されます。
	 * <blockquote>
	 * VOTE_ME : 投票先が自分であること<br>
	 * W(status) : 評価指標statusに対する重み<br>
	 * day : 日数<br>
	 * <br>
	 * 評価値 = W(VOTE_ME) * (1.0 + day * 0.1)
	 * </blockquote>
	 * @return 自分に投票したエージェントに対する評価値
	 */
	private Double getEvalVoteMe() {
		double coefficient = 1.0D + this.timeManager.getCurrent().getDay() / 10.0D;
		return this.weightMap.get(EvaluateIndex.VOTE_ME) * coefficient;
	}

	/**
	 * 自分と投票先が同じエージェントに対する評価値を返します。<br>
	 * 評価値は次のように計算されます。
	 * <blockquote>
	 * SAME_VOTE : 自分と投票先が同じであること<br>
	 * W(status) : 評価指標statusに対する重み<br>
	 * day : 日数<br>
	 * <br>
	 * 評価値 = W(SAME_VOTE) * (1.0 + day * 0.1)
	 * </blockquote>
	 * @return 自分と投票先が同じエージェントに対する評価値
	 */
	private Double getEvalSameVote() {
		double coefficient = 1.0D + this.timeManager.getCurrent().getDay() * 0.1D;
		return this.weightMap.get(EvaluateIndex.SAME_VOTE) * coefficient;
	}

	/**
	 * 投票されたエージェントに対する評価値を返します。<br>
	 * 評価値は次のように計算されます。
	 * <blockquote>
	 * VOTED : 投票されたこと<br>
	 * W(status) : 評価指標statusに対する重み<br>
	 * day : 日数<br>
	 * <br>
	 * 評価値 = W(VOTED) * (1.0 + day * 0.1)
	 * </blockquote>
	 * @return 投票されたエージェントに対する評価値
	 */
	private Double getEvalVoted() {
		double coefficient = 1.0D + this.timeManager.getCurrent().getDay() / 10.0D;
		return this.weightMap.get(EvaluateIndex.VOTED) * coefficient;
	}

	/**
	 * 会話を評価します。
	 * @param talk 会話
	 */
	public void evaluateTalk(Talk talk) {
		Topic topic = new Utterance(talk.getContent()).getTopic();
		if (topic == Topic.DIVINED) {
			this.evaluateDivined(AIWolfTools.convertToDivined(talk));
		}
		if (topic == Topic.COMINGOUT) {
			this.evaluateComingout(AIWolfTools.convertToComingout(talk));
		}
	}

	/**
	 * 占い結果を評価します。
	 * @param agent 発言したエージェント
	 * @param divined 占い結果の発言
	 */
	private void evaluateDivined(Divined divined) {
		// 自分が人狼と占われた場合です
		if (divined.getTarget().equals(me) && this.isWolf(divined.getResult())) {
			updateEvalMap(divined.getAgent(), weightMap.get(EvaluateIndex.DIVINE_ME_WOLF));
			return;
		}
		// 自分が人間と占われた場合です
		if (divined.getTarget().equals(me) && this.isHuman(divined.getResult())) {
			updateEvalMap(divined.getAgent(), weightMap.get(EvaluateIndex.DIVINE_ME_HUMAN));
			return;
		}
		// 他人が人狼と占われた場合です
		if (this.isWolf(divined.getResult())) {
			updateEvalMap(divined.getTarget(), weightMap.get(EvaluateIndex.DIVINED_WOLF));
			return;
		}
		// 他人が人間と占われた場合です
		if (this.isHuman(divined.getResult())) {
			updateEvalMap(divined.getTarget(), weightMap.get(EvaluateIndex.DIVINED_HUMAN));
			return;
		}
	}

	/**
	 * 人狼かどうかを返します。
	 * @param species 種族
	 * @return 指定された種族がWEREWOLFならtrue、そうでないならfalseを返します。
	 */
	private boolean isWolf(Species species) {
		return species == Species.WEREWOLF;
	}

	/**
	 * 人間かどうかを返します。
	 * @param species 種族
	 * @return 指定された種族がHUMANならtrue、そうでないならfalseを返します。
	 */
	private boolean isHuman(Species species) {
		return species == Species.HUMAN;
	}

	/**
	 * COを評価します。
	 * @param agent 発言したエージェント
	 * @param comingout COの発言
	 */
	private void evaluateComingout(Comingout co) {
		switch (co.getRole()) {
		case BODYGUARD:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.BODYGUARD_CO));
			break;
		case MEDIUM:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.MEDIUM_CO));
			break;
		case POSSESSED:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.POSSESSED_CO));
			break;
		case SEER:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.SEER_CO));
			break;
		case VILLAGER:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.VILLAGER_CO));
			break;
		case WEREWOLF:
			updateEvalMap(co.getAgent(), weightMap.get(EvaluateIndex.WEREWOLF_CO));
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
			if (agent.equals(me)) {
				continue;
			}
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
			if (agent.equals(me)) {
				continue;
			}
			if (agentEvalMap.get(agent) < min) {
				convictionAgentList.clear();
				convictionAgentList.add(agent);
				min = agentEvalMap.get(agent);
			} else if (agentEvalMap.get(agent) == min) {
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

	/**
	 * 指定されたエージェントの評価値を返します。ただし、自分の評価は正しくできないため、0.0を返します。
	 * @param agent 評価値を調べたいエージェント
	 * @return agentの評価値
	 */
	public double getEvaluateValue(Agent agent) {
		if (agent.equals(me)) {
			return 0.0D;
		}
		return agentEvalMap.get(agent);
	}
}
