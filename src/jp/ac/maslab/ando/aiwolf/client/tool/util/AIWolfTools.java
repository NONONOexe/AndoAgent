package jp.ac.maslab.ando.aiwolf.client.tool.util;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.common.data.Agent;

/**
 * 人狼知能で使用する便利な関数を含むクラスです。
 * @author keisuke
 */
public class AIWolfTools {
	/**
	 * ログです。
	 */
	public static String debugLog = "";

	public static void println(Object obj) {
		debugLog += obj.toString() + "\n";
	}

	/**
	 * 指定された2つのエージェントのリストの両方に含まれているエージェントをリストで返します。
	 * @param agentList1 エージェントのリスト1
	 * @param agentList2 エージェントのリスト2
	 * @return 指定された2つのエージェントのリストの両方に含まれているエージェントのリスト
	 */
	public static List<Agent> getIntersectionAgentList(List<Agent> agentList1, List<Agent> agentList2) {
		List<Agent> intersectionAgentList = new ArrayList<>(agentList1);
		intersectionAgentList.retainAll(agentList2);
		return intersectionAgentList;
	}
}
