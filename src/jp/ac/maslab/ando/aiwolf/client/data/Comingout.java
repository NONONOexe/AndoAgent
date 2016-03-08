package jp.ac.maslab.ando.aiwolf.client.data;

import org.aiwolf.common.data.Agent;
import org.aiwolf.common.data.Role;

/**
 * カミングアウトです。
 * @author keisuke
 *
 */
public class Comingout {
	/**
	 * COした日付です。
	 */
	private int day;
	/**
	 * COした発言のIDです。
	 */
	private int talkID;
	/**
	 * COしたエージェントです。
	 */
	private Agent agent;
	/**
	 * COした役職です。
	 */
	private Role role;

	/**
	 * 新しくカミングアウトを構築します。
	 * @param day COした日付
	 * @param talkID COした発言のID
	 * @param agent COしたエージェント
	 * @param role COした役職
	 */
	public Comingout(int day, int talkID, Agent agent, Role role) {
		this.day = day;
		this.talkID = talkID;
		this.agent = agent;
		this.role = role;
	}

	/**
	 * COした日付を返します。
	 * @return COした日付
	 */
	public int getDay() {
		return day;
	}

	/**
	 * COした発言のIDを返します。
	 * @return COした発言のID
	 */
	public int getTalkID() {
		return talkID;
	}

	/**
	 * COしたエージェントを返します。
	 * @return COしたエージェント
	 */
	public Agent getAgent() {
		return agent;
	}

	/**
	 * COした役職を返します。
	 * @return COした役職
	 */
	public Role getRole() {
		return role;
	}
}
