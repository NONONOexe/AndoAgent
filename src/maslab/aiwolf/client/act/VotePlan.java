package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;

/**
 * 投票の意思のクラスです。<br>
 * 会話内で「今晩、誰に投票する」という発言が出たときに使用できます。
 */
public class VotePlan extends TalkAct {

	private final static ActName actName = ActName.VOTE_PLAN;

	/**
	 * 新しく投票の意思のオブジェクトを構築します。
	 * @param day 投票の意思を示した日
	 * @param talkID 投票の意思を示しているTalkオブジェクトのID
	 * @param agent 投票するエージェント
	 * @param target 投票されるエージェント
	 */
	public VotePlan(int day, int talkID, Agent agent, Agent target) {
		super(actName, day, talkID, agent);
		super.target = target;
		super.role = null;
	}

}
