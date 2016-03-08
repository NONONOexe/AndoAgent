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
 * 人狼陣営の役職の行動を定義する抽象クラスです。
 * @author keisuke
 */
public abstract class WerewolfSide extends RoleBase {
	/**
	 * 騙る役職です。
	 */
	private Role fakeRole;
	/**
	 * 偽結果です。
	 */
	private List<Judge> fakeJudgeList;
	/**
	 * 偽占いの対象です。
	 */
	private Agent fakeDivineTarget;

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		this.fakeRole = Role.VILLAGER;
		this.fakeJudgeList = new ArrayList<>();
	}

	@Override
	public void dayStart() {
		super.dayStart();
		if (fakeRole == Role.SEER
				&& getDay() != 0
				&& fakeDivineTarget != null) {
			Species result = getEvaluator().getDoubtfulAgentList().contains(fakeDivineTarget) ? Species.WEREWOLF
					: Species.HUMAN;
			this.fakeJudgeList.add(
					new Judge(getLatestDayGameInfo().getDay(), getMe(), fakeDivineTarget, result));
		}

		if (fakeRole.equals(Role.MEDIUM)
				&& getLatestDayGameInfo().getExecutedAgent() != null) {
			Species result = getEvaluator().getDoubtfulAgentList().contains(getLatestDayGameInfo().getExecutedAgent())
					? Species.WEREWOLF : Species.HUMAN;
			this.fakeJudgeList.add(
					new Judge(getLatestDayGameInfo().getDay(), getMe(), getLatestDayGameInfo().getExecutedAgent(),
							result));
		}
	}

	/**
	 * 偽結果を返します。
	 * @return 偽結果
	 */
	public List<Judge> getFakeJudgeList() {
		return fakeJudgeList;
	}

	/**
	 * 指定されたエージェントが偽結果に含まれているかどうかを返します。
	 * @param agent エージェント
	 * @return 指定されたエージェントが偽結果に含まれているかどうか
	 */
	public boolean isJuggedAgent(Agent agent) {
		for (Judge judge : getFakeJudgeList()) {
			if (judge.getTarget() == agent) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定された種族が今までの結果で出したことがあるかを返します。
	 * @param species 種族
	 * @return 指定された種族が今までの結果で出したことがあるか
	 */
	public boolean containsInJudge(Species species) {
		for (Judge judge : getFakeJudgeList()) {
			if (judge.getResult().equals(species)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 偽役職を設定します。
	 * @param fakeRole 偽役職
	 */
	public void setFakeRole(Role fakeRole) {
		this.fakeRole = fakeRole;
	}

	/**
	 * 偽占いの対象を設定します。
	 * @param fakeDivineTarget 偽占いの対象
	 */
	public void setFakeDivineTarget(Agent fakeDivineTarget) {
		this.fakeDivineTarget = fakeDivineTarget;
	}

	@Override
	public final Agent divine() {
		return null;
	}

	@Override
	public Agent guard() {
		return null;
	}
}
