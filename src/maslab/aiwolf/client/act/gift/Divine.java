package maslab.aiwolf.client.act.gift;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;

import maslab.aiwolf.client.act.ActName;

/**
 * 占い師の占いのクラスです。
 */
public class Divine extends GiftAct {

	private final static ActName ACT_NAME = ActName.DIVINE;
	private final static Role ROLE = Role.SEER;

	/**
	 * 新規占いを構築します。
	 * @param day 占った日
	 * @param agent 占ったエージェント
	 * @param target 占われたエージェント
	 * @param species 占いの結果
	 */
	public Divine(int day, Agent agent, Agent target, Species species) {
		super(ACT_NAME, day, agent, ROLE, target);
		setSpecies(species);
	}

}
