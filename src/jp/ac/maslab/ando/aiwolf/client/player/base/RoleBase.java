package jp.ac.maslab.ando.aiwolf.client.player.base;

import java.util.List;

import org.aiwolf.client.base.player.AbstractRole;
import org.aiwolf.client.lib.Topic;
import org.aiwolf.client.lib.Utterance;
import org.aiwolf.common.data.Talk;
import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

import jp.ac.maslab.ando.aiwolf.client.data.COInfo;
import jp.ac.maslab.ando.aiwolf.client.data.Comingout;
import jp.ac.maslab.ando.aiwolf.client.data.FirstPersonEvaluator;
import jp.ac.maslab.ando.aiwolf.client.data.RoleForecast;

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
	 * COに関する情報を保持します。
	 */
	private COInfo coInfo;
	/**
	 * 会話に関する情報を保持します。
	 */
	private TalkInfo talkInfo;
	/**
	 * 役職の予想です。
	 */
	private RoleForecast roleForecast;
	/**
	 * 各エージェントの評価です。
	 */
	private FirstPersonEvaluator evaluator;
	/**
	 * 各エージェントの生死に関する情報です。
	 */
	private ViabilityInfo viabilityInfo;

	/**
	 * 全役職の共通戦略を構築します。
	 */
	public RoleBase() {
	}

	@Override
	public void initialize(GameInfo gameInfo, GameSetting gameSetting) {
		super.initialize(gameInfo, gameSetting);
		coInfo = new COInfo(gameSetting.getRoleNumMap());
		talkInfo = new TalkInfo(gameInfo.getAgentList());
		viabilityInfo = new ViabilityInfo(gameInfo.getAgentList());
		roleForecast = new RoleForecast(gameInfo.getAgentList());
		evaluator = new FirstPersonEvaluator(gameInfo.getAgentList(), getMe());
	}

	@Override
	public void dayStart() {
		// readTalkNumの初期化
		readTalkNum = 0;

		// ====FirstPersonEvaluatorの更新====
		// 投票を評価
		evaluator.evaluateVote(getLatestDayGameInfo().getVoteList());

		// ====ViabilityInfoの更新====
		// 処刑されたエージェントを死亡エージェントに追加
		if (super.getLatestDayGameInfo().getExecutedAgent() != null) {
			viabilityInfo.addDeadAgent(
					super.getLatestDayGameInfo().getExecutedAgent());
		}
		// 襲撃されたエージェントを死亡エージェントに追加
		if (super.getLatestDayGameInfo().getAttackedAgent() != null) {
			viabilityInfo.addDeadAgent(
					super.getLatestDayGameInfo().getAttackedAgent());
		}
	}

	@Override
	public void update(GameInfo gameInfo) {
		super.update(gameInfo);

		List<Talk> talkList = gameInfo.getTalkList();

		while (readTalkNum < gameInfo.getTalkList().size()) {
			Talk talk = talkList.get(readTalkNum);

			// ====COInfoの更新====
			Utterance utterance = new Utterance(talk.getContent());
			if (utterance.getTopic().equals(Topic.COMINGOUT)) {
				coInfo.addComingout(new Comingout(
						talk.getDay(),
						talk.getIdx(),
						utterance.getTarget(),
						utterance.getRole()));
			}

			// ====TalkInfoの更新====
			talkInfo.addTalk(talk);

			// ====FirstPersonEvaluatorの更新====
			// 会話を評価
			evaluator.evaluateTalk(talk);

			readTalkNum++;
		}
	}

	@Override
	public void finish() {
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
	 * @return
	 */
	public FirstPersonEvaluator getEvaluator() {
		return evaluator;
	}
}
