package maslab.aiwolf.client;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * 各役職にどのエージェントがカミングアウトしているかをマッピングします。<br>
 * 1人のエージェントが複数の役職にカミングアウトした場合も全て含まれます。
 */
public class RoleComingoutMapper {

	private Map<Role, Set<Agent>> comingoutMap;

	/**
	 * 指定されたマップに各役職がどのエージェントがカミングアウトしているかをマッピングします。
	 *
	 * @param map
	 *            各役職にカミングアウトしたエージェントをマッピングするマップ
	 */
	public RoleComingoutMapper(Map<Role, Set<Agent>> map) {
		this.comingoutMap = map;
	}

	/**
	 * 指定されたカミングアウトから、マップを更新します。
	 *
	 * @param co
	 *            マップを更新するためのカミングアウト
	 */
	public void addComingout(Comingout co) {
		if (!comingoutMap.containsKey(co.getRole())) {
			comingoutMap.put(co.getRole(), new HashSet<>());
		}
		comingoutMap.get(co.getRole()).add(co.getAgent());
	}

	/**
	 * 指定された役職にカミングアウトしたエージェントを返します。
	 *
	 * @param role
	 *            カミングアウトした役職
	 * @return roleのカミングアウトをしたエージェント
	 */
	public Set<Agent> getComingoutAgents(Role role) {
		return comingoutMap.get(role);
	}

	/**
	 * 各役職にその役職をカミングアウトしたエージェントを関連付けたマップを返します。
	 *
	 * @return 各役職にその役職をカミングアウトしたエージェントを関連付けたマップ
	 */
	public Map<Role, Set<Agent>> getComingoutMap() {
		return comingoutMap;
	}

}
