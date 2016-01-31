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

import jp.ac.maslab.ando.aiwolf.client.data.action.ActiondataProcesser;
import jp.ac.maslab.ando.aiwolf.client.data.action.TalkActiondata;
import jp.ac.maslab.ando.aiwolf.client.data.action.VoteActiondata;
import jp.ac.maslab.ando.aiwolf.client.data.definition.Result;
import jp.ac.maslab.ando.aiwolf.client.data.log.WerewolfLog;
import jp.ac.maslab.ando.aiwolf.client.tool.ClientSystem;
import jp.ac.maslab.ando.aiwolf.client.tool.RunMode;

/**
 * 全役職に共通する処理を行うクラスです。
 * @author keisuke
 */
public abstract class RoleBase extends AbstractRole {
	private int readTalkNum;
	private WerewolfLog werewolfLog;
	private ActiondataProcesser actiondataProcesser;

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

	// 人狼日記を付けます
	private void writeWerewolfLog() {
		GameInfo gameInfo = getLatestDayGameInfo();
		Agent attackedAgent = gameInfo.getAttackedAgent();
		int day = getDay() - 1;
		if (day < 1) {
			return;
		}
		Result result = isDead(attackedAgent) ? Result.SUCCESS : Result.FAILURE;
		werewolfLog.write(day, result);
	}

	// 投票データをプロセッサに加えます
	private void addVoteActiondataToActiondataProcesser() {
		List<Vote> voteList = getLatestDayGameInfo().getVoteList();
		for (Vote vote : voteList) {
			actiondataProcesser.addActionData(new VoteActiondata(vote));
		}
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		ClientSystem clientSystem = ClientSystem.getInstance();
		// TODO 提出時はCONTESTモードに切り替え
		// clientSystem.setMode(RunMode.CONTEST);
		clientSystem.setMode(RunMode.DEBUG);
		werewolfLog = new WerewolfLog();
		actiondataProcesser = new ActiondataProcesser();
	}

	@Override
	public void dayStart() {
		readTalkNum = 0;
		writeWerewolfLog();
		addVoteActiondataToActiondataProcesser();
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println(getLatestDayGameInfo().getGuardedAgent());
	}

	@Override
	public void finish() {
		writeWerewolfLog();
		addVoteActiondataToActiondataProcesser();
		try {
			actiondataProcesser.exportCSVFile(".");
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
			actiondataProcesser.addActionData(new TalkActiondata(talk));
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
}
