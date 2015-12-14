package maslab.aiwolf.client;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * カミングアウトのクラスです。<br>
 * カミングアウトとは、自分の役職を任意のタイミングで全体に告白することです。COと略されます。
 */
public class Comingout {

	private int day;
	private int talkID;
	private Agent agent;
	private Role role;

	/**
	 * 新しくCOのオブジェクトを構築します。
	 *
	 * @param day
	 *            COした日
	 * @param talkID
	 *            TalkオブジェクトのID
	 * @param agent
	 *            COしたエージェント
	 * @param role
	 *            COした役職
	 */
	public Comingout(int day, int talkID, Agent agent, Role role) {
		this.day = day;
		this.talkID = talkID;
		this.agent = agent;
		this.role = role;
	}

	/**
	 * TalkオブジェクトのIDを返します。
	 *
	 * @return TalkオブジェクトのID
	 */
	public int getTalkID() {
		return talkID;
	}

	/**
	 * COした日を返します。
	 *
	 * @return COした日
	 */
	public int getDay() {
		return day;
	}

	/**
	 * COしたエージェントを返します。
	 *
	 * @return COしたエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * COした役職を返します。
	 *
	 * @return COした役職
	 */
	public Role getRole() {
		return role;
	}

}
