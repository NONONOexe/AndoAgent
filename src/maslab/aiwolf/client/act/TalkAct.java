package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * エージェントの発言です。<br>
 * 発言である行動クラスはこの抽象クラスを継承します。
 */
public abstract class TalkAct extends Act {

	private int talkID;
	protected Agent target;
	protected Role role;

	/**
	 * 新規に発言を構築します。
	 * @param actName 行動名
	 * @param day 発言した日
	 * @param talkID 発言ID
	 * @param agent 発言したエージェント
	 */
	public TalkAct(ActName actName, int day, int talkID, Agent agent) {
		super(actName, day, agent);
		this.talkID = talkID;
	}

	/**
	 * 発言のIDを返します。
	 * @return 疑い発言のID
	 */
	public int getTalkID() {
		return talkID;
	}

	/**
	 * 話している対象のエージェントを返します。
	 * @return 話している対象のエージェント
	 */
	public Agent getTarget() {
		return target;
	}

	/**
	 * 発言に含まれる役職を返します。
	 * @return 発言に含まれている役職
	 */
	public Role getRole() {
		return role;
	}

}
