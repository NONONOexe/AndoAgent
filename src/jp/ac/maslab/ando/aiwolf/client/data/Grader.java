package jp.ac.maslab.ando.aiwolf.client.data;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Talk;

/**
 * {@code Talk}を採点します。
 * @author keisuke
 */
public class Grader {
	/**
	 * 中心視点の自エージェントを示します。
	 */
	private Agent me;
	/**
	 * 評価する{@code Talk}オブジェクトです。
	 */
	private Talk talk;

	/**
	 * 新しく{@code Talk}を採点するオブジェクトを構築します。
	 * @param me 中心視点の自エージェント
	 * @param talk 評価する{@code Talk}オブジェクト
	 */
	public Grader(Agent me, Talk talk) {

	}
}
