package jp.ac.maslab.ando.aiwolf.client.data.definition;

/**
 * 投票のタグの列挙です。<br>
 * 投票タグのIDは1250から1299が使用されます。
 * @author keisuke
 *
 */
public enum VoteTag {
	/**
	 * 村での投票を示します。
	 */
	EXECUTION_VOTE(1251),
	/**
	 * 人狼の襲撃投票を示します。
	 */
	ATTACK_VOTE(1252),
	/**
	 * 誰に投票するかの予定の発言があった時に、その対象と異なるエージェントへの投票を示します。
	 */
	ILLEGAL_VOTE(1253),
	/**
	 * 占い師の白判定エージェントに対しての投票を示します。
	 */
	VOTE_FOR_WHITE(1254),
	/**
	 * 占い師の黒判定エージェントに対しての投票を示します。
	 */
	VOTE_FOR_BLACK(1255);

	private int id;

	private VoteTag(int id) {
		this.id = id;
	}

	/**
	 * 行動名のIDを返します。
	 * @return 属性のID
	 */
	public int getID() {
		return id;
	}

}
