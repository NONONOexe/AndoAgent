package jp.ac.maslab.ando.aiwolf.client.data.definition;

/**
 * 能力のタグの列挙です。<br>
 * 能力タグのIDは1300から1349が使用されます。
 * @author keisuke
 *
 */
public enum GiftTag {
	/**
	 * 人狼による襲撃を示します。
	 */
	ATTACK(1301),
	/**
	 * 占い師による占いを示します。
	 */
	DIVINE(1302),
	/**
	 * 狩人による護衛を示します。
	 */
	GUARD(1303),
	/**
	 * 霊能者による霊能を示します。
	 */
	INQUEST(1304);

	private int id;

	private GiftTag(int id) {
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
