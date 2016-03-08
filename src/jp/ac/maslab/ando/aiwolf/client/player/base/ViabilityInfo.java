package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.common.data.Agent;

/**
 * 各エージェントの生死に関わる情報です。
 * @author keisuke
 *
 */
public class ViabilityInfo {
	/**
	 * 生存しているエージェントのリストです。
	 */
	private List<Agent> aliveAgentList;
	/**
	 * 死亡しているエージェントのリストです。
	 */
	private List<Agent> deadAgentList;

	/**
	 * 各エージェントの生死に関わる情報のオブジェクトを構築します。
	 * @param agentList 全エージェントのリスト
	 */
	public ViabilityInfo(List<Agent> agentList) {
		aliveAgentList = new ArrayList<>(agentList);
		deadAgentList = new ArrayList<>();
	}

	/**
	 * 死亡したエージェントを追加します。
	 * @param deadAgent 死亡したエージェント
	 */
	public void addDeadAgent(Agent deadAgent) {
		deadAgentList.add(deadAgent);
		aliveAgentList.remove(deadAgent);
	}

	/**
	 * 指定されたエージェントから生存しているエージェントだけを返します。
	 * @param agentList エージェントのリスト
	 * @return 指定されたエージェントの中で生存しているエージェント
	 */
	public List<Agent> getAliveAgentList(List<Agent> agentList) {
		List<Agent> aliveAgentList = new ArrayList<>(agentList);
		aliveAgentList.retainAll(this.aliveAgentList);
		return aliveAgentList;
	}

	/**
	 * 生存しているエージェントを返します。
	 * @return 生存しているエージェント
	 */
	public List<Agent> getAliveAgentList() {
		return aliveAgentList;
	}
}
