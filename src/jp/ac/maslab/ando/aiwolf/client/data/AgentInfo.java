package jp.ac.maslab.ando.aiwolf.client.data;

import org.aiwolf.common.net.GameInfo;
import org.aiwolf.common.net.GameSetting;

/**
 * エージェントの持つ情報です。
 * @author keisuke
 */
public class AgentInfo {
	/**
	 * COに関する情報です。
	 */
	private COInfo coInfo;
	/**
	 * 疑い度を管理します。
	 */
	private ScoreManager scoreManager;

	/**
	 * エージェントの情報を構築します。
	 * @param gameInfo ゲーム情報
	 * @param gameSetting ゲーム設定
	 */
	public AgentInfo(GameInfo gameInfo, GameSetting gameSetting) {
		coInfo = new COInfo();
		scoreManager = new ScoreManager(gameInfo.getAgentList(), gameInfo.getAgent());
	}

	/**
	 * COに関する情報を返します。
	 * @return COに関する情報
	 */
	public COInfo getCOInfo() {
		return coInfo;
	}

	/**
	 * 疑い度を管理するオブジェクトを返します。
	 * @return 疑い度を管理するオブジェクト
	 */
	public ScoreManager getScoreManager() {
		return scoreManager;
	}
}
