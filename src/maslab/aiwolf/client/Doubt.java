package maslab.aiwolf.client;

import org.aiwolf.common.data.Agent;

/**
 * 疑いのクラスです。<br>
 * 誰が誰を疑っているかを保持します。
 */
public class Doubt {
	private int day;
	private int talkID;
	private Agent agent;
	private Agent target;

	/**
	 * 新しく疑いオブジェクトを構築します。
	 *
	 * @param day
	 *            疑いを発言した日
	 * @param talkID
	 *            疑い発言のID
	 * @param agent
	 *            疑っているエージェント
	 * @param target
	 *            疑われているエージェント
	 */
	public Doubt(int day, int talkID, Agent agent, Agent target) {
		this.day = day;
		this.talkID = talkID;
		this.agent = agent;
		this.target = target;
	}

	/**
	 * 疑いを発言した日を返します。
	 *
	 * @return 疑いを発言した日
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 疑い発言のIDを返します。
	 *
	 * @return 疑い発言のID
	 */
	public int getTalkID() {
		return talkID;
	}

	/**
	 * 疑っているエージェントを返します。
	 *
	 * @return 疑っているエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * 疑われているエージェントを返します。
	 *
	 * @return 疑われているエージェント
	 */
	public Agent getTarget() {
		return target;
	}

}
