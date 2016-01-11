package maslab.aiwolf.client.act.gift;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

import maslab.aiwolf.client.act.ActName;

/**
 * 人狼の襲撃のクラスです。
 */
public class Attack extends GiftAct {

	private final static ActName ACT_NAME = ActName.ATTACK;
	private final static Role ROLE = Role.WEREWOLF;

	/**
	 * 新規襲撃を構築します。
	 * @param day 襲撃した日
	 * @param agent 襲撃したエージェント
	 * @param target 襲撃されたエージェント
	 * @param result 襲撃の結果
	 */
	public Attack(int day, Agent agent, Agent target, Result result) {
		super(ACT_NAME, day, agent, ROLE, target);
		setResult(result);
	}

}
