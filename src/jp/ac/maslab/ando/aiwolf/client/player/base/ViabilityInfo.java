package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aiwolf.common.data.Agent;

/**
 * 各エージェントの生死に関わる情報です。
 * @author keisuke
 *
 */
public class ViabilityInfo {
	/**
	 * ゲームに参加している全エージェントのリスト
	 */
	private List<Agent> agentList;
	/**
	 * 日付と処刑されたエージェントのマップです。
	 */
	private Map<Integer, Agent> executedMap;
	/**
	 * 日付と襲撃されたエージェントのマップです。
	 */
	private Map<Integer, Agent> attackedMap;

	/**
	 * 各エージェントの生死に関わる情報のオブジェクトを構築します。
	 * @param agentList 全エージェントのリスト
	 */
	public ViabilityInfo(List<Agent> agentList) {
		this.agentList = agentList;
		this.executedMap = new HashMap<>();
		this.attackedMap = new HashMap<>();
	}

	/**
	 * 処刑されたエージェントを設定します。エージェントが<code>null</code>の場合は何もしません。
	 * @param day 日付
	 * @param agent 処刑されたエージェント
	 */
	public void setExecuted(int day, Agent agent) {
		if (agent == null) {
			return;
		}
		this.executedMap.put(day, agent);
	}

	/**
	 * 襲撃されたエージェントを設定します。エージェントが<code>null</code>の場合は何もしません。
	 * @param day 日付
	 * @param agent 襲撃されたエージェント
	 */
	public void setAttacked(int day, Agent agent) {
		if (agent == null) {
			return;
		}
		this.attackedMap.put(day, agent);
	}

	/**
	 * 生存しているエージェントをリストで返します。
	 * @return 生存しているエージェントのリスト
	 */
	public List<Agent> getAliveAgentList() {
		List<Agent> aliveAgentList = new ArrayList<>(this.agentList);
		aliveAgentList.removeAll(this.executedMap.values());
		aliveAgentList.removeAll(this.attackedMap.values());
		return aliveAgentList;
	}

	/**
	 * 死亡しているエージェントをリストで返します。
	 * @return 死亡しているエージェントのリスト
	 */
	public List<Agent> getDeadAgentList() {
		Set<Agent> deadAgentList = new HashSet<>();
		deadAgentList.addAll(this.executedMap.values());
		deadAgentList.addAll(this.attackedMap.values());
		return new ArrayList<>(deadAgentList);
	}

	/**
	 * 指定されたエージェントが生存しているかどうかを返します。
	 * @param agent 生存しているかどうかを調べるエージェント
	 * @return 生存しているならtrue、そうでない場合はfalse
	 */
	public boolean isAlive(Agent agent) {
		return getAliveAgentList().contains(agent);
	}

	/**
	 * 指定されたエージェントが死亡しているかどうかを返します。
	 * @param agent 死亡しているかどうかを調べるエージェント
	 * @return 死亡しているならtrue、そうでない場合はfalse
	 */
	public boolean isDead(Agent agent) {
		return getDeadAgentList().contains(agent);
	}
}
