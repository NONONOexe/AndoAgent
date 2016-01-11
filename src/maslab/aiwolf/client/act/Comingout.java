package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * カミングアウトのクラスです。<br>
 * カミングアウトとは、自分の役職を任意のタイミングで全体に告白することです。COと略されます。
 */
public final class Comingout extends TalkAct {

	private final static ActName actName = ActName.COMINGOUT;

	/**
	 * 新しくCOのオブジェクトを構築します。
	 * @param day COした日
	 * @param talkID TalkオブジェクトのID
	 * @param agent COしたエージェント
	 * @param role COした役職
	 */
	public Comingout(int day, int talkID, Agent agent, Role role) {
		super(actName, day, talkID, agent);
		super.target = agent;
		super.role = role;
	}

}
