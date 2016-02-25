package jp.ac.maslab.ando.aiwolf.client.data.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.util.Pair;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Result;

/**
 * 狩人日記です。<br>
 * 狩人の護衛の結果を記録します。
 * @author keisuke
 */
public class BodyguardLog {
	private Map<Integer, Pair<Agent, Result>> guardOfDayMap;

	/**
	 * 新しく狩人日記を構築します。
	 */
	public BodyguardLog() {
		guardOfDayMap = new HashMap<>();
	}

	/**
	 * 狩人日記に、日付とその日の護衛結果を書き込みます。
	 * @param day 護衛日
	 * @param result 護衛結果
	 */
	public void write(int day, Agent agent, Result result) {
		guardOfDayMap.put(day, new Pair<>(agent, result));
	}

	/**
	 * 指定された日に護衛したエージェントとその護衛結果の{@code Pair<Agent, Result>}を返します。
	 * @param day 護衛日
	 * @return その日の護衛情報
	 */
	public Pair<Agent, Result> read(int day) {
		return guardOfDayMap.get(day);
	}

	@Override
	public String toString() {
		String string = "";
		ArrayList<Integer> dayList = new ArrayList<>(guardOfDayMap.keySet());
		for (int i = 0; i < dayList.size(); i++) {
			int day = dayList.get(i);
			string += String.format("Day%02d\t%s->%s", day, guardOfDayMap.get(day).getKey(),
					guardOfDayMap.get(day).getValue());
			if (i != dayList.size() - 1) {
				string += "\n";
			}
		}
		return string;
	}
}
