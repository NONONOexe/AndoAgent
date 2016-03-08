package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Species;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

/**
 * 霊能者の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class MediumBase extends VillagerSide {
	/**
	 * 霊能結果です。
	 */
	private List<Judge> myJudgeList;

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		myJudgeList = new ArrayList<>();
	}

	@Override
	public void dayStart() {
		super.dayStart();
		if (getLatestDayGameInfo().getMediumResult() != null) {
			this.myJudgeList.add(getLatestDayGameInfo().getMediumResult());

			switch (getLatestDayGameInfo().getMediumResult().getResult()) {
			case HUMAN:
				getRoleForecast().estimateWhite(getLatestDayGameInfo().getMediumResult().getTarget());
				break;
			case WEREWOLF:
				getRoleForecast().estimateRole(getLatestDayGameInfo().getMediumResult().getTarget(), Role.WEREWOLF);
				break;
			}
		}
	}

	/**
	 * 霊能結果を返します。
	 * @return 霊能結果
	 */
	public List<Judge> getMyJudgeList() {
		return myJudgeList;
	}

	/**
	 * 指定されたエージェントを霊能したかどうかを返します。
	 * @param agent エージェント
	 * @return 指定されたエージェントを霊能したかどうか
	 */
	public boolean isJuggedAgent(Agent agent) {
		for (Judge judge : getMyJudgeList()) {
			if (judge.getTarget() == agent) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定された種族が今までの霊能で出たことがあるかを返します。
	 * @param species 種族
	 * @return 指定された種族が今までの霊能で出たことがあるか
	 */
	public boolean containsInJudge(Species species) {
		for (Judge judge : getMyJudgeList()) {
			if (judge.getResult().equals(species)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Agent divine() {
		return null;
	}

	@Override
	public final Agent guard() {
		return null;
	}
}
