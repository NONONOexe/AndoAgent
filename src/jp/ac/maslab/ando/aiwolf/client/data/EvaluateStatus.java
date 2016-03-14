package jp.ac.maslab.ando.aiwolf.client.data;

/**
 * 各エージェントの評価する状況です。
 * @author ando
 */
public enum EvaluateStatus {
	/**
	 * 他のエージェントに人狼と占われることを示します。
	 */
	DIVINED_WOLF,
	/**
	 * 他のエージェントに人間と占われることを示します。
	 */
	DIVINED_HUMAN,
	/**
	 * 自分を人狼だと占ったことを示します。
	 */
	DIVINE_ME_WOLF,
	/**
	 * 自分を人間だと占ったことを示します。
	 */
	DIVINE_ME_HUMAN,
	/**
	 * 自分の投票先と投票先が一致したことを示します。
	 */
	SAME_VOTE,
	/**
	 * 自分に投票したことを示します。
	 */
	VOTE_ME,
	/**
	 * 投票されることを示します。
	 */
	VOTED,
	/**
	 * 占い師COしたことを示します。
	 */
	SEER_CO,
	/**
	 * 霊能者COしたことを示します。
	 */
	MEDIUM_CO,
	/**
	 * 村人COしたことを示します。
	 */
	VILLAGER_CO,
	/**
	 * 狂人COしたことを示します。
	 */
	POSSESSED_CO,
	/**
	 * 人狼COしたことを示します。
	 */
	WEREWOLF_CO,
	/**
	 * 狩人COしたことを示します。
	 */
	BODYGUARD_CO,
	/**
	 * 意見に賛成されたことを示します。
	 */
	AGREED_OPINION,
	/**
	 * 意見に反対されたことを示します。
	 */
	DISAGREED_OPINION
}
