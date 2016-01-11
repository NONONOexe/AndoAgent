package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;

/**
 * 投票の行動クラスです。
 */
public class ExecutionVote extends VoteAct {

	private final static ActName actName = ActName.EXECUTION_VOTE;

	/**
	 * 新規投票を構築します。
	 * @param day 投票した日
	 * @param agent 投票したエージェント
	 * @param voted 投票されたエージェント
	 */
	public ExecutionVote(int day, Agent agent, Agent voted) {
		super(actName, day, agent, voted);
	}

}
