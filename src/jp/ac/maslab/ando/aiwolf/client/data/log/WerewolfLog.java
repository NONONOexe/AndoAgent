package jp.ac.maslab.ando.aiwolf.client.data.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.util.Pair;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Result;

/**
 * 人狼日記です。<br>
 * 人狼の襲撃の結果を記録します。
 * @author keisuke
 */
public class WerewolfLog {
	private Map<Integer, Pair<Agent, Result>> attackOfDayMap;

	/**
	 * 新しく人狼日記を構築します。
	 */
	public WerewolfLog() {
		attackOfDayMap = new HashMap<>();
	}

	/**
	 * 人狼日記に、日付と襲撃したエージェントとその日の襲撃結果を書き込みます。
	 * @param day 襲撃日
	 * @param agent 襲撃したエージェント
	 * @param result 襲撃結果
	 */
	public void write(int day, Agent agent, Result result) {
		attackOfDayMap.put(day, new Pair<>(agent, result));
	}

	/**
	 * 指定された日に襲撃したエージェントとその襲撃結果の{@code Pair<Agent, Result>}を返します。
	 * @param day 襲撃日
	 * @return その日の襲撃情報
	 */
	public Pair<Agent, Result> read(int day) {
		return attackOfDayMap.get(day);
	}

	@Override
	public String toString() {
		String string = "";
		ArrayList<Integer> dayList = new ArrayList<>(attackOfDayMap.keySet());
		for (int i = 0; i < dayList.size(); i++) {
			int day = dayList.get(i);
			string += String.format("Day%02d\t%s->%s", day, attackOfDayMap.get(day).getKey(),
					attackOfDayMap.get(day).getValue());
			if (i != dayList.size() - 1) {
				string += "\n";
			}
		}
		return string;
	}
}
