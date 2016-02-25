package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aiwolf.client.base.player.AbstractRole;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.data.Vote;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.data.AgentInfo;
import jp.ac.maslab.ando.aiwolf.client.data.ScoreManager;
import jp.ac.maslab.ando.aiwolf.client.data.action.ActionDataProcesser;
import jp.ac.maslab.ando.aiwolf.client.data.action.TalkActionData;
import jp.ac.maslab.ando.aiwolf.client.data.action.VoteActionData;
import jp.ac.maslab.ando.aiwolf.client.data.definition.Result;
import jp.ac.maslab.ando.aiwolf.client.data.log.BodyguardLog;
import jp.ac.maslab.ando.aiwolf.client.data.log.WerewolfLog;
import jp.ac.maslab.ando.aiwolf.client.tool.ClientSystem;
import jp.ac.maslab.ando.aiwolf.client.tool.DebugPrinter;
import jp.ac.maslab.ando.aiwolf.client.tool.RunMode;

/**
 * 全役職に共通する処理を行うクラスです。
 * @author keisuke
 */
public abstract class RoleBase extends AbstractRole {
	private int readTalkNum;
	private AgentInfo agentInfo;
	private WerewolfLog werewolfLog;
	private BodyguardLog bodyguardLog;
	private ActionDataProcesser actiondataProcesser;

	/**
	 * 各エージェントに対する疑い度を管理します。
	 */
	private ScoreManager scoreManager;

	/**
	 * 全役職の共通戦略を構築します。
	 */
	public RoleBase() {
		scoreManager = new ScoreManager(getAgentList(), getMe());
	}

	/**
	 * 人狼日記を付けます。
	 */
	private void writeWerewolfLog() {
		GameInfo gameInfo = getLatestDayGameInfo();
		Agent attackedAgent = gameInfo.getAttackedAgent();
		int day = getDay() - 1;
		if (1 <= day) {
			Result result = attackedAgent != null ? Result.SUCCESS : Result.FAILURE;
			werewolfLog.write(day, attackedAgent, result);
		}
	}

	/**
	 * 狩人日記を付けます
	 */
	private void writeBodyGuardLog() {
		GameInfo gameInfo = getLatestDayGameInfo();
		Agent guardedAgent = gameInfo.getGuardedAgent();
		Agent attackedAgent = gameInfo.getAttackedAgent();
		int day = getDay() - 1;
		if (1 <= day) {
			Result result = attackedAgent == null ? Result.SUCCESS : Result.FAILURE;
			bodyguardLog.write(day, guardedAgent, result);
		}
	}

	/**
	 * 投票データ作成し、プロセッサに追加します。
	 */
	private void addVoteActiondataToActiondataProcesser() {
		List<Vote> voteList = getLatestDayGameInfo().getVoteList();
		for (Vote vote : voteList) {
			actiondataProcesser.addActionData(new VoteActionData(vote));
		}
	}

	private void debugPrint() {
		DebugPrinter.println("=============================================");
		DebugPrinter.println("WEREWOLF_LOG");
		DebugPrinter.println(werewolfLog);
		DebugPrinter.println("=============================================");
		DebugPrinter.println("BODYGUARD_LOG");
		DebugPrinter.println(bodyguardLog);
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		ClientSystem clientSystem = ClientSystem.getInstance();
		// TODO 提出時はCONTESTモードに切り替え
		// clientSystem.setMode(RunMode.CONTEST);
		clientSystem.setMode(RunMode.DEBUG);
		werewolfLog = new WerewolfLog();
		bodyguardLog = new BodyguardLog();
		actiondataProcesser = new ActionDataProcesser();
		agentInfo = new AgentInfo(gameInfo, gameSetting);
	}

	@Override
	public void dayStart() {
		readTalkNum = 0;
		writeWerewolfLog();
		writeBodyGuardLog();
		addVoteActiondataToActiondataProcesser();
		debugPrint();
	}

	@Override
	public void finish() {
		writeWerewolfLog();
		writeBodyGuardLog();
		addVoteActiondataToActiondataProcesser();
		debugPrint();
		try {
			actiondataProcesser.exportCSVFile("./csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameInfo gameInfo) {
		super.update(gameInfo);
		List<Talk> talkList = gameInfo.getTalkList();
		while (readTalkNum < gameInfo.getTalkList().size()) {
			Talk talk = talkList.get(readTalkNum);
			actiondataProcesser.addActionData(new TalkActionData(talk));
			readTalkNum++;
		}
	}

	/**
	 * 人狼日記を返します。
	 * @return 人狼日記
	 */
	public WerewolfLog getWerewolfLog() {
		return werewolfLog;
	}

	/**
	 * 参加しているすべてのエージェントのリストを返します。
	 * @return 参加しているすべてのエージェントのリスト
	 */
	public List<Agent> getAgentList() {
		return getLatestDayGameInfo().getAgentList();
	}

	/**
	 * 生存しているエージェントのリストを返します。
	 * @return 生存しているエージェントのリスト
	 */
	public List<Agent> getAliveAgentList() {
		return getLatestDayGameInfo().getAliveAgentList();
	}

	/**
	 * すでに襲撃や処刑などによって死亡したエージェントのリストを返します。
	 * @return すでに死亡したエージェントのリスト
	 */
	public List<Agent> getDeadAgentList() {
		GameInfo gameInfo = getLatestDayGameInfo();
		List<Agent> deadAgentList = new ArrayList<>(gameInfo.getAgentList());
		deadAgentList.removeAll(gameInfo.getAliveAgentList());
		return deadAgentList;
	}

	/**
	 * 指定されたエージェントがすでに死んでいるかどうかを返します。
	 * @param agent 死亡しているかを確認するエージェント
	 * @return 指定されたエージェントが死んでいるかどうか
	 */
	public boolean isDead(Agent agent) {
		return getDeadAgentList().contains(agent);
	}

	/**
	 * 指定されたエージェントが生存しているかどうかを返します。
	 * @param agent 生存しているかを確認するエージェント
	 * @return 指定されたエージェントが生きているかどうか
	 */
	public boolean isAlive(Agent agent) {
		return getLatestDayGameInfo().getAliveAgentList().contains(agent);
	}

	/**
	 * 最も疑わしいエージェントのリストを返します。
	 * @return 最も疑わしいエージェントのリスト
	 */
	public List<Agent> getMostSuspiciousAgentList() {
		return scoreManager.getMostSuspiciousAgentList();
	}

	/**
	 * 最も信頼できるエージェントのリストを返します。
	 * @return 最も信頼できるエージェントのリスト
	 */
	public List<Agent> getMostTrustedAgent() {
		return scoreManager.getMostTrustedAgentList();
	}
}
