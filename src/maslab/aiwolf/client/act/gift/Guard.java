package maslab.aiwolf.client.act.gift;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

import maslab.aiwolf.client.act.ActName;

/**
 * 狩人の護衛のクラスです。
 */
public class Guard extends GiftAct {

	private final static ActName ACT_NAME = ActName.GUARD;
	private final static Role ROLE = Role.BODYGUARD;

	/**
	 * 新規護衛を構築します。
	 * @param day 護衛した日
	 * @param agent 護衛したエージェント
	 * @param target 護衛されたエージェント
	 * @param result 護衛の結果
	 */
	public Guard(int day, Agent agent, Agent target, Result result) {
		super(ACT_NAME, day, agent, ROLE, target);
		setResult(result);
	}

}
