package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.aiwolf.common.data.Agent;

/**
 * 投票先を時間で管理します。
 * @author keisuke
 */
public class VoteTargetManager {
	/**
	 * 時間を取得するためのオブジェクトです。
	 */
	private TimeManager timeManager;
	/**
	 * 時間ごとの投票先のマップです。
	 */
	private Map<Time, Agent> timeTargetMap;

	/**
	 * 投票先を決定するためのオブジェクトを構築します。
	 * @param timeManager 時間を取得するためのオブジェクト
	 */
	public VoteTargetManager(TimeManager timeManager) {
		this.timeManager = timeManager;
		this.timeTargetMap = new LinkedHashMap<>();
	}

	/**
	 * 今日の投票先が決定しているかどうかを返します。
	 * @return 今日の投票先が決定しているならtrue、そうでない場合はfalse
	 */
	public boolean isDecided() {
		int today = timeManager.getCurrent().getDay();
		for (Time time : timeTargetMap.keySet()) {
			if (time.getDay() == today) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 投票先を設定します。
	 * @param voteTarget 投票先のエージェント
	 */
	public void setVoteTarget(Agent voteTarget) {
		if (voteTarget == null) {
			return;
		}
		this.timeTargetMap.put(timeManager.getCurrent(), voteTarget);
	}

	/**
	 * 今日最後に設定した投票先を返します。今日の投票先がない場合はnullを返します。
	 * @return 投票先
	 */
	public Agent getVoteTarget() {
		if (!isDecided()) {
			return null;
		}
		ArrayList<Time> timeList = new ArrayList<>(timeTargetMap.keySet());
		Collections.sort(timeList, new TimeComparator());
		Time lastTime = timeList.get(timeList.size() - 1);
		return timeTargetMap.get(lastTime);
	}

	/**
	 * 前のターンと今のターンの投票先を比較して変更があったかどうかを返します。
	 * 前のターンまたは今のターンの投票先が設定されていない場合はfalseを返します。
	 * @return 前のターンと今のターンを比較して変更があればtrue、そうでない場合はfalse
	 */
	public boolean hasChangedTarget() {
		Time current = timeManager.getCurrent();
		Time before = new Time(current.getDay(), current.getTurn() - 1);
		if (!timeTargetMap.containsKey(current) || !timeTargetMap.containsKey(before)) {
			return false;
		}
		return !timeTargetMap.get(current).equals(timeTargetMap.get(before));
	}
}
