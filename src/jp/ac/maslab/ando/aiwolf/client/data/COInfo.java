package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * COに関する情報です。
 * @author keisuke
 */
public class COInfo {
	/**
	 * エージェントにそのエージェントがCOした役職を関連付けたマップです。
	 */
	private Map<Agent, Role> agentRoleMap;

	/**
	 * 新しくCO情報を構築します。
	 */
	public COInfo() {
		agentRoleMap = new HashMap<>();
	}

	/**
	 * 指定されたエージェントがCOした役職を返します。
	 * @param agent エージェント
	 * @return 指定したエージェントがCOした役職
	 */
	public Role getCORole(Agent agent) {
		return agentRoleMap.get(agent);
	}

	/**
	 * 指定された役職にCOしたエージェントのセットを返します。
	 * @param role 役職
	 * @return 指定した役職にCOしたエージェントのセット
	 */
	public Set<Agent> getCOAgentSet(Role role) {
		Set<Agent> agentSet = new HashSet<>();
		for (Agent agent : agentRoleMap.keySet()) {
			if (agentRoleMap.get(agent).equals(role)) {
				agentSet.add(agent);
			}
		}
		return agentSet;
	}

	/**
	 * COしたエージェントとそのCOに含まれる役職を情報として追加します。
	 * @param agent COしたエージェント
	 * @param role COした役職
	 */
	public void addCOAgentRole(Agent agent, Role role) {
		agentRoleMap.put(agent, role);
	}
}
