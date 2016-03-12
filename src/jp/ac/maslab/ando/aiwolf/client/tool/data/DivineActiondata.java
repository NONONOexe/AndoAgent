package jp.ac.maslab.ando.aiwolf.client.tool.data;

import java.util.Arrays;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Species;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.data.definition.GiftTag;

/**
 * 占い結果のデータのクラスです。
 * @author keisuke
 */
public class DivineActionData extends Judge implements ActionData {
	/**
	 * 新しく占い結果のデータを構築します。
	 * @param day 占った日
	 * @param agent 占ったエージェント
	 * @param target 占われたエージェント
	 * @param result 占い結果により判明したエージェントの種族
	 */
	public DivineActionData(int day, Agent agent, Agent target, Species result) {
		super(day, agent, target, result);
	}

	/**
	 * 指定されたJudgeオブジェクトを元に新規占い結果のデータを構築します。
	 * @param judge 元にするJudgeオブジェクト
	 */
	public DivineActionData(Judge judge) {
		super(judge.getDay(), judge.getAgent(), judge.getTarget(), judge.getResult());
	}

	@Override
	public Record convertRecord() {
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TARGET, getTarget());
		record.setValue(Field.ACTION_TAGS, Arrays.asList(GiftTag.DIVINE));
		return record;
	}
}
