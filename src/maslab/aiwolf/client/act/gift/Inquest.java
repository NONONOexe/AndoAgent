package maslab.aiwolf.client.act.gift;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;

import maslab.aiwolf.client.act.ActName;

/**
 * 霊能者の霊能のクラスです。
 */
public class Inquest extends GiftAct {

	private final static ActName ACT_NAME = ActName.INQUEST;
	private final static Role ROLE = Role.MEDIUM;

	/**
	 * 新規霊能を構築します。
	 * @param day 霊能した日
	 * @param agent 霊能したエージェント
	 * @param target 霊能されたエージェント（前日に処刑されたエージェント）
	 * @param species 霊能の結果
	 */
	public Inquest(int day, Agent agent, Agent target, Species species) {
		super(ACT_NAME, day, agent, ROLE, target);
		setSpecies(species);
	}
}
