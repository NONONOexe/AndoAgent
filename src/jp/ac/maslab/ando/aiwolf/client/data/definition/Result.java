package jp.ac.maslab.ando.aiwolf.client.data.definition;

/**
 * 能力の使用の成功と失敗の定義です。
 * @author keisuke
 */
public enum Result {
	/**
	 * 成功を示します。
	 */
	SUCCESS(2001),
	/**
	 * 失敗を示します。
	 */
	FAILURE(2002);

	private int id;

	private Result(int id) {
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
