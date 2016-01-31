package jp.ac.maslab.ando.aiwolf.client.data.definition;

/**
 * データIDを発行するクラスです。
 * @author keisuke
 */
public class DataIDIssuer {
	private static int id;

	private DataIDIssuer() {
		id = -1;
	}

	/**
	 * データIDを取得します。
	 * @return データID
	 */
	public static int getID() {
		id++;
		return id;
	}
}
