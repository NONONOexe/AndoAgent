package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * 役職予想に関する情報を持ちます。
 * @author keisuke
 *
 */
public class RoleForecast {
	/**
	 * 白と推定したエージェントのセットです。
	 */
	private Set<Agent> whiteAgentSet;
	/**
	 * 黒と推定したエージェントのセットです。
	 */
	private Set<Agent> blackAgentSet;
	/**
	 * 白か黒かが推定されていないエージェントのセットです。
	 */
	private Set<Agent> grayAgentSet;
	/**
	 * 役職が推定できているエージェントとその役職です。
	 */
	private Map<Agent, Role> estimateRoleMap;

	/**
	 * 役職予想を構築します。
	 * @param roleNumberMap 役職とその役職の人数
	 * @param agentList ゲームに参加している全エージェントのリスト
	 */
	public RoleForecast(List<Agent> agentList) {
		this.whiteAgentSet = new HashSet<>();
		this.blackAgentSet = new HashSet<>();
		this.grayAgentSet = new HashSet<>();
		this.estimateRoleMap = new HashMap<>();
		grayAgentSet.addAll(agentList);
	}

	/**
	 * 黒と推定します。
	 * @param agent エージェント
	 */
	public void estimateBlack(Agent agent) {
		blackAgentSet.add(agent);
		grayAgentSet.remove(agent);
	}

	/**
	 * 黒と推定します。
	 * @param agentList エージェントのリスト
	 */
	public void estimateBlack(List<Agent> agentList) {
		blackAgentSet.addAll(agentList);
		grayAgentSet.removeAll(agentList);
	}

	/**
	 * 白と推定します。
	 * @param agent エージェント
	 */
	public void estimateWhite(Agent agent) {
		whiteAgentSet.add(agent);
		grayAgentSet.remove(agent);
	}

	/**
	 * 白と推定します。
	 * @param agentList 白と推定するエージェントのリスト
	 */
	public void estimateWhite(List<Agent> agentList) {
		whiteAgentSet.addAll(agentList);
		grayAgentSet.removeAll(agentList);
	}

	/**
	 * エージェントの役職を推定します。
	 * @param agent エージェント
	 * @param role 役職
	 */
	public void estimateRole(Agent agent, Role role) {
		if (role != Role.WEREWOLF) {
			estimateWhite(agent);
		} else {
			estimateBlack(agent);
		}
		estimateRoleMap.put(agent, role);
	}

	/**
	 * 白と推定したエージェントを返します。
	 * @return 白と推定したエージェント
	 */
	public List<Agent> getWhiteAgentList() {
		return new ArrayList<>(whiteAgentSet);
	}

	/**
	 * 黒と推定したエージェントを返します。
	 * @return 黒と推定したエージェント
	 */
	public List<Agent> getBlackAgentList() {
		return new ArrayList<>(blackAgentSet);
	}

	/**
	 * 白か黒かが推定されてないエージェントを返します。
	 * @return 白か黒かが推定されてないエージェント
	 */
	public List<Agent> getGrayAgentList() {
		return new ArrayList<>(grayAgentSet);
	}

	/**
	 * 指定した役職に推定しているエージェントを返します。
	 * @param role 役職
	 * @return 指定した役職に推定しているエージェント
	 */
	public List<Agent> getEstimateList(Role role) {
		List<Agent> estimateAgentList = new ArrayList<>();
		for (Agent agent : estimateRoleMap.keySet()) {
			if (estimateRoleMap.get(agent).equals(role)) {
				estimateAgentList.add(agent);
			}
		}
		return estimateAgentList;
	}
}
