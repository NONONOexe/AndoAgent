package jp.ac.maslab.ando.aiwolf.client.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * COに関する情報を保持します。
 * @author keisuke
 */
public class COInfo {
	/**
	 * エージェントにCOした役職を関連付けたマップです。
	 */
	private Map<Agent, Role> agentRoleMap;
	/**
	 * 今までされた全てのCOを保持するリストです。
	 */
	private List<Comingout> coList;
	/**
	 * 各役職とその設定人数を関連付けたマップです。
	 */
	private Map<Role, Integer> roleNumberMap;

	/**
	 * COに関する情報を保持するオブジェクトを構築します。
	 */
	public COInfo(Map<Role, Integer> roleNumberMap) {
		this.agentRoleMap = new HashMap<>();
		this.coList = new ArrayList<>();
		this.roleNumberMap = roleNumberMap;
	}

	/**
	 * カミングアウトを追加します。
	 * @param co カミングアウト
	 */
	public void addComingout(Comingout co) {
		agentRoleMap.put(co.getAgent(), co.getRole());
		coList.add(co);
	}

	/**
	 * COしているエージェントをリストで返します。
	 * @return COしているエージェントのリスト
	 */
	public List<Agent> getCOAgentList() {
		return new ArrayList<>(agentRoleMap.keySet());
	}

	/**
	 * 指定された役職にCOしているエージェントのリストを返します。指定された役職にCOしたエージェントが存在しない場合は空のリストを返します。
	 * @param role 役職
	 * @return 指定された役職にCOしているエージェントのリスト
	 */
	public List<Agent> getCOAgentList(Role role) {
		List<Agent> coAgentList = new ArrayList<>();
		for (Agent agent : getCOAgentList()) {
			if (agentRoleMap.get(agent).equals(role)) {
				coAgentList.add(agent);
			}
		}
		return coAgentList;
	}

	/**
	 * 指定されたエージェントがCOしている役職を返します。指定されたエージェントがCOしていない場合はnullを返します。
	 * @param agent エージェント
	 * @return 指定されたエージェントがCOしている役職
	 */
	public Role getCORole(Agent agent) {
		if (agentRoleMap.containsKey(agent)) {
			return agentRoleMap.get(agent);
		} else {
			return null;
		}
	}

	/**
	 * 指定された役職にCOしている人数を返します。
	 * @param role 役職
	 * @return 指定された役職にCOしている人数
	 */
	public int getNumberOfCOAgent(Role role) {
		int numberOfCOAgent = 0;
		for (Agent agent : agentRoleMap.keySet()) {
			if (agentRoleMap.get(agent).equals(role)) {
				numberOfCOAgent++;
			}
		}
		return numberOfCOAgent;
	}

	/**
	 * 指定されたエージェントがCOしているかどうかを返します。
	 * @param agent エージェント
	 * @return 指定されたエージェントがCOしているかどうか
	 */
	public boolean hasTalkedCO(Agent agent) {
		return agentRoleMap.containsKey(agent);
	}

	/**
	 * 指定した役職について設定人数よりCOしたエージェントのほうが多いかどうかを返します。
	 * @param role 役職
	 * @return 設定人数よりCOしたエージェントのほうが多いならtrue、そうでない場合はfalse
	 */
	public boolean isOverCapatityCORole(Role role) {
		return roleNumberMap.get(role) < getNumberOfCOAgent(role);
	}
}
