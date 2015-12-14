package maslab.aiwolf.client;

import java.util.Map;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * 各エージェントがどの役職にカミングアウトしているかをマップ化します。<br>
 * 1人のエージェントが複数の役職にカミングアウトした場合は最後にカミングアウトした役職だけが保持されます。
 */
public final class AgentComingoutMapper {

	private Map<Agent, Role> comingoutMap;

	/**
	 * 指定されたマップに各エージェントがどの役職にカミングアウトしているかをマッピングします。
	 *
	 * @param map
	 *            各エージェントのカミングアウトした役職をマッピングするマップ
	 */
	public AgentComingoutMapper(Map<Agent, Role> map) {
		this.comingoutMap = map;
	}

	/**
	 * 指定されたカミングアウトからマップを更新します。すでにカミングアウトしたエージェントの場合は、以前にカミングアウトした役職を返します。
	 *
	 * @param co
	 *            カミングアウトの情報
	 * @return カミングアウトしたエージェントが以前にカミングアウトした役職
	 */
	public Role addComingout(Comingout co) {
		return this.comingoutMap.put(co.getAgent(), co.getRole());
	}

	/**
	 * 指定されたエージェントがカミングアウトした役職を返します。カミングアウトしていないエージェントが渡された場合には、nullを返します。
	 *
	 * @param agent
	 *            カミングアウトした役職を返すエージェント
	 * @return agentがカミングアウトした役職
	 */
	public Role getComingoutRole(Agent agent) {
		if (this.comingoutMap.containsKey(agent)) {
			return this.comingoutMap.get(agent);
		} else {
			return null;
		}
	}

	/**
	 * 各エージェントがどの役職にカミングアウトしているかを関連付けたマップを返します。
	 *
	 * @return 各エージェントがどの役職にカミングアウトしているかを関連付けたマップ
	 */
	public Map<Agent, Role> getComingoutMap() {
		return comingoutMap;
	}

}
