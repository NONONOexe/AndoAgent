package jp.ac.maslab.ando.aiwolf.client.data;

/**
 * 疑い点数です。各エージェントの行動に応じて点数を定義します。
 * @author keisuke
 */
public enum DoubtScore {
	/**
	 * 疑い点数の初期値です。
	 */
	DEFAULT_SCORE(5),
	/**
	 * 他エージェントによる占い結果が人狼だったエージェントに対する得点です。
	 */
	DIVINE_WOLF_SCORE(5),
	/**
	 * 他エージェントによる占い結果が人間だったエージェントに対する得点です。
	 */
	DIVINE_HUMAN_SCORE(-3),
	/**
	 * 自分を人狼だと占ったエージェントに対する得点です。
	 */
	DIVINE_ME_WOLF_SCORE(15),
	/**
	 * 自分を人間だと占ったエージェントに対する得点です。
	 */
	DIVINE_ME_HUMAN_SCORE(-3),
	/**
	 * 自分の投票先と投票先が一致したエージェントに対する得点です。
	 */
	SAME_VOTE_SCORE(-2),
	/**
	 * 自分に投票したエージェントに対する得点です。
	 */
	VOTE_ME_SCORE(2),
	/**
	 * 占い師COしたエージェントに対する得点です。
	 */
	SEER_CO_SCORE(0),
	/**
	 * 霊能者COしたエージェントに対する得点です。
	 */
	MEDIUM_CO_SCORE(0),
	/**
	 * 村人COしたエージェントに対する得点です。
	 */
	VILLAGER_CO_SCORE(1),
	/**
	 * 狂人COしたエージェントに対する得点です。
	 */
	POSSESSED_CO_SCORE(4),
	/**
	 * 人狼COしたエージェントに対する得点です。
	 */
	WEREWOLF_CO_SCORE(15),
	/**
	 * 狩人COしたエージェントに対する得点です。
	 */
	BODYGUARD_CO_SCORE(3);

	/**
	 * 点数を示します。
	 */
	private int score;

	/**
	 * 指定された点数の疑い点数を構築します。
	 * @param score 点数
	 */
	private DoubtScore(int score) {
		this.score = score;
	}

	/**
	 * この疑い点数の点数を返します。
	 * @return 点数
	 */
	public int getScore() {
		return score;
	}
}
