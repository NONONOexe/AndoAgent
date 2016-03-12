package jp.ac.maslab.ando.aiwolf.client.tool.data;

import java.util.Arrays;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Guard;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.data.definition.GiftTag;

/**
 * 護衛のデータのクラスです。
 * @author keisuke
 */
public class GuardActionData extends Guard implements ActionData {
	/**
	 * 新しく護衛のデータを構築します。
	 * @param day 護衛した日
	 * @param agent 護衛したエージェント
	 * @param target 護衛されたエージェント
	 */
	public GuardActionData(int day, Agent agent, Agent target) {
		super(day, agent, target);
	}

	/**
	 * 指定されたGuardオブジェクトを元に新規護衛のデータを構築します。
	 * @param guard 元にするGuardオブジェクト
	 */
	public GuardActionData(Guard guard) {
		super(guard.getDay(), guard.getAgent(), guard.getTarget());
	}

	@Override
	public Record convertRecord() {
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TARGET, getTarget());
		record.setValue(Field.ACTION_TAGS, Arrays.asList(GiftTag.GUARD));
		return record;
	}

}
