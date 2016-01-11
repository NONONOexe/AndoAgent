package maslab.aiwolf.client.act;

import org.aiwolf.common.data.Agent;

/**
 * 一日の終わりの投票です。<br>
 * 投票である行動クラスはこの抽象クラスを継承します。
 */
public abstract class VoteAct extends Act {

	private Agent target;

	/**
	 * 新しく投票のオブジェクトを構築します。
	 * @param day 投票した日
	 * @param agent 投票したエージェント
	 * @param voted 投票されたエージェント
	 */
	public VoteAct(ActName actName, int day, Agent agent, Agent voted) {
		super(actName, day, agent);
		this.target = voted;
	}

	/**
	 * 投票されたエージェントを返します。
	 * @return 投票されたエージェント
	 */
	public Agent getTarget() {
		return target;
	}
}
