package jp.ac.maslab.ando.aiwolf.client.data;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Species;

/**
 * 占い結果です。
 * @author keisuke
 */
public class Divined {
	/**
	 * 占った日付です。
	 */
	private int day;
	/**
	 * 占いの発言のIDです。
	 */
	private int talkID;
	/**
	 * 占ったエージェントです。
	 */
	private Agent agent;
	/**
	 * 占われたエージェントです。
	 */
	private Agent target;
	/**
	 * 占った結果です。
	 */
	private Species result;

	/**
	 * 新しく占い結果を構築します。
	 */
	public Divined(int day, int talkID, Agent agent, Agent target, Species result) {
		this.day = day;
		this.talkID = talkID;
		this.agent = agent;
		this.target = target;
		this.result = result;
	}

	/**
	 * 占った日付を返します。
	 * @return 占った日付
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 占いの発言のIDを返します。
	 * @return 占いの発言のID
	 */
	public int getTalkID() {
		return talkID;
	}

	/**
	 * 占ったエージェントを返します。
	 * @return 占ったエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * 占われたエージェントを返します。
	 * @return 占われたエージェント
	 */
	public Agent getTarget() {
		return target;
	}

	/**
	 * 占った結果を返します。
	 * @return 占った結果
	 */
	public Species getResult() {
		return result;
	}
}
