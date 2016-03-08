package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Talk;

/**
 * 会話の情報を保持します。
 * @author keisuke
 */
public class TalkInfo {
	/**
	 * エージェントごとの会話を保持するマップです。
	 */
	private Map<Agent, List<Talk>> agentTalkListMap;
	/**
	 * {@code Topic}ごとの会話を保持するマップです。
	 */
	private Map<Topic, List<Talk>> topicTalkListMap;

	/**
	 * 新しく会話の情報を構築します。
	 * @param agentList ゲームに参加する全ゲームエージェント
	 */
	public TalkInfo(List<Agent> agentList) {
		agentTalkListMap = new HashMap<>();
		for (Agent agent : agentList) {
			agentTalkListMap.put(agent, new ArrayList<>());
		}
		topicTalkListMap = new HashMap<>();
		for (Topic topic : Topic.values()) {
			topicTalkListMap.put(topic, new ArrayList<>());
		}
	}

	/**
	 * 会話情報に{@code Talk}オブジェクトを追加します。{@code Topic}がSKIPまたはOVERの場合は無視されます。
	 * @param talk {@code Talk}オブジェクト
	 */
	public void addTalk(Talk talk) {
		if (talk.isSkip() || talk.isOver()) {
			return;
		}
		agentTalkListMap.get(talk.getAgent()).add(talk);
		topicTalkListMap.get(new Utterance(talk.getContent()).getTopic()).add(talk);
	}

	/**
	 * 指定されたエージェントの発言をリストで返します。
	 * @param agent エージェント
	 * @return 発言のリスト
	 */
	public List<Talk> getTalkList(Agent agent) {
		return agentTalkListMap.get(agent);
	}

	/**
	 * 指定された{@code Topic}の発言をリストで返します。
	 * @param topic {@code Topic}オブジェクト
	 * @return 発言のリスト
	 */
	public List<Talk> getTalkList(Topic topic) {
		return topicTalkListMap.get(topic);
	}

	/**
	 * エージェントの発言回数を返します。
	 * @param agent エージェント
	 * @return エージェントの発言回数
	 */
	public int getTalkTimes(Agent agent) {
		return agentTalkListMap.get(agent).size();
	}

	/**
	 * エージェントの発言回数を返します。
	 * @param agent エージェント
	 * @param day 日付
	 * @return エージェントの発言回数
	 */
	public int getTalkTimes(Agent agent, int day) {
		int talkTimes = 0;
		for (Talk talk : agentTalkListMap.get(agent)) {
			if (talk.getDay() == day) {
				talkTimes++;
			}
		}
		return talkTimes;
	}

	/**
	 * 指定されたエージェントのリストの中でゲーム全体での発言回数が最も少ないエージェントをリストで返します。
	 * @return 指定されたエージェントのリストの中でゲーム全体での発言回数が最も少ないエージェントのリスト
	 */
	public List<Agent> getMostQuietAgentList(List<Agent> agentList) {
		List<Agent> quietAgentList = new ArrayList<>();
		int minTalkTimes = Integer.MAX_VALUE;
		for (Agent agent : agentList) {
			if (getTalkTimes(agent) < minTalkTimes) {
				quietAgentList.clear();
				quietAgentList.add(agent);
				minTalkTimes = getTalkTimes(agent);
			} else if (getTalkTimes(agent) == minTalkTimes) {
				quietAgentList.add(agent);
			}
		}
		return quietAgentList;
	}

	/**
	 * 指定されたエージェントのリストの中でゲーム全体での発言回数が最も多いエージェントをリストで返します。
	 * @return 指定されたエージェントのリストの中でゲーム全体での発言回数が最も多いエージェントのリスト
	 */
	public List<Agent> getMostNoisyAgentList(List<Agent> agentList) {
		List<Agent> noisyAgentList = new ArrayList<>();
		int maxTalkTimes = Integer.MIN_VALUE;
		for (Agent agent : agentList) {
			if (maxTalkTimes < getTalkTimes(agent)) {
				noisyAgentList.clear();
				noisyAgentList.add(agent);
				maxTalkTimes = getTalkTimes(agent);
			} else if (maxTalkTimes == getTalkTimes(agent)) {
				noisyAgentList.add(agent);
			}
		}
		return noisyAgentList;
	}

	/**
	 * 指定された発言がされているかどうかを返します。
	 * @param day 日付
	 * @param agent エージェント
	 * @param topic {@code Topic}オブジェクト
	 * @return 指定された発言がされているかどうか
	 */
	public boolean hasTalked(int day, Agent agent, Topic topic) {
		for (Talk talk : getTalkList(agent)) {
			Utterance utterance = new Utterance(talk.getContent());
			if (talk.getDay() == day && utterance.getTopic().equals(topic)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定された{@code Judge}について発言されてるかどうかを返します。
	 * @param judge 占い結果または霊能結果
	 * @return 指定された{@code Judge}について発言されてるかどうか
	 */
	public boolean hasTalkedJudge(Judge judge) {
		for (Talk talk : getTalkList(Topic.DIVINED)) {
			Utterance utterance = new Utterance(talk.getContent());
			if (judge.getAgent().equals(talk.getAgent())
					&& judge.getDay() == talk.getDay()
					&& judge.getTarget().equals(utterance.getTarget())
					&& judge.getResult().equals(utterance.getResult())) {
				return true;
			}
		}
		for (Talk talk : getTalkList(Topic.INQUESTED)) {
			Utterance utterance = new Utterance(talk.getContent());
			if (judge.getAgent().equals(talk.getAgent())
					&& judge.getDay() == talk.getDay()
					&& judge.getTarget().equals(utterance.getTarget())
					&& judge.getResult().equals(utterance.getResult())) {
				return true;
			}
		}
		return false;
	}
}
