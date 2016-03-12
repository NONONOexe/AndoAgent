package jp.ac.maslab.ando.aiwolf.client.tool.data;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Vote;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;

/**
 * 投票のデータのクラスです。
 * @author keisuke
 */
public class VoteActionData extends Vote implements ActionData {
	/**
	 * 新しく投票のデータを構築します。
	 * @param day 投票した日
	 * @param agent 投票したエージェント
	 * @param target 投票されたエージェント
	 */
	public VoteActionData(int day, Agent agent, Agent target) {
		super(day, agent, target);
	}

	/**
	 * 指定されたVoteオブジェクトを元に新規投票データを構築します。
	 * @param vote 元にするVoteオブジェクト
	 */
	public VoteActionData(Vote vote) {
		super(vote.getDay(), vote.getAgent(), vote.getTarget());
	}

	@Override
	public Record convertRecord() {
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TARGET, getTarget());
		return record;
	}
}
