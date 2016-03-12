package jp.ac.maslab.ando.aiwolf.client.tool.data;

import java.util.Arrays;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Species;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.data.definition.GiftTag;

/**
 * 霊能結果のデータのクラスです。
 * @author keisuke
 */
public class InquestActionData extends Judge implements ActionData {
	/**
	 * 新しく霊能結果のデータを構築します。
	 * @param day 霊能した日
	 * @param agent 霊能したエージェント
	 * @param target 霊能されたエージェント
	 * @param result 霊能結果により判明したエージェントの種族
	 */
	public InquestActionData(int day, Agent agent, Agent target, Species result) {
		super(day, agent, target, result);
	}

	/**
	 * 指定されたJudgeオブジェクトを元に新規霊能結果のデータを構築します。
	 * @param judge 元にするJudgeオブジェクト
	 */
	public InquestActionData(Judge judge) {
		super(judge.getDay(), judge.getAgent(), judge.getTarget(), judge.getResult());
	}

	@Override
	public Record convertRecord() {
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TARGET, getTarget());
		record.setValue(Field.ACTION_TAGS, Arrays.asList(GiftTag.INQUEST));
		return record;
	}
}
