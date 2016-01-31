package jp.ac.maslab.ando.aiwolf.client.data.action;

import java.util.Arrays;

import org.aiwolf.common.data.Agent;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Field;
import jp.ac.maslab.ando.aiwolf.client.data.definition.GiftTag;

/**
 * 人狼の襲撃のデータのクラスです。
 * @author keisuke
 */
public class AttackActiondata implements Actiondata {
	private int day;
	private Agent agent;
	private Agent target;

	/**
	 * 新しく人狼の襲撃のデータを構築します。
	 * @param day 襲撃した日
	 * @param agent 襲撃するエージェント
	 * @param target 襲撃されたエージェント
	 */
	public AttackActiondata(int day, Agent agent, Agent target) {
		this.day = day;
		this.agent = agent;
		this.target = target;
	}

	/**
	 * 襲撃した日を返します。
	 * @return 襲撃した日
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 襲撃したエージェントを返します。
	 * @return 襲撃したエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * 襲撃されたエージェントを返します。
	 * @return 襲撃されたエージェント
	 */
	public Agent getTarget() {
		return target;
	}

	@Override
	public Record convertRecord() {
		Record record = new Record();
		record.setValue(Field.DAY, getDay());
		record.setValue(Field.AGENT, getAgent());
		record.setValue(Field.DATA_TYPE, getClass().getSuperclass().getSimpleName());
		record.setValue(Field.TARGET, getTarget());
		record.setValue(Field.ACTION_TAGS, Arrays.asList(GiftTag.ATTACK));
		return record;
	}

}
