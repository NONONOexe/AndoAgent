package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.List;

import org.aiwolf.client.base.player.AbstractRole;
import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.data.COInfo;
import jp.ac.maslab.ando.aiwolf.client.data.FirstPersonEvaluator;
import jp.ac.maslab.ando.aiwolf.client.data.RoleForecast;
import jp.ac.maslab.ando.aiwolf.client.tool.util.AIWolfTools;

/**
 * 全役職に共通する処理を行うクラスです。
 * @author keisuke
 */
public abstract class RoleBase extends AbstractRole {
	/**
	 * 今日の会話ログをどこまで読んだかを示します。
	 */
	private int readTalkNum;
	/**
	 * 時間を管理します。
	 */
	private TimeManager timeManager;
	/**
	 * COに関する情報を保持します。
	 */
	private COInfo coInfo;
	/**
	 * 会話に関する情報を保持します。
	 */
	private TalkInfo talkInfo;
	/**
	 * 各エージェントの生死に関する情報です。
	 */
	private ViabilityInfo viabilityInfo;
	/**
	 * 役職の予想です。
	 */
	private RoleForecast roleForecast;
	/**
	 * 各エージェントの評価です。
	 */
	private FirstPersonEvaluator evaluator;
	/**
	 * 投票先を決定します。
	 */
	private VoteTargetManager voteTargetManager;

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		this.timeManager = new TimeManager();
		this.coInfo = new COInfo(gameSetting.getRoleNumMap());
		this.talkInfo = new TalkInfo(gameInfo.getAgentList());
		this.viabilityInfo = new ViabilityInfo(gameInfo.getAgentList());
		this.roleForecast = new RoleForecast(gameInfo.getAgentList());
		this.evaluator = new FirstPersonEvaluator(gameInfo.getAgentList(), getMe(), timeManager);
		this.voteTargetManager = new VoteTargetManager(timeManager);
	}

	@Override
	public void dayStart() {
		// readTalkNumを初期化します。
		this.readTalkNum = 0;
		// 投票を評価します。
		this.evaluator.evaluateVote(getLatestDayGameInfo().getVoteList());
		// 処刑されたエージェントを設定します。
		this.viabilityInfo.setExecuted(getDay(), getLatestDayGameInfo().getExecutedAgent());
		// 襲撃されたエージェントを設定します。
		this.viabilityInfo.setAttacked(getDay(), getLatestDayGameInfo().getAttackedAgent());
		// 情報を表示します。
		System.out.println("dayStart");
		System.out.println("========Evaluator========");
		for (Agent agent : getLatestDayGameInfo().getAgentList()) {
			if (agent.equals(getMe())) {
				continue;
			}
			System.out.print(agent + " " + evaluator.getEvaluateValue(agent));
			if (evaluator.getConvictionAgentList().contains(agent)) {
				System.out.print(" conviction");
			}
			if (evaluator.getDoubtfulAgentList().contains(agent)) {
				System.out.print(" doubtful");
			}
			System.out.println();
		}
		System.out.println("=============================================");
	}

	@Override
	public void update(GameInfo gameInfo) {
		super.update(gameInfo);
		// ターンを進めます。
		timeManager.moveNextTurn(this.getDay());
		// 今日のtalkListに対する処理部分です。
		List<Talk> talkList = gameInfo.getTalkList();
		while (readTalkNum < talkList.size()) {
			Talk talk = talkList.get(readTalkNum);
			// TopicがCominoutのTalkから、COInfoを更新します。
			Utterance utterance = new Utterance(talk.getContent());
			if (utterance.getTopic().equals(Topic.COMINGOUT)) {
				coInfo.addComingout(AIWolfTools.convertToComingout(talk));
			}
			// talkInfoにtalkを追加します。
			talkInfo.addTalk(talk);
			// 会話を評価します。
			evaluator.evaluateTalk(talk);
			// readTalkNumをインクリメントします。
			readTalkNum++;
		}
		// 情報を表示します。
		System.out.println("update");
		System.out.println("Vote target:" + timeManager.getCurrent() + " " + voteTargetManager.getVoteTarget());
		System.out.println("=============================================");
	}

	@Override
	public void finish() {
		// 情報を表示します。
		System.out.println("finish");
		System.out.println("========COInfo========");
		System.out.println("Comingout Agents:" + coInfo.getCOAgentList());
		System.out.println("Medium CO:" + coInfo.getCOAgentList(Role.MEDIUM));
		System.out.println("Seer CO:" + coInfo.getCOAgentList(Role.SEER));
		System.out.println("========TalkInfo========");
		for (Agent agent : getLatestDayGameInfo().getAgentList()) {
			System.out.println(agent + " Talk times:" + talkInfo.getTalkTimes(agent));
		}
		Agent testAgent = getMe();
		System.out.println("=" + testAgent + " talk=");
		for (Talk talk : talkInfo.getTalkList(testAgent)) {
			System.out.println(talk);
		}
		System.out.println("========Evaluator========");
		for (Agent agent : getLatestDayGameInfo().getAgentList()) {
			if (agent.equals(getMe())) {
				continue;
			}
			System.out.print(agent + " " + evaluator.getEvaluateValue(agent));
			if (evaluator.getConvictionAgentList().contains(agent)) {
				System.out.print(" conviction");
			}
			if (evaluator.getDoubtfulAgentList().contains(agent)) {
				System.out.print(" doubtful");
			}
			System.out.println();
		}
		System.out.println("========ViabilityInfo========");
		if (getViabilityInfo().isAlive(getMe())) {
			System.out.println("You Alive!");
		}
		if (getViabilityInfo().isDead(getMe())) {
			System.out.println("You Dead!");
		}
		System.out.println("=============================================");
	}

	/**
	 * COに関する情報を返します。
	 * @return COに関する情報
	 */
	public COInfo getCOInfo() {
		return coInfo;
	}

	/**
	 * 会話に関する情報を返します。
	 * @return 会話に関する情報
	 */
	public TalkInfo getTalkInfo() {
		return talkInfo;
	}

	/**
	 * 生死に関する情報を返します。
	 * @return 生死に関する情報
	 */
	public ViabilityInfo getViabilityInfo() {
		return viabilityInfo;
	}

	/**
	 * 役職の予想に関するオブジェクトを返します。
	 * @return 役職の予想に関するオブジェクト
	 */
	public RoleForecast getRoleForecast() {
		return roleForecast;
	}

	/**
	 * 各エージェントの評価に関するオブジェクトを返します。
	 * @return 各エージェントの評価に関するオブジェクト
	 */
	public FirstPersonEvaluator getEvaluator() {
		return evaluator;
	}

	/**
	 * 投票先を管理するためのオブジェクトを返します。
	 * @return 投票先を管理するためのオブジェクト
	 */
	public VoteTargetManager getVoteTargetManager() {
		return voteTargetManager;
	}
}
