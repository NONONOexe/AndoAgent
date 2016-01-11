package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;

/**
 * 疑いのクラスです。<br>
 * 誰が誰を疑っているかを保持します。
 */
public final class Doubt extends TalkAct {

	private final static ActName actName = ActName.DOUBT;

	/**
	 * 新しく疑いオブジェクトを構築します。
	 * @param day 疑いを発言した日
	 * @param talkID 疑い発言のID
	 * @param agent 疑っているエージェント
	 * @param target 疑われているエージェント
	 */
	public Doubt(int day, int talkID, Agent agent, Agent target) {
		super(actName, day, talkID, agent);
		super.target = target;
		super.role = null;
	}

}
