package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;

import maslab.aiwolf.client.act.gift.Result;

/**
 * 人狼におけるエージェントの行動はすべてこのクラスを継承します。
 */
public abstract class Act {

	// 全行動に共通する要素
	private int actID;
	private ActName actName;
	private int day;
	private Agent agent;

	// 能力行使に関係する要素
	private Agent target;
	private Role role;
	private Species species;
	private Result result;

	/**
	 * 新規にエージェントの行動を構築します。
	 * @param actName 行動名
	 * @param day 行動した日
	 * @param agent 行動したエージェント
	 */
	public Act(ActName actName, int day, Agent agent) {
		this.actID = ActIDPublisher.getNextActID();
		this.actName = actName;
		this.day = day;
		this.agent = agent;
	}

	// ====全行動に共通するメソッド====

	/**
	 * この行動のIDを返します。
	 * @return 行動のID
	 */
	public int getActID() {
		return actID;
	}

	/**
	 * 行動名を返します。
	 * @return 行動名
	 */
	public ActName getActName() {
		return actName;
	}

	/**
	 * 行動したエージェントを返します。
	 * @return 行動したエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * 行動した日を返します。
	 * @return 行動した日
	 */
	public int getDay() {
		return day;
	}

	// ====能力の行使関係====

	/**
	 * 能力行使の役職とその対象を設定します。
	 * @param role 能力を行使したエージェントの役職
	 * @param target 能力行使した対象のエージェント
	 */
	protected void setGift(Agent target, Role role) {
		this.target = target;
		this.role = role;
	}

	/**
	 * 占い師の占い結果や霊能者の霊能結果を設定します。
	 * @param species 対象のエージェントの種族
	 */
	protected void setSpecies(Species species) {
		this.species = species;
	}

	/**
	 * 人狼の襲撃や狩人の護衛の結果を設定します。
	 * @param result 襲撃や護衛の結果
	 */
	protected void setResult(Result result) {
		this.result = result;
	}

	/**
	 * 能力を行使したエージェントの役職を返します。
	 * @return 能力を行使したエージェントの役職
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * 能力の行使の対象となるエージェントを返します。
	 * @return 能力の行使の対象となるエージェント
	 */
	public Agent getTarget() {
		return target;
	}

	/**
	 * 占い師の占い結果や霊能者の霊能結果を返します。
	 * @return 行動の結果
	 */
	public Species getSpecies() {
		return species;
	}

	/**
	 * 人狼の襲撃や狩人の護衛の結果を返します。
	 * @return 人狼の襲撃や狩人の護衛の結果
	 */
	public Result getResult() {
		return result;
	}

	@Override
	public String toString() {
		return String.format(
				"%d, %s, %d, %s, %s, %s, %s",
				actID, actName, day, agent,
				target, role, species, result);
	}
}
