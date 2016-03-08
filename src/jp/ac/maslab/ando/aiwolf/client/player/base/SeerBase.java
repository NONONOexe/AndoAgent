package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.ArrayList;
import java.util.List;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Judge;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

/**
 * 占い師の基本行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class SeerBase extends VillagerSide {
	/**
	 * 占い結果です。
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
		if (getLatestDayGameInfo().getDivineResult() != null) {
			this.myJudgeList.add(getLatestDayGameInfo().getDivineResult());

			switch (getLatestDayGameInfo().getDivineResult().getResult()) {
			case HUMAN:
				getRoleForecast().estimateWhite(getLatestDayGameInfo().getDivineResult().getTarget());
				break;
			case WEREWOLF:
				getRoleForecast().estimateRole(getLatestDayGameInfo().getDivineResult().getTarget(), Role.WEREWOLF);
				break;
			}
		}
	}

	/**
	 * 占い結果のリストを返します。
	 * @return 占い結果のリスト
	 */
	public List<Judge> getMyJudgeList() {
		return myJudgeList;
	}

	/**
	 * 指定されたエージェントを占ったかどうかを返します。
	 * @param agent エージェント
	 * @return 指定されたエージェントを占ったかどうか
	 */
	public boolean isJuggedAgent(Agent agent) {
		for (Judge judge : getMyJudgeList()) {
			if (judge.getTarget() == agent) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final Agent guard() {
		return null;
	}
}
