package maslab.aiwolf.client.act.gift;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

import maslab.aiwolf.client.act.Act;
import maslab.aiwolf.client.act.ActName;

/**
 * 能力者の能力の行使です。<br>
 * 能力者の能力の行使である行動クラスはこの抽象クラスを継承します。
 */
public abstract class GiftAct extends Act {

	/**
	 * 新規に能力の行使を構築します。
	 * @param actName 行動名
	 * @param day 能力を行使した日
	 * @param agent 能力を行使したエージェント
	 * @param role 能力を行使したエージェントの役職
	 * @param target 行使の対象となるエージェント
	 */
	public GiftAct(ActName actName, int day, Agent agent, Role role, Agent target) {
		super(actName, day, agent);
		setGift(target, role);
	}

}
