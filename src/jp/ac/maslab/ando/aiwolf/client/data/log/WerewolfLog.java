package jp.ac.maslab.ando.aiwolf.client.data.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jp.ac.maslab.ando.aiwolf.client.data.definition.Result;

/**
 * 人狼日記です。<br>
 * 人狼の襲撃の結果を記録します。
 * @author keisuke
 */
public class WerewolfLog {
	private Map<Integer, Result> resultOfDayMap;

	/**
	 * 新しく人狼日記を構築します。
	 */
	public WerewolfLog() {
		resultOfDayMap = new HashMap<>();
	}

	/**
	 * 人狼日記に、日付とその日の襲撃結果を書き込みます。
	 * @param day 襲撃日
	 * @param result 襲撃結果
	 */
	public void write(int day, Result result) {
		resultOfDayMap.put(day, result);
	}

	/**
	 * 指定された日の襲撃結果を返します。
	 * @param day 襲撃日
	 * @return 襲撃結果
	 */
	public Result read(int day) {
		return resultOfDayMap.get(day);
	}

	@Override
	public String toString() {
		String str = "Day Result\n";
		ArrayList<Integer> dayList = new ArrayList<>(resultOfDayMap.keySet());
		for (int i = 0; i < dayList.size(); i++) {
			int day = dayList.get(i);
			str += String.format("%02d  %s", day, resultOfDayMap.get(day));
			if (i < dayList.size() - 1) {
				str += "\n";
			}
		}
		return str;
	}
}
